package com.qbs.laafresh.ui.dialog

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.qbs.laafresh.R
import com.qbs.laafresh.databinding.TermsAndConditionBinding
import com.qbs.laafresh.ui.extension.isConnected
import com.qbs.laafresh.ui.extension.toast

typealias terms = (Boolean) -> Unit

class TermsAndConditionsDialog(var terms: terms) : DialogFragment() {

    private lateinit var binding: TermsAndConditionBinding

    override fun onResume() {
        super.onResume()
        val width = (resources.displayMetrics.widthPixels * 1)
        val height = (resources.displayMetrics.heightPixels * 0.90).toInt()
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setLayout(width, height)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TermsAndConditionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isConnected(requireActivity())) {
            webHandling()
        } else
            activity?.toast(getString(R.string.no_internet))
        binding.linearAcceptTerms.setOnClickListener {
            terms.invoke(true)
            dialog?.dismiss()
        }

        binding.linearCancelTerms.setOnClickListener {
            terms.invoke(false)
            dialog?.dismiss()
        }
    }

    @SuppressLint("JavascriptInterface")
    private fun webHandling() {
        binding.apply {
            wvTermsCondition.settings.javaScriptEnabled = true
            wvTermsCondition.settings.loadWithOverviewMode = true
            wvTermsCondition.webViewClient = WebView()
            val url = "https://laafresh.com/app/terms.php"
            wvTermsCondition.loadUrl(url)
        }
    }


    inner class WebView : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: android.webkit.WebView?,
            url: String?
        ): Boolean {
            view?.loadUrl(url.toString())
            return true
        }

        override fun onPageStarted(view: android.webkit.WebView?, url: String?, favicon: Bitmap?) {
            if (binding.pbTermsCondition != null) {
                binding.pbTermsCondition.max = 100
                binding.pbTermsCondition.visibility = View.VISIBLE
                binding.pbTermsCondition.progress = 0
            }
        }

        override fun onPageFinished(view: android.webkit.WebView?, url: String?) {
            if (binding.pbTermsCondition != null) {
                binding.pbTermsCondition.visibility = View.GONE
            }
        }
    }
}