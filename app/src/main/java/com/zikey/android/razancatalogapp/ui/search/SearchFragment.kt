package com.zikey.android.razancatalogapp.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.R
import com.zikey.android.razancatalogapp.databinding.FragmentSearchBinding
import com.zikey.android.razancatalogapp.model.Product
import com.zikey.android.razancatalogapp.ui.adapter.ProductsAdapter
import com.zikey.android.razancatalogapp.ui.products.CatalogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var rvGroups: RecyclerView? = null
    private var productsAdapter: ProductsAdapter? = null
    private var keySearch = ""
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        hideBottomNavigation()
        return binding.root
    }

    override fun onDestroy() {

        _binding = null
        showBottomNavigation()
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

    private fun hideBottomNavigation() {

        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        navBar.visibility = View.GONE

    }

    private fun showBottomNavigation() {

        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        navBar.visibility = View.VISIBLE

    }

    private fun initToolbar() {

        val toolbar = binding.toolbar

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionDismiss()
            )

        }
        val menu: Menu = toolbar.menu


        searchView =
            menu.findItem(R.id.menu_search).actionView as androidx.appcompat.widget.SearchView
        menu.performIdentifierAction(R.id.menu_search, 0)


        if (searchView != null) {
            searchView.queryHint = "جستجو کالا"
            searchView.setQuery("", true)
            searchView.isIconified = false
        }


        searchView.requestFocus()



        searchView.setOnQueryTextFocusChangeListener { p0, hasFocus ->
            if (hasFocus) {
                showKeyboard(p0!!.findFocus());
            }
        }



        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            private var timer: Timer = Timer()
            private val DELAY: Long = 1000 // Milliseconds


            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                timer.cancel()
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {

                        override fun run() {
                            if (newText != null) {
                                keySearch = newText
                            } else {
                                keySearch = ""
                            }

                            viewModel.getProducts(keySearch)

                        }
                    },
                    DELAY
                )


                return true
            }

        })

    }

    private fun showKeyboard(view: View) {
        try {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun hideKeyboard() {

        try {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchView.windowToken,0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
                                object : CatalogFragment.ScrollListener {
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
                        // productsAdapter!!.submitList(null)
                        binding.txtEmptyRows.visibility = View.GONE
                        productsAdapter!!.submitList(response.products)

                    } else {
                        productsAdapter!!.submitList(null)
                        binding.txtEmptyRows.visibility = View.VISIBLE
                    }

                }
            })

        viewModel.productsError.observe(
            viewLifecycleOwner,
            Observer { dataError ->
                dataError?.let {

                    if (dataError) {
                        productsAdapter!!.submitList(null)
                        binding.txtEmptyRows.visibility = View.VISIBLE
                    }

                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        searchView.setOnQueryTextFocusChangeListener(null)
        hideKeyboard()
    }


}