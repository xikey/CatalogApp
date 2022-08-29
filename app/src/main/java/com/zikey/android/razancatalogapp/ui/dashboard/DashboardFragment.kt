package com.zikey.android.razancatalogapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.R
import com.zikey.android.razancatalogapp.databinding.FragmentDashboardBinding
import com.zikey.android.razancatalogapp.model.ProductMainGroup
import com.zikey.android.razancatalogapp.ui.adapter.ProductMainGroupAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val viewModel: DashboardViewModel by viewModels()
    private val binding get() = _binding!!
    private var rvGroups: RecyclerView? = null
    private var productGroupsAdapter: ProductMainGroupAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        navBar.visibility=View.VISIBLE

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProgress()
        initFont()
        initRecycleView()
        initProductGroupsObserver()
        getData()


    }

    private fun initFont() {

        FontChanger().applyMainFont(binding.lyContent)
    }

    private fun getData() {

        viewModel.getProductMainGroups(requireContext())
    }

    private fun initProductGroupsObserver() {

        if (viewModel.productGroupDataResponse.hasObservers())
            return
        viewModel.productGroupDataResponse.observe(
            viewLifecycleOwner,
            Observer { response ->
                response?.let {

                    if (!response.list.isNullOrEmpty()) {
                        productGroupsAdapter!!.submitList(response.list)
                    } else {

                    }

                }
            })

        viewModel.productGroupError.observe(
            viewLifecycleOwner,
            Observer { dataError ->
                dataError?.let {
                    if (dataError) {
//                        viewModel.getAdvertises(requireContext())
                    }

                }
            })


    }

    private fun initRecycleView() {

        if (productGroupsAdapter == null)
            productGroupsAdapter =
                ProductMainGroupAdapter(this, object : ProductMainGroupAdapter.OnSelectItem {
                    override fun onSelect(item: ProductMainGroup) {

                    }

                })


        rvGroups = binding.rvGroups.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productGroupsAdapter
        }


    }

    private fun initProgress() {

        val progress: LinearLayout = binding.lyProgress.lyProgress
        viewModel.progress.observe(viewLifecycleOwner) {
            if (it) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        }

    }

}