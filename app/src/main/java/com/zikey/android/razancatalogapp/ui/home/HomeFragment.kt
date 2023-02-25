package com.zikey.android.razancatalogapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.R
import com.zikey.android.razancatalogapp.core.ImageViewWrapper
import com.zikey.android.razancatalogapp.core.ScreenSize
import com.zikey.android.razancatalogapp.databinding.FragmentHomeBinding
import com.zikey.android.razancatalogapp.model.Advertise
import com.zikey.android.razancatalogapp.model.Product
import com.zikey.android.razancatalogapp.ui.adapter.MainAdvertisePagerAdapter
import com.zikey.android.razancatalogapp.ui.adapter.MainTopTenAdapter
import com.zikey.android.razancatalogapp.ui.custom_view.Indicator
import com.zikey.android.razancatalogapp.ui.products.CatalogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Runnable

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var pageChanged = true
    private var slideHandler: Handler? = null
    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private var topTenAdapter: MainTopTenAdapter? = null
    private var slideChanger: Runnable? = null
    var firstItemXPositionOnStart: Int = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initFonts()
        initProgress()
        initRecycleView()
        initClickListeners()
        initTopTenSize()
        initAdvertiseObservers()
        initProductsObserver()
        getData()

    }

    private fun initClickListeners() {

        binding.lyContent.lySearch.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToSearchFragment()
            )
        }

    }

    private fun initTopTenSize() {
        ImageViewWrapper(requireContext()).into(binding.lyContent.lyTopTen.imgSpecial)
            .loadFromDrowable(R.drawable.ic_offer_png)

//        binding.lyContent.lyTopTen.

        val box: View = binding.lyContent.lyTopTen.lyShortcuts
        val width = ScreenSize.width
        val height = (width / 3) * 2
        val params = box.layoutParams
        params.height = height

        box.layoutParams = params
    }

    private fun initProductsObserver() {

        if (viewModel.productDataResponse.hasObservers())
            return

        viewModel.productDataResponse.observe(
            viewLifecycleOwner,
            Observer { response ->
                response?.let {
                    topTenAdapter?.submitList(response.products)

                }
            })

        viewModel.productError.observe(
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
        val rvTopTen = binding.lyContent.lyTopTen.rvTopDailyProducts


        if (topTenAdapter == null)
            topTenAdapter = MainTopTenAdapter(this, object : MainTopTenAdapter.OnSelectItem {
                override fun onSelect(item: Product, position: Int) {

                    CatalogFragment.newInstance(requireActivity().supportFragmentManager,
                        item, object : CatalogFragment.ScrollListener {
                            override fun onScroll(position: Int) {

                            }

                        })

                }


            })
        val rvLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        rvTopTen.apply {
            adapter = topTenAdapter
            layoutManager = rvLayoutManager
        }


        rvTopTen.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                try {
                    val pos = rvLayoutManager.findFirstVisibleItemPosition()

                    if (pos == 0) {
                        val originalPos = IntArray(2)

                        recyclerView.getChildAt(pos).getLocationOnScreen(originalPos)
                        if (firstItemXPositionOnStart == 0) {
                            firstItemXPositionOnStart = originalPos[0]
                        }
                        if (originalPos[0] - firstItemXPositionOnStart >= 0) {
                            val alpgha: Float =
                                (originalPos[0] - firstItemXPositionOnStart).toFloat()
                            // Log.e("POSITION", "onScrolled: ${100 - alpgha / 2}")
                            binding.lyContent.lyTopTen.lyRightSide.alpha =
                                ((100 - alpgha / 2) / 100)
                        }
//                    Log.e("POSITION", "onScrolled: $pos")
//                    Log.e("dx", "onScrolled: $dx")
//                    Log.e("posX", "onScrolled: ${originalPos[0]}")
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })

    }


    private fun initAdvertiseView(count: Int, advertises: List<Advertise>) {

        if (_binding == null)
            return

        val advertiseHeaderBox: View = binding.lyContent.rvAdvertiseBox
        val width = ScreenSize.width
        val height = (width / 3) * 2
        val params = advertiseHeaderBox.layoutParams
        params.height = height

        advertiseHeaderBox.layoutParams = params

        val adapter = MainAdvertisePagerAdapter(
            this
        )

        adapter.setAdvertises(advertises)
        adapter.setCount(advertises.size)
        adapter.setHeight(height)

        adapter.notifyDataSetChanged()


        val pager: ViewPager2 = binding.lyContent.advertiseHeaderBox.pager
        pager.adapter = adapter
        val indexBox: Indicator = binding.lyContent.advertiseHeaderBox.indexBox
        indexBox.setViewPager(pager)
        pager.currentItem = indexBox.selectedPage
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                pageChanged = true
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        initSlideAutoChanger(pager, count)


    }

    private fun getData() {

        viewModel.getAdvertises()
        viewModel.getProducts()
    }

    private fun initAdvertiseObservers() {

        if (viewModel.advertiseDataResponse.hasObservers())
            return

        viewModel.advertiseDataResponse.observe(
            viewLifecycleOwner,
            Observer { response ->
                response?.let {
                    if (!response.datas.isNullOrEmpty()) {
                        initAdvertiseView(response.datas!!.size, response.datas!!)
                    } else {

                    }

                }
            })

        viewModel.advertiseError.observe(
            viewLifecycleOwner,
            Observer { dataError ->
                dataError?.let {
                    if (dataError) {
//                        viewModel.getAdvertises(requireContext())
                    }

                }
            })


    }

    private fun initSlideAutoChanger(pager: ViewPager2, count: Int) {

        if (slideChanger == null)
            slideChanger = object : Runnable {
                override fun run() {
                    if (!pageChanged) {
                        if (pager != null) {
                            var i = pager!!.currentItem
                            i = i + 1
                            if (i >= count) i = 0
                            pager!!.currentItem = i
                        }
                    }
                    pageChanged = false
                    slideHandler?.postDelayed(this, 5000)
                }
            }


        slideHandler = Handler(Looper.getMainLooper())
        slideChanger!!.run()

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

    private fun initFonts() {

        FontChanger().applyMainFont(binding.lyRoot)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        slideChanger = null
        slideHandler = null
        pageChanged = true


    }
}