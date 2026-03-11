package com.qbs.laafresh.ui.fragments.settings

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
import com.qbs.laafresh.data.network.api.request.AddAddressRequest
import com.qbs.laafresh.data.network.api.request.GetAddressDetailsRequest
import com.qbs.laafresh.data.network.api.request.PincodeRequest
import com.qbs.laafresh.data.network.api.request.UpdateAddressRequest
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.viewmodel.VMAccount
import com.qbs.laafresh.databinding.FragmentAddAddressBinding
import com.qbs.laafresh.ui.extension.isConnected
import com.qbs.laafresh.ui.extension.toast

class AddAddressFragment : Fragment() {
    private lateinit var binding: FragmentAddAddressBinding
    var addAddressRequest = AddAddressRequest()
    var updateAddressRequest = UpdateAddressRequest()
    var getAddressDetailsRequest = GetAddressDetailsRequest()
    var addressDetails = "";
    val vmAccount: VMAccount by lazy {
        this.let {
            ViewModelProvider(it, VMAccount.Factory(requireContext()))
                .get(VMAccount::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getString("AddressDetails", "") != null) {
            addressDetails = arguments?.getString("AddressDetails", "")!!
            getAddressDetails(addressDetails)
            binding.deleteAddressButton.visibility = View.VISIBLE
            binding.addAddressButton.text = "Update Address"
            binding.deleteAddressButton.setOnClickListener {
                deleteAddress(addressDetails)
            }
        }
        initAppBar()
        binding.addAddressButton.setOnClickListener {
            validateAllFields()
        }
    }

    private fun deleteAddress(addressDetails: String) {
        binding.pbAddAddress.visibility = View.VISIBLE
        getAddressDetailsRequest.id = addressDetails
        vmAccount.deleteAddress(getAddressDetailsRequest, onSuccess = {
            if (it.success == "1") {
                binding.pbAddAddress.visibility = View.GONE
                findNavController().navigate(R.id.action_addAddressFragment_to_settingsFragment,
                    null,
                    navOptions {
                        popUpTo(R.id.addAddressFragment) {
                            inclusive = true
                        }
                    })

            } else {
                binding.pbAddAddress.visibility = View.GONE
            }
        },
            onError = {
                binding.pbAddAddress.visibility = View.GONE
            }
        )
    }

    private fun getAddressDetails(addressDetailsId: String) {
        var getAddressDetailsRequest = GetAddressDetailsRequest();
        getAddressDetailsRequest.id = addressDetailsId
        if (isConnected(requireActivity())) {
            vmAccount.getAddressDetails(getAddressDetailsRequest, onSuccess = {
                binding.etAFirstName.setText(it.address?.get(0)?.name)
                binding.etAddressOne.setText(it.address?.get(0)?.address1)
                binding.etAddressTwo.setText(it.address?.get(0)?.address2)
                binding.etMobile.setText(it.address?.get(0)?.phone)
                binding.etCity.setText(it.address?.get(0)?.city)
                binding.etState.setText(it.address?.get(0)?.state)
                binding.etCountry.setText(it.address?.get(0)?.country)
                binding.etPinCode.setText(it.address?.get(0)?.zip)
                binding.chkDefaultAddress.isChecked =
                    it.address?.get(0)?.defaultAddress.equals("true")
            }, onError = {

            })
        } else {
            activity?.toast("Please Check the your Internet! ")
        }


    }

    private fun initAppBar() {
        binding.tbAddAddress?.apply {
            (activity as AppCompatActivity).setSupportActionBar(binding.tbAddAddress)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun validateAllFields() {
        try {
            when {
                TextUtils.isEmpty(binding.etAFirstName.text) -> binding.etAFirstName.error =
                    getString(R.string.label_add_name)

                TextUtils.isEmpty(binding.etAddressOne.text) -> binding.etAddressOne.error =
                    getString(R.string.label_add_address_one)

                TextUtils.isEmpty(binding.etAddressTwo.text) -> binding.etAddressTwo.error =
                    getString(R.string.label_add_address_two)

                TextUtils.isEmpty(binding.etMobile.text) -> binding.etMobile.error =
                    getString(R.string.label_add_mobile)

                binding.etMobile.text?.length != 10 -> binding.etMobile.error =
                    getString(R.string.label_mobile_length_error)

                TextUtils.isEmpty(binding.etCity.text) -> binding.etCity.error =
                    getString(R.string.label_add_city)

                TextUtils.isEmpty(binding.etState.text) -> binding.etState.error =
                    getString(R.string.label_add_state)

                TextUtils.isEmpty(binding.etCountry.text) -> binding.etCountry.error =
                    getString(R.string.label_add_country)

                TextUtils.isEmpty(binding.etPinCode.text) -> binding.etPinCode.error =
                    getString(R.string.label_add_pinCode)

                else -> {
                    binding.pbAddAddress.visibility = View.VISIBLE
                    var pinCodeRequest = PincodeRequest()
                    pinCodeRequest.zipcode = binding.etPinCode.text.toString()
                    if (arguments?.getString("AddressDetails", "") != null) {
                        addressDetails = arguments?.getString("AddressDetails", "")!!
                        updateAddressRequest.userId =
                            PreferenceManager(requireContext()).getCustomerId()
                        updateAddressRequest.name = binding.etAFirstName.text.toString()
                        updateAddressRequest.address1 = binding.etAddressOne.text.toString()
                        updateAddressRequest.address2 = binding.etAddressTwo.text.toString()
                        updateAddressRequest.phone = binding.etMobile.text.toString()
                        updateAddressRequest.city = binding.etCity.text.toString()
                        updateAddressRequest.state = binding.etState.text.toString()
                        updateAddressRequest.country = binding.etCountry.text.toString()
                        updateAddressRequest.zip = binding.etPinCode.text.toString()
                        updateAddressRequest.langlat = ""
                        updateAddressRequest.defaultAddress =
                            (binding.chkDefaultAddress.isChecked.toString())
                        updateAddressRequest.id = addressDetails
                    } else {
                        addAddressRequest.userId =
                            PreferenceManager(requireContext()).getCustomerId()
                        addAddressRequest.name = binding.etAFirstName.text.toString()
                        addAddressRequest.address1 = binding.etAddressOne.text.toString()
                        addAddressRequest.address2 = binding.etAddressTwo.text.toString()
                        addAddressRequest.phone = binding.etMobile.text.toString()
                        addAddressRequest.city = binding.etCity.text.toString()
                        addAddressRequest.state = binding.etState.text.toString()
                        addAddressRequest.country = binding.etCountry.text.toString()
                        addAddressRequest.zip = binding.etPinCode.text.toString()
                        addAddressRequest.langlat = ""
                        addAddressRequest.defaultAddress =
                            (binding.chkDefaultAddress.isChecked.toString())
                    }

                    vmAccount.pinCode(pinCodeRequest, onSuccess = {
                        if (it.success == "1") {
                            binding.pbAddAddress.visibility = View.GONE
                            if (arguments?.getString("AddressDetails", "") != null) {
                                addressDetails = arguments?.getString("AddressDetails", "")!!
                                updateAddress(updateAddressRequest)
                            } else
                                addAddress(addAddressRequest)
                        } else {
                            activity?.toast(it.message.toString())
                            binding.pbAddAddress.visibility = View.GONE
                        }
                    }, onError = {
                        binding.pbAddAddress.visibility = View.GONE
                    })
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateAddress(updateAddressRequest: UpdateAddressRequest) {
        binding.pbAddAddress.visibility = View.VISIBLE
        vmAccount.updateAddress(updateAddressRequest, onSuccess = {
            if (it.success == "1") {
                binding.pbAddAddress.visibility = View.GONE
                findNavController().navigate(R.id.action_addAddressFragment_to_settingsFragment,
                    null,
                    navOptions {
                        popUpTo(R.id.addAddressFragment) {
                            inclusive = true
                        }
                    })

            } else {
                binding.pbAddAddress.visibility = View.GONE
            }
        },
            onError = {
                binding.pbAddAddress.visibility = View.GONE
            }
        )
    }

    private fun addAddress(addAddressRequest: AddAddressRequest) {
        binding.pbAddAddress.visibility = View.VISIBLE
        vmAccount.addAddress(addAddressRequest, onSuccess = {
            if (it.success == "1") {
                binding.pbAddAddress.visibility = View.GONE
                findNavController().navigate(R.id.action_addAddressFragment_to_settingsFragment,
                    null,
                    navOptions {
                        popUpTo(R.id.addAddressFragment) {
                            inclusive = true
                        }
                    })

            } else {
                binding.pbAddAddress.visibility = View.GONE
            }
        },
            onError = {
                binding.pbAddAddress.visibility = View.GONE
            }
        )
    }
}