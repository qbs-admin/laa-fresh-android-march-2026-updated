package com.qbs.laafresh.ui.fragments.login

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.qbs.laafresh.R
import com.qbs.laafresh.data.viewmodel.VMRegister
import com.qbs.laafresh.databinding.FragmentForgetBinding
import com.qbs.laafresh.ui.extension.toast
import java.util.regex.Pattern

class ForgetFragment : Fragment() {
    private lateinit var binding: FragmentForgetBinding
    val vmRegister: VMRegister by lazy {
        this.let {
            ViewModelProvider(it, VMRegister.Factory(requireContext()))
                .get(VMRegister::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppBar()
        binding.sendFbutton.setOnClickListener {
            forgotPasswordFieldValidation()
        }
    }

    private fun initAppBar() {
        binding.tbFfrogetpassword.apply {
            (activity as AppCompatActivity).setSupportActionBar(binding.tbFfrogetpassword)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun forgotPasswordFieldValidation() {
        try {
            when {
                TextUtils.isEmpty(binding.etFForgotPassword.text) -> binding.etFForgotPassword.error =
                    getString(R.string.label_error_email)

                !isValidMail(email = binding.etFForgotPassword.text.toString())
                    -> binding.etFForgotPassword.error = getString(R.string.error_email_valid)

                else -> {
                    binding.pbFForgotPassword.visibility = View.VISIBLE
                    vmRegister.forgotPassword(binding.etFForgotPassword!!.text.toString(),
                        onSuccess = {
                            binding.pbFForgotPassword.visibility = View.GONE
                            if (it.success == "1") {
                                findNavController().navigate(R.id.action_forgetFragment_to_loginFragment,
                                    null,
                                    navOptions {
                                        popUpTo(R.id.loginFragment) {
                                            inclusive = true
                                        }
                                    })
                            } else {
                                binding.pbFForgotPassword.visibility = View.GONE
                                activity?.toast(it.message.toString())
                            }

                        }, onError = {
                            binding.pbFForgotPassword.visibility = View.GONE
                        })
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isValidMail(email: String): Boolean {
        return Pattern.compile(
            ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        ).matcher(email).matches()
    }
}