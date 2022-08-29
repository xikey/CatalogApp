package com.zikey.android.razancatalogapp.ui.product_sub_group

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.R
import com.zikey.android.razancatalogapp.databinding.FragmentProductSubGroupBinding
import com.zikey.android.razancatalogapp.model.ProductMainGroup
import com.zikey.android.razancatalogapp.model.ProductSubGroup
import com.zikey.android.razancatalogapp.ui.adapter.ProductSubGroupAdapter
import com.zikey.android.razancatalogapp.ui.products.ProductsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductSubGroupFragment : Fragment() {

    private var _binding: FragmentProductSubGroupBinding? = null
    private val binding get() = _binding!!
    private var rvGroups: RecyclerView? = null
    private var productGroupsAdapter: ProductSubGroupAdapter? = null
    private var mainGroupID: Long? = null
    private var mainGroupName: String? = null


    private val viewModel: ProductSubGroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args: ProductSubGroupFragmentArgs by navArgs()
        mainGroupID = args.mainGroupId
        mainGroupName = args.mainGroupName

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductSubGroupBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onDestroy() {

        _binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initProgress()
        hideBottomNavigation()
        initFont()
        initToolbar()
        initRecycleView()
        initProductGroupsObserver()
        getData()

    }

    private fun hideBottomNavigation() {

        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        navBar.visibility=View.GONE

    }

    private fun initToolbar() {

        val toolbar = binding.toolbar
        toolbar.setSubtitle(mainGroupName)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                ProductSubGroupFragmentDirections.actionProductSubGroupFragmentToNavigationDashboard()
            )

        }
        val menu: Menu = toolbar.menu
        menu.findItem(R.id.menu_search)
            .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(p0: MenuItem?): Boolean {

                    return true
                }

            })
    }

    private fun initFont() {

        FontChanger().applyMainFont(binding.txtEmptyRows)
        FontChanger().applyMainFont(binding.rvGroups)
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

    private fun initRecycleView() {

        if (productGroupsAdapter == null)
            productGroupsAdapter =
                ProductSubGroupAdapter(this, object : ProductSubGroupAdapter.OnSelectItem {
                    override fun onSelect(item: ProductSubGroup) {
                        findNavController().navigate(
                            ProductSubGroupFragmentDirections.actionProductSubGroupFragmentToProductsFragment(
                                item.name!!, mainGroupID!!, item.id!!
                            )
                        )
                    }

                })


        rvGroups = binding.rvGroups.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productGroupsAdapter
        }


    }

    private fun initProductGroupsObserver() {

        if (viewModel.productSubDataResponse.hasObservers())
            return
        viewModel.productSubDataResponse.observe(
            viewLifecycleOwner,
            Observer { response ->
                response?.let {

                    if (!response.list.isNullOrEmpty()) {
                        binding.txtEmptyRows.visibility = View.GONE
                        productGroupsAdapter!!.submitList(response.list)
                    } else {
                        binding.txtEmptyRows.visibility = View.VISIBLE
                    }

                }
            })

        viewModel.productSubError.observe(
            viewLifecycleOwner,
            Observer { dataError ->
                dataError?.let {
                    if (dataError) {
                        binding.txtEmptyRows.visibility = View.VISIBLE
                    }

                }
            })
    }

    private fun getData() {

        mainGroupID?.let { viewModel.getProductSubGroups(it) }
    }


}