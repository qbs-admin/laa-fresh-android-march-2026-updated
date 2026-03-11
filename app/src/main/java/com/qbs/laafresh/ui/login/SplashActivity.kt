package com.qbs.laafresh.ui.login

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.qbs.laafresh.BuildConfig
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.viewmodel.VMProduct
import com.qbs.laafresh.databinding.ActivitySplashBinding
import com.qbs.laafresh.ui.DashBoardActivity
import com.qbs.laafresh.ui.extension.errorResponseMessage
import com.qbs.laafresh.ui.extension.isConnected
import com.qbs.laafresh.ui.extension.toast

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val vmProduct: VMProduct by viewModels { VMProduct.Factory(this) }
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        proceedAfterPermissions(isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)

        if (isConnected(this)) {
            checkPermissionsAndProceed()
        } else {
            showNoInternetError()
        }
//        checkAutoUpdate()
    }

    private fun checkPermissionsAndProceed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            Log.e("TAG"," ----------  1 ---------")
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Already has permission
                    checkAutoUpdate()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    showPermissionRationale()
                }

                else -> {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
//            Log.e("TAG"," ----------  2 ---------")
            // No notification permission needed for older versions
            checkAutoUpdate()
        }
    }

    private fun showPermissionRationale() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.notification_permission_title))
            .setMessage(getString(R.string.notification_permission_message))
            .setPositiveButton(getString(R.string.continue_text)) { _, _ ->
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton(getString(R.string.skip), null)
            .setOnDismissListener {
                // Proceed even if user dismisses without granting
                checkAutoUpdate()
            }
            .show()
    }

    private fun proceedAfterPermissions(notificationPermissionGranted: Boolean) {
        if (!notificationPermissionGranted) {
            // Optional: Log or track that user declined notifications
            Toast.makeText(
                this,
                getString(R.string.notifications_disabled_warning),
                Toast.LENGTH_SHORT
            ).show()
        }
        checkAutoUpdate()
    }

    private fun checkAutoUpdate() {
        vmProduct.getAutoUpdate(
            onSuccess = { updateInfo ->
                updateInfo.appVersion?.toFloatOrNull()?.let { latestVersion ->
                    if (BuildConfig.VERSION_CODE < latestVersion) {
                        showAppUpdateDialog()
                    } else {
                        proceedToNextScreen()
                    }
                } ?: proceedToNextScreen() // If version parsing fails, proceed anyway
            },
            onError = { error ->
                toast(errorResponseMessage(error))
                proceedToNextScreen()
            }
        )
    }

    private fun showAppUpdateDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.update_available_title))
            .setMessage(getString(R.string.update_available_message))
            .setPositiveButton(getString(R.string.update)) { _, _ ->
                redirectToPlayStore()
                finish()
            }
            .setNegativeButton(getString(R.string.later)) { _, _ ->
                proceedToNextScreen()
            }
            .setCancelable(false)
            .show()
    }

    private fun redirectToPlayStore() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }

    private fun proceedToNextScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val destination = if (PreferenceManager(this).getIsLoggedIn()) {
                DashBoardActivity::class.java
            } else {
                LoginActivity::class.java
            }
            startActivity(Intent(this, destination))
            finish()
        }, 3000)
    }

    private fun showNoInternetError() {
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        proceedToNextScreen()
    }

    private fun showErrorToast(error: Throwable) {
        Toast.makeText(this, errorResponseMessage(error), Toast.LENGTH_SHORT).show()
    }
}