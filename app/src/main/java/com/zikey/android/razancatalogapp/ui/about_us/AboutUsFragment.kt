package com.zikey.android.razancatalogapp.ui.about_us

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.*
import com.karumi.dexter.listener.single.CompositePermissionListener
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.R
import com.zikey.android.razancatalogapp.core.ImageViewWrapper
import com.zikey.android.razancatalogapp.core.ScreenSize
import com.zikey.android.razancatalogapp.databinding.FragmentAboutUsBinding
import com.zikey.android.razancatalogapp.model.CompanyInformation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AboutUsFragment : Fragment() {

    private var _binding: FragmentAboutUsBinding? = null
    private val viewModel: AboutUsViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var companyInformation: CompanyInformation? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FontChanger().applyMainFont(binding.root)
        initProgress()
        initTopTenSize()
        initProductGroupsObserver()
        getData()
        initListeners()

    }

    private fun initTopTenSize() {

        val box: View = binding.lyTop
        val width = ScreenSize.width
        val height = (width * 60) / 100
        val params = box.layoutParams
        params.height = height

        box.layoutParams = params
    }


    private fun initListeners() {

        binding.imgEmail.setOnClickListener {
            try {

                if (companyInformation == null)
                    return@setOnClickListener

                if (companyInformation!!.details.isNullOrEmpty())
                    return@setOnClickListener

                val dt = companyInformation!!.details!!.find { it.name!! == "پست الکترونیکی" }
                dt?.value?.let { it1 -> openEmailIntent(it1) }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.imgWebSite.setOnClickListener {
            try {

                if (companyInformation == null)
                    return@setOnClickListener

                if (companyInformation!!.details.isNullOrEmpty())
                    return@setOnClickListener

                val dt = companyInformation!!.details!!.find { it.name!! == "وبسایت" }
                dt?.value?.let { it1 -> openWebBrowser(it1) }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        binding.imgInstagram.setOnClickListener {
            try {

                if (companyInformation == null)
                    return@setOnClickListener

                if (companyInformation!!.details.isNullOrEmpty())
                    return@setOnClickListener

                val dt = companyInformation!!.details!!.find { it.name!! == "اینستاگرام" }
                dt?.value?.let { it1 -> openInstagramBrowser(it1) }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.imgCall.setOnClickListener {
            try {

                if (companyInformation == null)
                    return@setOnClickListener

                if (companyInformation!!.details.isNullOrEmpty())
                    return@setOnClickListener

                initCallPermission()


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.imgLocation.setOnClickListener {
            try {

                if (companyInformation == null)
                    return@setOnClickListener

                if (companyInformation!!.latitude.isNullOrEmpty() || companyInformation!!.longitude.isNullOrEmpty())
                    return@setOnClickListener

                openMapIntent(companyInformation!!.latitude!!, companyInformation!!.longitude!!)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        companyInformation = null
    }

    private fun getData() {

        viewModel.getInfo()
    }

    private fun initProductGroupsObserver() {

        if (viewModel.dataResponse.hasObservers())
            return
        viewModel.dataResponse.observe(
            viewLifecycleOwner,
            Observer { response ->
                response?.let {

                    if (response.data != null) {

                        companyInformation = response.data
                        initContents()
                    }

                }
            })

        viewModel.dataError.observe(
            viewLifecycleOwner,
            Observer { dataError ->
                dataError?.let {

                    if (dataError) {
//                        viewModel.getAdvertises(requireContext())
                    }

                }
            })


    }

    fun openEmailIntent(mail: String) {

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$mail")
        }
        startActivity(Intent.createChooser(emailIntent, "Send feedback"))
    }

    fun openCallIntent(number: String) {

        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$number")
        }
        startActivity(intent)
    }


    fun openMapIntent(latitude: String, longitude: String) {
        // Create a Uri from an intent string. Use the result to create an Intent.
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(yadegar)")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        startActivity(mapIntent)
    }

    fun openWebBrowser(address: String) {
        var url = address
        if (!url.contains("http")) {
            url = "http://" + address
        }
        val browserIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(browserIntent)
    }

    fun openInstagramBrowser(address: String) {

        val url = "http://www.instagram.com/" + address.removePrefix("@")
        openWebBrowser(url)

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

    private fun initCallPermission() {

        val dialogPermissionListener: PermissionListener = DialogOnDeniedPermissionListener.Builder
            .withContext(context)
            .withTitle("مجوز دسترسی به تماس")
            .withMessage("جهت برقراری تماس نیاز به مجوز دسترسی به تماس دارید")
            .withButtonText("باشه")
            .withIcon(R.drawable.ic_launcher_background)
            .build()

        val permissionListener = object : PermissionListener {

            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                val dt = companyInformation!!.details!!.find { it.name!! == "شماره تماس" }
                dt?.value?.let { it1 -> openCallIntent(it1) }
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                if (p0!!.isPermanentlyDenied) {
                    val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireActivity().getPackageName(), null)
                    intent.data = uri
                    startActivity(intent)
                }

            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                Toast.makeText(requireActivity(), "show denied", Toast.LENGTH_SHORT).show()
                p1!!.continuePermissionRequest()
            }

        }

        val listeners: PermissionListener =
            CompositePermissionListener(permissionListener, dialogPermissionListener)

        Dexter.withContext(requireActivity())
            .withPermission(Manifest.permission.CALL_PHONE)
            .withListener(listeners)
            .withErrorListener(object : PermissionRequestErrorListener {
                override fun onError(p0: DexterError?) {
                    Toast.makeText(requireActivity(), "error", Toast.LENGTH_SHORT).show()
                }

            })
            .check()
    }


    fun initContents() {

        if (companyInformation == null)
            return

        ImageViewWrapper(requireContext()).FromUrl(companyInformation!!.imageUrl)
            .into(binding.imgAvatar).defaultImage(R.drawable.img_yadegar_loader).load()


        if (companyInformation!!.details.isNullOrEmpty())
            return

        try {
            binding.txtComment.setText(companyInformation!!.details!!.find { it.name!! == "درباره ما" }!!.value)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

}