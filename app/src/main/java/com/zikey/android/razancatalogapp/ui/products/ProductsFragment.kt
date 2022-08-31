package com.zikey.android.razancatalogapp.ui.products

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
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.R
import com.zikey.android.razancatalogapp.databinding.FragmentProductsBinding
import com.zikey.android.razancatalogapp.model.Product
import com.zikey.android.razancatalogapp.ui.adapter.ProductSubGroupAdapter
import com.zikey.android.razancatalogapp.ui.adapter.ProductsAdapter
import com.zikey.android.razancatalogapp.ui.product_sub_group.ProductSubGroupFragmentArgs
import com.zikey.android.razancatalogapp.ui.product_sub_group.ProductSubGroupFragmentDirections
import com.zikey.android.razancatalogapp.ui.product_sub_group.ProductSubGroupViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModels()

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private var rvGroups: RecyclerView? = null
    private var productsAdapter: ProductsAdapter? = null
    private var mainGroupID: Long? = null
    private var subGroupID: Long? = null
    private var subName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args: ProductsFragmentArgs by navArgs()
        mainGroupID = args.mainGroupId
        subGroupID = args.subGroupId
        subName = args.subGroupName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {

        _binding = null
        super.onDestroy()
    }

    private fun initFont() {

        FontChanger().applyMainFont(binding.txtEmptyRows)
        FontChanger().applyMainFont(binding.rvProducts)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initProgress()
        initFont()
        initToolbar()
        initRecycleView()
        initProductGroupsObserver()
        getData()

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


    private fun initToolbar() {

        val toolbar = binding.toolbar

        toolbar.setSubtitle(subName)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                ProductsFragmentDirections.actionDismiss()
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

    private fun initRecycleView() {

        if (productsAdapter == null)
            productsAdapter =
                ProductsAdapter(this, object : ProductsAdapter.OnSelectItem {
                    override fun onSelect(item: Product, position: Int) {

                        if (viewModel.productsResponse.value != null) {
                            CatalogFragment.newInstance(
                                requireActivity().supportFragmentManager,
                                viewModel.productsResponse.value!!.products,
                                position,
                                object:CatalogFragment.ScrollListener{
                                    override fun onScroll(position: Int) {
                                        rvGroups?.scrollToPosition(position)
                                    }

                                }
                            )
                        }


                    }

                })


        rvGroups = binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productsAdapter
        }

    }

    private fun initProductGroupsObserver() {

        if (viewModel.productsResponse.hasObservers())
            return
        viewModel.productsResponse.observe(
            viewLifecycleOwner,
            Observer { response ->
                response?.let {

                    if (!response.products.isNullOrEmpty()) {
                        binding.txtEmptyRows.visibility = View.GONE
                        productsAdapter!!.submitList(response.products)

                    } else {
                        binding.txtEmptyRows.visibility = View.VISIBLE
                    }

                }
            })

        viewModel.productsError.observe(
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

        mainGroupID?.let { subGroupID?.let { it1 -> viewModel.getProducts(it, it1) } }
    }


}