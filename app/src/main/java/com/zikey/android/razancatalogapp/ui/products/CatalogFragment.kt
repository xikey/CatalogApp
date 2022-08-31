package com.zikey.android.razancatalogapp.ui.products

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.*
import com.google.android.material.appbar.AppBarLayout
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.R
import com.zikey.android.razancatalogapp.databinding.FragmentCatalogBinding
import com.zikey.android.razancatalogapp.model.Product
import com.zikey.android.razancatalogapp.ui.adapter.CatalogAdapter


class CatalogFragment : DialogFragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!
    private var products: List<Product>? = null

    private var rvGroups: RecyclerView? = null
    private var productsAdapter: CatalogAdapter? = null
    private var selectedPosition = 0
    private var onSrollListener: ScrollListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.getWindow()?.getAttributes()!!.windowAnimations = R.style.DialogAnimation
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(
            androidx.fragment.app.DialogFragment.STYLE_NO_FRAME,
            android.R.style.Theme_Light_NoTitleBar
        );
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFont()
        initToolbar()
        initRecycleView()
        getData()

    }

    private fun getData() {

        if (products.isNullOrEmpty())
            return

        if (productsAdapter == null)
            return

        productsAdapter!!.submitList(products)

        rvGroups?.scrollToPosition(selectedPosition)
        initContent()
    }

    private fun initFont() {

        FontChanger().applyMainFont(binding.lyContent.lyContent)
    }

    private fun initToolbar() {

        val toolbar = binding.toolbar

        toolbar.setNavigationOnClickListener {
            dismiss()

        }

        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (Math.abs(verticalOffset) - appBarLayout!!.getTotalScrollRange() == 0) {
                    //  Collapsed
                    toolbar.setBackgroundColor(
                        ResourcesCompat.getColor(
                            getResources(),
                            R.color.colorPrimary,
                            null
                        )
                    )

                } else {
                    //Expanded
                    toolbar.setBackgroundColor(
                        ResourcesCompat.getColor(
                            getResources(),
                            R.color.transparent,
                            null
                        )
                    )

                }
            }

        })

    }

    private fun initRecycleView() {

        val snapHelper = PagerSnapHelper()

        val rvlayoutManager: LinearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        if (productsAdapter == null)
            productsAdapter =
                CatalogAdapter(this, object : CatalogAdapter.OnSelectItem {
                    override fun onSelect(item: Product) {

                    }

                })


        rvGroups = binding.rvProducts.apply {
            layoutManager = rvlayoutManager
            adapter = productsAdapter
        }

        snapHelper.attachToRecyclerView(rvGroups)

        rvGroups!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    val p: Int = rvlayoutManager.findLastCompletelyVisibleItemPosition()
                    if (p >= 0 && selectedPosition != p) {
                        recyclerView.getChildAt(p)
                        selectedPosition = p
                        initContent()
                    }
                }


            }

        })

    }

    fun initContent() {
        try {
            binding.lyContent.txtName.setText(products!!.get(selectedPosition).name)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    companion object {

        private const val KEY_SMS_VALIDATION = "KEY_SMS_VALIDATION"

        @JvmStatic
        fun newInstance(
            fragmentManager: FragmentManager,
            list: List<Product>?,
            itemPosition: Int,
            scrollListener: ScrollListener
        ) =
            CatalogFragment().apply {

                products = list
                show(fragmentManager, KEY_SMS_VALIDATION)
                selectedPosition = itemPosition
                onSrollListener = scrollListener
            }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        onSrollListener?.onScroll(selectedPosition)
    }

    interface ScrollListener {
        fun onScroll(position: Int)
    }

}