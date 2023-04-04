package com.zikey.android.razancatalogapp.ui.splash

import android.animation.Animator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.MainActivity
import com.zikey.android.razancatalogapp.core.ScreenSize
import com.zikey.android.razancatalogapp.databinding.ActivityZikeySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import render.animations.*
import kotlin.math.hypot


class ZikeySplashActivity : AppCompatActivity() {


    private var _binding: ActivityZikeySplashBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    /**
     * Duration of wait
     */
    private val SPLASH_DISPLAY_LENGTH = 4000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar();

        _binding = ActivityZikeySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }


    override fun onStart() {
        super.onStart()

        initScreenSize()
        FontChanger().applyTitleFont(_binding!!.lyText)
        runHandler()

        CoroutineScope(IO).launch {
            delay(1000)
            withContext(Main) {

                revealContent()

            }

        }

    }

    private fun initScreenSize() {

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels

        ScreenSize.setScreenSize(width, height)

    }

    //جهت تعیین زمان نمایش صفحه خوشامدگویی
    private fun runHandler() {

        CoroutineScope(IO).launch {
            delay(SPLASH_DISPLAY_LENGTH)
            MainActivity.start(this@ZikeySplashActivity)
            finish()
        }
    }


    private fun hideStatusBar() {
        try {
            if (Build.VERSION.SDK_INT < 16) {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            } else {
                val decorView = window.decorView
                decorView.systemUiVisibility =
                    (View.SYSTEM_UI_FLAG_IMMERSIVE // Set the content to appear under the system bars so that the
                            // content doesn't resize when the system bars hide and show.
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    companion object {
        @JvmStatic
        fun start(context: FragmentActivity) {
            val starter = Intent(context, ZikeySplashActivity::class.java)
            //  .putExtra()
            context.startActivity(starter)
        }
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()

    }

    private fun revealContent() {
        val view = _binding!!.layoutRoot
        ///Center start
//        val cx = view.width / 2
//        val cy = view.height / 2
//        val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
//        val anim: Animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius)

        ///bottom right start
        val cx = view.right
        val cy = view.bottom
        val startRadius = 0f
        val endRadius = hypot(view.width.toDouble(), view.height.toDouble()).toFloat()
        val anim: Animator =
            ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, endRadius)

        view.visibility = View.VISIBLE
        anim.duration = 1200
        anim.start()
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {

                val render = Render(this@ZikeySplashActivity)
                render.setAnimation(Flip.InX(_binding!!.txt1));
                render.start();
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }

        })
    }


}