package com.qbs.laafresh.ui.fragments.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.dbhelper.LaaFreshDB
import com.qbs.laafresh.data.network.api.reponse.AddressItem
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.viewmodel.VMAccount
import com.qbs.laafresh.databinding.FragmentSettingsBinding
import com.qbs.laafresh.ui.DashBoardActivity
import com.qbs.laafresh.ui.login.LoginActivity
import com.qbs.laafresh.ui.adapter.AddressListAdapter
import com.qbs.laafresh.ui.extension.isConnected
import com.qbs.laafresh.ui.extension.showDialAlert
import com.qbs.laafresh.ui.extension.toast

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    var db: LaaFreshDAO? = null
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
        binding = FragmentSettingsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = LaaFreshDB.getInstance(context = requireContext())?.laaFreshDAO()
        binding.pbSetting.visibility = View.VISIBLE
        binding.pbSetting.visibility = View.VISIBLE
        vmAccount.getProfile(onSuccess = {
            binding.accountView.visibility = View.VISIBLE
            binding.pbSetting.visibility = View.GONE
            binding.contentProfile.tvCustomerName.text = "Name: ${it.username}"
            binding.contentProfile.tvCustomerEmail.text = "Email Id: ${it.email}"
            binding.contentProfile.tvCustomerPhone.text = "Phone No: ${it.phone}"
        }, onError = {
            binding.pbSetting.visibility = View.GONE
        })
        vmAccount.getAddress(
            onSuccess = {
                setAddressAdapter(it.address as List<AddressItem>)

            },
            onError = {

            }
        )
        binding.tvAddNewAddress.setOnClickListener {
            findNavController().navigate(R.id.action_addAddressFragment_to_settingsFragment)
        }
        binding.tvReturnPolicy.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", "https://laafresh.com/app/return.php")
            bundle.putString("title", "Return Policy")
            findNavController().navigate(R.id.action_addAddressFragment_to_webViewFragment, bundle)
        }
        binding.tvContactUs.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", "https://laafresh.com/app/faq.php")
            bundle.putString("title", "faq")
            findNavController().navigate(R.id.action_addAddressFragment_to_webViewFragment, bundle)
        }
        binding.tvAboutUs.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", "https://laafresh.com/app/about.php")
            bundle.putString("title", "About Us")
            findNavController().navigate(R.id.action_addAddressFragment_to_webViewFragment, bundle)
        }
        binding.tvPrivacy.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", "https://laafresh.com/app/privacy.php")
            bundle.putString("title", "Privacy")
            findNavController().navigate(R.id.action_addAddressFragment_to_webViewFragment, bundle)
        }
        binding.tvLogout.setOnClickListener {
            (activity as DashBoardActivity).showDialAlert("Are you sure do want to Logout? ") {
                if (it) {
                    PreferenceManager(requireContext()).setIsLoggedIn(false)
                    PreferenceManager(requireContext()).clearPreferenceManager()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    db?.deleteAllMyCardItem()
                    activity?.finish()
                }

            }
        }


    }

    private fun setAddressAdapter(addressItem: List<AddressItem>) {
        binding.contentAddress.rvAddress.apply {
            binding.contentAddress.rvAddress.adapter = AddressListAdapter {
                if (isConnected(requireActivity())) {
                    val bundle = Bundle()
                    bundle.putString("AddressDetails", it)
                    findNavController().navigate(
                        R.id.action_addAddressFragment_to_settingsFragment,
                        bundle
                    )
                } else {
                    activity?.toast("Please Check the your Internet! ")
                }

            }
        }
        (binding.contentAddress.rvAddress.adapter as AddressListAdapter).listAddress(addressItem)
    }
}