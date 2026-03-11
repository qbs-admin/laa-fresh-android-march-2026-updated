package com.qbs.laafresh.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.qbs.laafresh.R
import com.qbs.laafresh.data.viewmodel.VMRegister
import com.qbs.laafresh.databinding.ActivityForgetBinding
import com.qbs.laafresh.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class ForgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetBinding
    private val vmRegister: VMRegister by lazy {
        this.let {
            ViewModelProvider(it, VMRegister.Factory(this))
                .get(VMRegister::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sendButton.setOnClickListener {
            forgotPasswordFieldValidation()
        }
    }

    private fun forgotPasswordFieldValidation() {
        try {
            when {
                TextUtils.isEmpty(binding.etForgotPassword.text) -> binding.etForgotPassword.error =
                    getString(R.string.label_error_email)

                !isValidMail(email = binding.etForgotPassword.text.toString())
                    -> binding.etForgotPassword.error = getString(R.string.error_email_valid)

                else -> {
                    binding.pbForgotPassword.visibility = View.VISIBLE
                    vmRegister.forgotPassword(binding.etForgotPassword!!.text.toString(),
                        onSuccess = {
                            binding.pbForgotPassword.visibility = View.GONE
                            if (it.success == "1") {
                                finish()
                                startActivity(Intent(this, LoginActivity::class.java))
                            }

                        }, onError = {
                            binding.pbForgotPassword.visibility = View.GONE
                        })
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isValidMail(email: String): Boolean =
        Pattern.compile(
            ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        ).matcher(email).matches()

}