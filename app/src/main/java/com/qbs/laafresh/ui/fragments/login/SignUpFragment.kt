package com.qbs.laafresh.ui.fragments.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.qbs.laafresh.R
import com.qbs.laafresh.data.viewmodel.VMRegister
import com.qbs.laafresh.databinding.FragmentSignupBinding
import com.qbs.laafresh.ui.dialog.TermsAndConditionsDialog
import com.qbs.laafresh.ui.extension.toast
import java.util.regex.Pattern

class SignUpFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentSignupBinding
    private val vmRegister: VMRegister by lazy {
        this.let {
            ViewModelProvider(it, VMRegister.Factory(requireContext()))
                .get(VMRegister::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppBar()
        binding.tvFTermsAndCondition.setOnClickListener(this)
        binding.signupFbutton.setOnClickListener(this)
    }

    private fun initAppBar() {
        binding.tbFSignUp?.apply {
            (activity as AppCompatActivity).setSupportActionBar(binding.tbFSignUp)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun validateAllFields() {
        try {
            when {
                TextUtils.isEmpty(binding.etFFirstName.text) -> binding.etFFirstName.error =
                    getString(R.string.error_first_name)

                TextUtils.isEmpty(binding.etFLastName.text) -> binding.etFLastName.error =
                    getString(R.string.error_last_name)

                TextUtils.isEmpty(binding.etFEmail.text) -> binding.etFEmail.error =
                    getString(R.string.error_email)

                TextUtils.isEmpty(binding.etFPassword.text) -> binding.etFPassword.error =
                    getString(R.string.error_password)

                TextUtils.isEmpty(binding.etFMobile.text) -> binding.etFMobile.error =
                    getString(R.string.error_mobile)

                !isValidMobile(phone = binding.etFMobile.text.toString()) -> binding.etFMobile.error =
                    getString(R.string.error_mobile_valid)

                !isValidMail(email = binding.etFEmail.text.toString())
                    -> binding.etFEmail.error = getString(R.string.error_email_valid)

                else -> {
                    binding.pbFRSignUP.visibility = View.VISIBLE
                    vmRegister.signUp(binding.etFFirstName.text.toString(),
                        binding.etFLastName.text.toString(),
                        binding.etFEmail.text.toString(),
                        binding.etFPassword.text.toString(),
                        binding.etFMobile.text.toString(),
                        onSuccess = {
                            binding.pbFRSignUP.visibility = View.GONE
                            if (it.success == "1") {
                                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment,
                                    null,
                                    navOptions {
                                        popUpTo(R.id.loginFragment) {
                                            inclusive = true
                                        }
                                    })
                            } else {
                                binding.pbFRSignUP.visibility = View.GONE
                                activity?.toast(it.message.toString())
                            }

                        },
                        onError = {
                            binding.pbFRSignUP.visibility = View.GONE
                            activity?.toast(it.toString())
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

    private fun isValidMail(email: String): Boolean {
        val EMAIL_STRING = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        return Pattern.compile(EMAIL_STRING).matcher(email).matches()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvFTermsAndCondition -> {
                val terms = TermsAndConditionsDialog {
                    if (it) {
                        binding.cbFTermsAndCondition.isEnabled = true
                        binding.cbFTermsAndCondition.isChecked = it
                    }
                }
                terms.show(childFragmentManager, "")
            }

            R.id.signup_Fbutton -> {
                if (binding.cbFTermsAndCondition.isChecked)
                    validateAllFields()
                else
                    activity?.toast(getString(R.string.error_terms));
            }
        }
    }
}