package com.qbs.laafresh.ui.fragments.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.qbs.laafresh.R
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.viewmodel.VMUser
import com.qbs.laafresh.databinding.FragmentLoginBinding
import com.qbs.laafresh.ui.extension.toast
import java.util.regex.Pattern

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    val vmUser: VMUser by lazy {
        this.let {
            ViewModelProvider(it, VMUser.Factory(requireContext()))
                .get(VMUser::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppBar()
        binding.loginButton.setOnClickListener {
            validateAllFields()
        }
        binding.tvForgot.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_forgetFragment
            )
        }
        binding.signupBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_signUpFragment
            )
        }

    }

    private fun validateAllFields() {
        try {
            when {
                TextUtils.isEmpty(binding.etfEmailId!!.text) -> binding.etfEmailId.error =
                    getString(R.string.label_error_email)

                TextUtils.isEmpty(binding.etfPassword!!.text) -> binding.etfPassword.error =
                    getString(R.string.label_error_password)

                !isValidMail(email = binding.etfEmailId.text.toString())
                    -> binding.etfEmailId.error = getString(R.string.error_email_valid)

                else -> {
                    showLoading(true)
                    vmUser.userLogin(binding.etfEmailId!!.text.toString(),
                        binding.etfPassword!!.text.toString(), onSuccess = {
                            showLoading(false)
                            if (it.success == "1") {
                                PreferenceManager(requireContext()).setIsLoggedIn(true)
                                PreferenceManager(requireContext()).setCustomerId(it.user_id.toString())
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment,
                                    null,
                                    navOptions {
                                        popUpTo(R.id.homeFragment) {
                                            inclusive = true
                                        }
                                    })

                            } else {
                                Log.e("response>>>", it.message.toString())
                                showLoading(false)
                                activity?.toast(it.message.toString())
                            }

                        }, onError = {
                            Log.e("response", it.toString())
                            showLoading(false)
                            activity?.toast(it.toString())
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

    private fun initAppBar() {
        binding.toolFLogin?.apply {
            (activity as AppCompatActivity).setSupportActionBar(binding.toolFLogin)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (binding.pbfUserLogin != null) {
            if (isLoading)
                binding.pbfUserLogin.visibility = View.VISIBLE
            else
                binding.pbfUserLogin.visibility = View.GONE
        }
    }
}