package com.qbs.laafresh.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.qbs.laafresh.R
import com.qbs.laafresh.data.viewmodel.VMRegister
import com.qbs.laafresh.databinding.ActivityRegisterBinding
import com.qbs.laafresh.ui.dialog.TermsAndConditionsDialog
import com.qbs.laafresh.ui.extension.toast
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity(), View.OnClickListener,
    AppBarLayout.OnOffsetChangedListener {

    private lateinit var binding: ActivityRegisterBinding
    private val vmRegister: VMRegister by lazy {
        this.let {
            ViewModelProvider(it, VMRegister.Factory(this))
                .get(VMRegister::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.collapsingToolbar.visibility = View.VISIBLE
        binding.collapsingToolbar.title = " "
        binding.appBarLayout.addOnOffsetChangedListener(this)
        binding.tvTermsAndCondition.setOnClickListener(this)
        binding.signupButton.setOnClickListener(this)
    }


    private fun validateAllFields() {
        try {
            when {
                TextUtils.isEmpty(binding.etFirstName.text) -> binding.etFirstName.error =
                    getString(R.string.error_first_name)

                TextUtils.isEmpty(binding.etLastName.text) -> binding.etLastName.error =
                    getString(R.string.error_last_name)

                TextUtils.isEmpty(binding.etEmail.text) -> binding.etEmail.error =
                    getString(R.string.error_email)

                TextUtils.isEmpty(binding.etPassword.text) -> binding.etPassword.error =
                    getString(R.string.error_password)

                TextUtils.isEmpty(binding.etMobile.text) -> binding.etMobile.error =
                    getString(R.string.error_mobile)

                !isValidMobile(phone = binding.etMobile.text.toString()) -> binding.etMobile.error =
                    getString(R.string.error_mobile_valid)

                !isValidMail(email = binding.etEmail.text.toString())
                    -> binding.etEmail.error = getString(R.string.error_email_valid)

                else -> {
                    binding.pbRegisterLogin.visibility = View.VISIBLE
                    vmRegister.signUp(
                        binding.etFirstName.text.toString(),
                        binding.etLastName.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString(),
                        binding.etMobile.text.toString(),
                        onSuccess = {
                            binding.pbRegisterLogin.visibility = View.GONE
                            if (it.success == "1") {
                                /*  PreferenceManager(this).setIsLoggedIn(true)
                                  PreferenceManager(this).setCustomerId(it.user_id.toString())*/
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                toast(it.message.toString())
                            }

                        },
                        onError = {
                            binding.pbRegisterLogin.visibility = View.GONE
                            toast(it.toString())

                        })
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isValidMobile(phone: String): Boolean {
        return if (phone.length != 10) false
        else Patterns.PHONE.matcher(phone).matches()
    }

    private fun isValidMail(email: String): Boolean =
        Pattern.compile(
            ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        ).matcher(email).matches()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvTermsAndCondition -> {
                val terms = TermsAndConditionsDialog {
                    if (it) {
                        binding.cbTermsAndCondition.isEnabled = true
                        binding.cbTermsAndCondition.isChecked = it
                    }
                }
                terms.show(supportFragmentManager, "")

            }

            R.id.signup_button -> {
                if (binding.cbTermsAndCondition.isChecked)
                    validateAllFields()
                else
                    toast(getString(R.string.error_terms));
            }
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