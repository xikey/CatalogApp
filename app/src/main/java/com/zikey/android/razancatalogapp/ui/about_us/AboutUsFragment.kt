package com.zikey.android.razancatalogapp.ui.about_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.razanpardazesh.com.resturantapp.tools.FontChanger
import com.zikey.android.razancatalogapp.databinding.FragmentAboutUsBinding


class AboutUsFragment : Fragment() {

    private var _binding: FragmentAboutUsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(AboutUsViewModel::class.java)

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
        FontChanger().applyTitleFont(binding.lyDetail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}