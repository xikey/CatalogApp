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
import androidx.viewpager.widget.ViewPager
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.rd.PageIndicatorView
import com.rd.animation.type.AnimationType
import com.zikey.android.razancatalogapp.databinding.FragmentHomeBinding
import com.zikey.android.razancatalogapp.model.Advertise
import com.zikey.android.razancatalogapp.ui.adapter.MainAdvertisePagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var pageChanged = true
    private var slideHandler: Handler? = null
    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        initAdvertiseObservers()
        getData()

    }

    private fun initAdvertiseView(count: Int, advertises: List<Advertise>) {


        val advertiseHeaderBox: View = binding.lyContent.rvAdvertiseBox
        val width = resources.displayMetrics.widthPixels
        val height = (width / 3) * 2
        val params = advertiseHeaderBox.layoutParams
        params.height = height



        advertiseHeaderBox.layoutParams = params


        val adapter = MainAdvertisePagerAdapter(
            activity?.supportFragmentManager,
            height,
            count,
            advertises
        )

        val pager = binding.lyContent.advertiseHeaderBox.pager
        pager.adapter = adapter
        val indexBox: PageIndicatorView = binding.lyContent.advertiseHeaderBox.pageIndicatorView
        indexBox.count = count
        indexBox.setViewPager(pager)
        indexBox.setAnimationType(AnimationType.WORM)
        pager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                pageChanged = true
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        initSlideAutoChanger(pager, count)

    }

    private fun getData() {

        viewModel.getAdvertises(requireContext())
    }

    private fun initAdvertiseObservers() {
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

    private fun initSlideAutoChanger(pager: ViewPager, count: Int) {

        var slideChanger: Runnable = object : Runnable {
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
        slideChanger.run()
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
    }
}