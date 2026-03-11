package com.qbs.laafresh.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.qbs.laafresh.R
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.viewmodel.VMUser
import com.qbs.laafresh.databinding.ActivityLoginBinding
import com.qbs.laafresh.ui.DashBoardActivity
import com.qbs.laafresh.ui.extension.toast
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    private lateinit var binding: ActivityLoginBinding
    private val vmUser: VMUser by lazy {
        this.let {
            ViewModelProvider(it, VMUser.Factory(this))
                .get(VMUser::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.collapsingToolbar.visibility = View.VISIBLE
        binding.collapsingToolbar.title = " "
        binding.appBarLayout.addOnOffsetChangedListener(this)
        binding.skipBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, DashBoardActivity::class.java))
        }
        binding.loginButton.setOnClickListener {
            validateAllFields()
        }
        binding.tvForgot.setOnClickListener {
            startActivity(Intent(this, ForgetActivity::class.java))
        }
        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        moveToDashBoard()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure?")
            .setPositiveButton("yes",
                DialogInterface.OnClickListener { dialog, which ->
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }).setNegativeButton("no", null).show()
    }


    private fun validateAllFields() {
        try {
            when {
                TextUtils.isEmpty(binding.etEmailId.text) -> binding.etEmailId.error =
                    getString(R.string.label_error_email)

                TextUtils.isEmpty(binding.etPassword.text) -> binding.etPassword.error =
                    getString(R.string.label_error_password)

                !isValidMail(email = binding.etEmailId.text.toString())
                    -> binding.etEmailId.error = getString(R.string.error_email_valid)

                else -> {
                    binding.pbUserLogin.visibility = View.VISIBLE
                    vmUser.userLogin(
                        binding.etEmailId.text.toString(),
                        binding.etPassword.text.toString(), onSuccess = {
                            binding.pbUserLogin.visibility = View.GONE
                            if (it.success == "1") {
                                PreferenceManager(this).setIsLoggedIn(true)
                                PreferenceManager(this).setCustomerId(it.user_id.toString())
                                finish()
                                startActivity(Intent(this, DashBoardActivity::class.java))
                            } else {
                                toast(it.message.toString())
                            }


                        }, onError = {
                            binding.pbUserLogin.visibility = View.GONE
                            toast(it.toString())
                        });

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isValidMail(email: String): Boolean {
        val EMAIL_STRING = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        return Pattern.compile(EMAIL_STRING).matcher(email).matches()
    }

    private fun moveToDashBoard() {
        if (PreferenceManager(this).getIsLoggedIn()) {
            finish()
            startActivity(Intent(this, DashBoardActivity::class.java))
        }

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        runOnUiThread {
            var scrollRange = -1
            if (scrollRange == -1) {
                scrollRange = appBarLayout?.totalScrollRange!!
            }

            if (scrollRange + verticalOffset == 0) {
                binding.tvCollpasing.text = ""
                binding.tvToolbar.text = getString(R.string.app_name)

            } else {
                binding.tvCollpasing.text = ""
                binding.tvToolbar.text = ""
            }
        }
    }
}