package com.qbs.laafresh.ui.fragments.settings

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.qbs.laafresh.databinding.FragmentWebViewBinding
import com.qbs.laafresh.ui.extension.isConnected

class WebViewFragment : Fragment() {
    private lateinit var binding: FragmentWebViewBinding
    var url = ""
    var mTitle = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url = arguments?.getString("url", "")!!
        mTitle = arguments?.getString("title", "")!!
        initAppBar()
        if (isConnected(requireActivity())) {
            showLoading(true)
            webHandling()
        } else {
            showLoading(false)
        }

    }

    private fun initAppBar() {
        binding.tbWebView?.apply {
            binding.tbWebView.title = mTitle
            (activity as AppCompatActivity).setSupportActionBar(binding.tbWebView)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.title = mTitle
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }

    @SuppressLint("JavascriptInterface")
    private fun webHandling() {
        binding.apply {
            webViewFragment.settings.javaScriptEnabled = true
            webViewFragment.settings.loadWithOverviewMode = true
            webViewFragment.webViewClient = WebView()
            webViewFragment.loadUrl(url)
        }
    }


    inner class WebView : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: android.webkit.WebView?,
            url: String?
        ): Boolean {
            showLoading(false)
            view?.loadUrl(url.toString())
            return true
        }

        override fun onPageStarted(view: android.webkit.WebView?, url: String?, favicon: Bitmap?) {
            if (binding.pbWebView != null) {
                binding.pbWebView.max = 100
                binding.pbWebView.visibility = View.VISIBLE
                binding.pbWebView.progress = 0
            }
        }

        override fun onPageFinished(view: android.webkit.WebView?, url: String?) {
            if (binding.pbWebView != null) {
                binding.pbWebView.visibility = View.GONE
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (binding.pbWebView != null) {
            if (isLoading)
                binding.pbWebView.visibility = View.VISIBLE
            else
                binding.pbWebView.visibility = View.GONE
        }
    }
}