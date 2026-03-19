package com.qbs.laafresh.ui.fragments.checkout

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.dbhelper.LaaFreshDB
import com.qbs.laafresh.databinding.FragmentWebViewBinding
import com.qbs.laafresh.ui.extension.isConnected
import com.qbs.laafresh.ui.extension.toast
import com.qbs.laafresh.ui.extension.toastLong


class InstaMojoWebViewFragment : Fragment() {
    private lateinit var binding: FragmentWebViewBinding
    var url = ""
    var mTitle = ""
    var db: LaaFreshDAO? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            v.setPadding(0, 0, 0, bottomInset)
            insets
        }



        db = LaaFreshDB.getInstance(context = requireContext())?.laaFreshDAO()
        url = arguments?.getString("url", "")!!
        // url = "https://www.instamojo.com/@lavenderwhite665/edda2b03d2c649efb9b7ff0ed16f9a6b"

        Log.e("TAG","------ INSTA")
        Log.e("TAG",url.toString())

        mTitle = arguments?.getString("title", "")!!
        initAppBar()
        if (isConnected(requireActivity())) {
            showLoading(true)
            //  initWebView()
            binding.webViewFragment.webChromeClient = object : WebChromeClient() {

                override fun onCreateWindow(
                    view: android.webkit.WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: android.os.Message?
                ): Boolean {

                    val newWebView = android.webkit.WebView(view?.context!!)

                    newWebView.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: android.webkit.WebView?,
                            request: WebResourceRequest?
                        ): Boolean {

                            val url = request?.url.toString()
                            Log.e("POPUP_URL>>>", url)

                            if (url.startsWith("upi://") || url.startsWith("intent://")) {
                                try {
                                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                                    startActivity(intent)
                                    return true
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

                            return false
                        }
                    }

                    val transport = resultMsg?.obj as WebView.WebViewTransport
                    transport.webView = newWebView
                    resultMsg.sendToTarget()

                    return true
                }
            }
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

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    private fun webHandling() {
        binding.apply {
            webViewFragment.settings.loadWithOverviewMode = true

            webViewFragment.webViewClient = MyWebViewClient()
            webViewFragment.setInitialScale(100)
            webViewFragment.clearCache(true)
            webViewFragment.clearHistory()
            webViewFragment.settings.builtInZoomControls = true
            webViewFragment.settings.useWideViewPort = true
            webViewFragment.isVerticalScrollBarEnabled = true
            webViewFragment.isHorizontalScrollBarEnabled = true
            webViewFragment.settings.displayZoomControls = false
            webViewFragment.settings.setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36")
            webViewFragment.settings.javaScriptEnabled = true
            webViewFragment.settings.setSupportMultipleWindows(true)
            webViewFragment.settings.javaScriptCanOpenWindowsAutomatically = true
            webViewFragment.settings.javaScriptCanOpenWindowsAutomatically
            webViewFragment.settings.cacheMode = WebSettings.LOAD_DEFAULT
            webViewFragment.settings.setGeolocationEnabled(true)
            webViewFragment.settings.allowFileAccess = true
            webViewFragment.settings.allowContentAccess = true
            webViewFragment.settings.loadsImagesAutomatically = true
            webViewFragment.settings.domStorageEnabled = true
            webViewFragment.settings.allowFileAccessFromFileURLs = true
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(webViewFragment, true);
            } else {
                CookieManager.getInstance().setAcceptCookie(true);
            }
            webViewFragment.loadUrl(url)
        }
    }

    private fun initWebView() {
        binding.webViewFragment.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                view: android.webkit.WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Log.d(":webview:::::", ":onJsAlert:" + url)

                return super.onJsAlert(view, url, message, result)
            }


            override fun onJsConfirm(
                view: android.webkit.WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Log.d(":webview:::::", ":onJsConfirm:" + url)

                return super.onJsConfirm(view, url, message, result)
            }

            override fun onJsPrompt(
                view: android.webkit.WebView?,
                url: String?,
                message: String?,
                defaultValue: String?,
                result: JsPromptResult?
            ): Boolean {
                Log.d(":webview:::::", ":onJsPrompt:" + url)

                return super.onJsPrompt(view, url, message, defaultValue, result)
            }

            override fun onJsBeforeUnload(
                view: android.webkit.WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Log.d(":webview:::::", ":onJsBeforeUnload:" + url)

                return super.onJsBeforeUnload(view, url, message, result)
            }

            override fun onProgressChanged(view: android.webkit.WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                //progressBar.progress = if (newProgress <= 100) newProgress else 100

            }

        }
    }

    inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: android.webkit.WebView?,
            request: WebResourceRequest?
        ): Boolean {

            val url = request?.url.toString()
            Log.e("URL_NEW>>>", url)

            if (url.contains("paymentredirect.php")) {
                parseAndDisplayData(url)
                return false
            }

            if (url.startsWith("upi://") || url.startsWith("intent://")) {
                try {
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    startActivity(intent)
                    return true
                } catch (e: Exception) {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }

            return false
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

        override fun onReceivedError(
            view: android.webkit.WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            try {
                binding.webViewFragment.stopLoading()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (binding.webViewFragment.canGoBack()) {
                binding.webViewFragment.goBack()
            }
            showError(requireContext(), errorCode)
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceivedError(
            view: android.webkit.WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            try {
                binding.webViewFragment.stopLoading()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (binding.webViewFragment.canGoBack()) {
                binding.webViewFragment.goBack()
            }
            showError(requireContext(), error?.errorCode!!)
        }
    }

    private fun showError(mContext: Context, errorCode: Int) {
        //Prepare message
        var message: String? = null
        var title: String? = null
        if (errorCode == WebViewClient.ERROR_AUTHENTICATION) {
            message = "User authentication failed on server"
            title = "Auth Error"
        } else if (errorCode == WebViewClient.ERROR_TIMEOUT) {
            message = "The server is taking too much time to communicate. Try again later."
            title = "Connection Timeout"
        } else if (errorCode == WebViewClient.ERROR_TOO_MANY_REQUESTS) {
            message = "Too many requests during this load"
            title = "Too Many Requests"
        } else if (errorCode == WebViewClient.ERROR_UNKNOWN) {
            message = "Generic error"
            title = "Unknown Error"
        } else if (errorCode == WebViewClient.ERROR_BAD_URL) {
            message = "Check entered URL.."
            title = "Malformed URL"
        } else if (errorCode == WebViewClient.ERROR_CONNECT) {
            message = "Failed to connect to the server"
            title = "Connection"
        } else if (errorCode == WebViewClient.ERROR_FAILED_SSL_HANDSHAKE) {
            message = "Failed to perform SSL handshake"
            title = "SSL Handshake Failed"
        } else if (errorCode == WebViewClient.ERROR_HOST_LOOKUP) {
            message = "Server or proxy hostname lookup failed"
            title = "Host Lookup Error"
        } else if (errorCode == WebViewClient.ERROR_PROXY_AUTHENTICATION) {
            message = "User authentication failed on proxy"
            title = "Proxy Auth Error"
        } else if (errorCode == WebViewClient.ERROR_REDIRECT_LOOP) {
            message = "Too many redirects"
            title = "Redirect Loop Error"
        } else if (errorCode == WebViewClient.ERROR_UNSUPPORTED_AUTH_SCHEME) {
            message = "Unsupported authentication scheme (not basic or digest)"
            title = "Auth Scheme Error"
        } else if (errorCode == WebViewClient.ERROR_UNSUPPORTED_SCHEME) {
            message = "Unsupported URI scheme"
            title = "URI Scheme Error"
        } else if (errorCode == WebViewClient.ERROR_FILE) {
            message = "Generic file error"
            title = "File"
        } else if (errorCode == WebViewClient.ERROR_FILE_NOT_FOUND) {
            message = "File not found"
            title = "File"
        } else if (errorCode == WebViewClient.ERROR_IO) {
            message = "The server failed to communicate. Try again later."
            title = "IO Error"
        }
        if (message != null) {
            AlertDialog.Builder(mContext)
                .setMessage(message)
                .setTitle(title)
                .setIcon(R.drawable.ic_error)
                .setCancelable(false)
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        /* setResult(RESULT_CANCELED)
                         finish()*/
                    }).show()
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


    private fun paymentFailed(check: Boolean) {
        binding.webViewFragment.visibility = View.GONE
        binding.tbWebView.visibility = View.GONE
        binding.paymentAnimationView.visibility = View.VISIBLE
        try {
            Handler(Looper.getMainLooper()).postDelayed({
                if (check) activity?.toastLong(
                    " Why did it fail?\n" +
                            "\n1.Incorrect CVV or Expiry date.\n + 2.Network problems with your payment method."
                )
                else activity?.toast("Payment Failure!")
                findNavController().navigate(R.id.action_instaMojoWebViewFragment_to_checkOutFragment,
                    null,
                    navOptions {
                        popUpTo(R.id.checkOutFragment) {
                            inclusive = true
                        }
                    })
            }, 2000)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    private fun parseAndDisplayData(url: String) {
        if (url.contains("Credit")) {
            binding.webViewFragment.visibility = View.GONE
            binding.tbWebView.visibility = View.GONE
            binding.successAnimationView.visibility = View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                db?.deleteAllMyCardItem()
                activity?.toast("thanks for your order . We will get back to you soon")
                findNavController().navigate(R.id.homeFragment, null,
                    navOptions {
                        popUpTo(R.id.homeFragment) {
                            inclusive = true
                        }
                    })
            }, 2000)

        } else {
            paymentFailed(false)
        }


    }


}