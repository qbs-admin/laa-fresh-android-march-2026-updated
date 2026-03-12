package com.qbs.laafresh.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.dbhelper.LaaFreshDB
import com.qbs.laafresh.data.dbhelper.table.ProductEntity
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.viewmodel.VMProduct
import com.qbs.laafresh.databinding.AddingCartFragmentBinding
import com.qbs.laafresh.ui.extension.isConnected
import com.qbs.laafresh.ui.extension.toSpanned
import com.qbs.laafresh.ui.extension.toast

class AddingToCartFragment : Fragment() {
    private lateinit var binding: AddingCartFragmentBinding
    private var db: LaaFreshDAO? = null
    private var getProductsList = ArrayList<ProductEntity>()
    private var productId = ""
    private var userId = "0"
    private val vmProduct: VMProduct by lazy {
        this.let {
            ViewModelProvider(it, VMProduct.Factory(requireContext()))
                .get(VMProduct::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddingCartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //nav overwrite issue
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val navBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            v.setPadding(0, 0, 0, navBarHeight)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.tbProductsDetails) { v, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.setPadding(0, statusBar, 0, 0)
            insets
        }

        binding.addLinearProductQty.visibility = View.VISIBLE
        db = LaaFreshDB.getInstance(context = requireContext())?.laaFreshDAO()
        val bundle = this.arguments
        initAppBar()
        if (bundle != null)
            productId = bundle.getString("productId")!!
        getProductOffline(productId)
        var qty = db?.getQtyAndQtyValue(productId)
        binding.tvItemProductQty.text = qty?.orderQty.toString()
        var count = db?.getAllCartCount()
        binding.contentCartView.tvCartCount.text = "Items- ${count}"
        binding.tvItemReduceProduct.setOnClickListener {
            var qty = db?.getQtyAndQtyValue(productId)
            var myCartEntity = db?.checkItemAlreadyExist(qty!!.productCode)
            if (myCartEntity!!.orderQty == 1) {
            } else {
                db?.updateMyCart(
                    qty!!.orderQty - 1,
                    (ParseDouble(qty.defaultProductPrice.toString()) * (qty.orderQty - 1))
                            - (ParseDouble(qty.defaultDiscountAmount.toString()) * (qty.orderQty - 1)) +
                            (ParseDouble(qty.defaultTax.toString()) * (qty.orderQty - 1)) + (ParseDouble(
                        qty.defaultShippingCost.toString()
                    ) * (qty.orderQty - 1)),
                    qty.productCode,

                    (ParseDouble(qty.defaultDiscountAmount.toString()) * (qty.orderQty - 1)),
                    (ParseDouble(qty.defaultTax.toString()) * (qty.orderQty - 1)),
                    (ParseDouble(qty.defaultShippingCost.toString()) * (qty.orderQty - 1))

                )
                binding.tvItemProductQty.text = (qty!!.orderQty - 1).toString()
            }
        }

        binding.tvItemAddProduct.setOnClickListener {
            var qty = db?.getQtyAndQtyValue(productId)
            db?.updateMyCart(
                qty!!.orderQty + 1,
                (ParseDouble(qty.defaultProductPrice.toString()) * (qty.orderQty + 1))
                        - (ParseDouble(qty.defaultDiscountAmount.toString()) * (qty.orderQty + 1)) +
                        (ParseDouble(qty.defaultTax.toString()) * (qty.orderQty + 1)) +
                        (ParseDouble(qty.defaultShippingCost.toString()) * (qty.orderQty + 1)),
                qty.productCode,
                (ParseDouble(qty.defaultDiscountAmount.toString()) * (qty.orderQty + 1)),
                (ParseDouble(qty.defaultTax.toString()) * (qty.orderQty + 1)),
                (ParseDouble(qty.defaultShippingCost.toString()) * (qty.orderQty + 1))

            )
            binding.tvItemProductQty.text = (qty!!.orderQty + 1).toString()
        }

        binding.contentCartView.root.setOnClickListener {
            findNavController().navigate(R.id.action_addAddressFragment_to_mycartFragment, null,
                navOptions {
                    popUpTo(R.id.homeFragment) {
                        inclusive = true
                    }
                })
        }
    }

    fun ParseDouble(value: String?): Double {
        return if (value != null && value.isNotEmpty()) {
            try {
                value.toDouble()
            } catch (e: java.lang.Exception) {
                (-1).toDouble()
            }
        } else 0.0
    }

    private fun getProductOffline(productId: String) {
        getProductsList.clear()
        getProductsList = db?.getProductItemsDetails(productId) as ArrayList<ProductEntity>
        if (getProductsList.size == 0) {
            activity?.toast(getString(R.string.no_internet))
            if (isConnected(requireActivity())) {
                userId = if (PreferenceManager(requireContext()).getIsLoggedIn()) PreferenceManager(
                    requireContext()
                ).getCustomerId() else "0"
                getProductOnline(userId)
            }

        }
        Glide.with(this)
            .load(getProductsList[0].image)
            .placeholder(R.drawable.product_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(binding.ivProductDetailsImage)
        binding.tvDescriptionTitle.text = getProductsList[0].title
        binding.tvProductDescription.text = getProductsList[0].description.toSpanned()
        binding.tvProductNetWeight.text = getProductsList[0].net_weight
        binding.tvProductsPrice.text =
            context?.getString(R.string.Rs) + " " + getProductsList[0].salePrice
    }

    private fun getProductOnline(userId: String) {
        vmProduct.getIndividualProduct(productId, userId, onSuccess = {
            binding.pbIndividualProduct.visibility = View.GONE
            Glide.with(this)
                .load(it.image)
                .into(binding.ivProductDetailsImage)
            binding.tvDescriptionTitle.text = it.title
            binding.tvProductDescription.text = it.description?.toSpanned()
            binding.tvProductNetWeight.text = it.net_weight
            binding.tvProductsPrice.text =
                context?.getString(R.string.Rs) + " " + getProductsList[0].salePrice


        }, onError = {
            binding.pbIndividualProduct.visibility = View.GONE
        })
    }

    private fun initAppBar() {
        binding.tbProductsDetails.apply {
            (activity as AppCompatActivity).setSupportActionBar(binding.tbProductsDetails)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (binding.tbProductsDetails != null) {
            if (isLoading)
                binding.tbProductsDetails.visibility = View.VISIBLE
            else
                binding.tbProductsDetails.visibility = View.GONE
        }
    }

}