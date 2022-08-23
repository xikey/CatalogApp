package com.zikey.android.razancatalogapp.ui.splash

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.*
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.R
import com.zikey.android.razancatalogapp.databinding.ActivityZikeySplashBinding
import com.zikey.android.razancatalogapp.databinding.FragmentHomeBinding
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.zikey.android.razancatalogapp.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class ZikeySplashActivity : AppCompatActivity() {


    private var _binding: ActivityZikeySplashBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var fadeInAnimation: Animation? = null
    var fadeOutAnimation: Animation? = null
    var englishTitleSlidedOut = false

    /**
     * Duration of wait
     */
    private val SPLASH_DISPLAY_LENGTH = 6000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar();

        _binding = ActivityZikeySplashBinding.inflate(layoutInflater)


        setContentView(binding.root)

    }


    override fun onStart() {
        super.onStart()

      initAnimation()
        runHandler()
        fadeInImageAnimator()
        fadeOutImageAnimator()
        slideInEnglishTitle()
        slideInPersianTitle()
    }

    //جهت تعیین زمان نمایش صفحه خوشامدگویی
    private fun runHandler() {
        Handler().postDelayed(Runnable {
//            openLoginActivity()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }


    private fun initAnimation() {
        val animationDrawable = binding.layoutRoot.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(10)
        animationDrawable.setExitFadeDuration(1800)
        animationDrawable.start()
    }

    fun fadeInImageAnimator() {

        fadeInAnimation = AnimationUtils.loadAnimation(
            this,
            R.anim.fade_in
        )
        fadeInAnimation!!.interpolator = AccelerateInterpolator()
        fadeInAnimation!!.duration = 2000L
        fadeInAnimation!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {

                binding.imgWhiteAvatar.startAnimation(fadeOutAnimation)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })

        binding.imgWhiteAvatar.startAnimation(fadeInAnimation)


    }

    fun fadeOutImageAnimator() {

        fadeOutAnimation = AnimationUtils.loadAnimation(
            this,
            R.anim.fade_out
        )
        fadeOutAnimation?.interpolator = AccelerateInterpolator()
        fadeOutAnimation!!.duration = 2000L
        fadeOutAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.imgWhiteAvatar.startAnimation(fadeInAnimation)
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })


    }

    fun slideInEnglishTitle() {
        val animation: Animation = AnimationUtils.loadAnimation(
            this,
            R.anim.slide_in
        )
        animation.startOffset = 1000
        animation.interpolator = AnticipateOvershootInterpolator()
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                if (!englishTitleSlidedOut) {
                    slideOutEnglishTitle()
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })

        binding.imgYadegarEngText.startAnimation(animation)


    }

    fun slideOutEnglishTitle() {
        val animation: Animation = AnimationUtils.loadAnimation(
            this,
            R.anim.slide_out
        )
        animation.startOffset = 1000
        animation.interpolator = AnticipateOvershootInterpolator()

        binding.imgYadegarEngText.startAnimation(animation)
        englishTitleSlidedOut = true

    }


    fun slideInPersianTitle() {
        val animation: Animation = AnimationUtils.loadAnimation(
            this,
            R.anim.slide_in
        )
        animation.startOffset = 3000
        animation.interpolator = AnticipateOvershootInterpolator()
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                CoroutineScope(Main).launch {
                    MainActivity.start(this@ZikeySplashActivity)
                    finish()  }

            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        binding.imgYadegarFarsiText.startAnimation(animation)


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


}