package com.qbs.laafresh.ui.fragments.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.dbhelper.LaaFreshDB
import com.qbs.laafresh.data.dbhelper.table.MyCartEntity
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.databinding.FragmentMyCartBinding
import com.qbs.laafresh.ui.DashBoardActivity
import com.qbs.laafresh.ui.adapter.CartViewAdapter
import com.qbs.laafresh.ui.extension.remove_demical


class MyCartFragment : Fragment() {
    private lateinit var binding: FragmentMyCartBinding
    var db: LaaFreshDAO? = null
    var getCartList = ArrayList<MyCartEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyCartBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = LaaFreshDB.getInstance(context = requireContext())?.laaFreshDAO()


        var count = db?.getAllCartCount()
        if (count!! > 0) {
            setCartCount(count)
            getCartProducts()
            checkMiniPrice(db?.getSubTotal())
            // newly added by udhaya on 10-02-2026


            var total = db?.getSubTotal()
            var tax = db?.getTotalTax()
            var discount = db?.getTotalDiscount()
            binding.contentOrder.root.visibility = View.VISIBLE
            binding.btnCheckout.visibility = View.VISIBLE
            binding.tvEmptyCart.visibility = View.GONE
            binding.contentOrder.tvSubTotal.text = activity?.remove_demical(total.toString())
            binding.contentOrder.tvDiscount.text = activity?.remove_demical(discount.toString())
            binding.contentOrder.tvTaxValue.text = activity?.remove_demical(tax.toString())
            binding.contentOrder.tvTotal.text = activity?.remove_demical(total.toString())
        } else {
            binding.contentOrder.root.visibility = View.GONE
            binding.btnCheckout.visibility = View.GONE
            binding.tvEmptyCart.visibility = View.VISIBLE
        }
        binding.btnCheckout.setOnClickListener {
            if (PreferenceManager(requireContext()).getIsLoggedIn()) {
                if (PreferenceManager(requireContext()).getMiniAmount()
                        .toInt() <= db?.getSubTotal()!!
                ) {
//                    Log.e("TAG","Navigate")
                    findNavController().navigate(R.id.action_myCartFragment_to_checkOutFragment)
                }
            } else {
                findNavController().navigate(R.id.action_myCartFragment_to_loginFragment)
            }

        }
    }

    fun setCartCount(cartCount: Int) {
        (activity as DashBoardActivity).setCartCount(cartCount)
    }

    private fun getCartProducts() {

        getCartList.clear()
        getCartList = db?.getMyCardList() as ArrayList<MyCartEntity>
//        Log.e("TAG","Cart List --");
//        Log.e("TAG",getCartList.toString());
        if (getCartList.isNotEmpty()) {
            setCartViewAdapter(getCartList as List<MyCartEntity>)
        }
    }

    private fun setCartViewAdapter(cartItems: List<MyCartEntity>) {
//        Log.e("TAGs", "set")
        binding.rvCart.apply {
            binding.rvCart.adapter =
                CartViewAdapter { qty, qtyvalue, productid, isdelete, subtotal, discount, tax, shipping_cost ->
//                    Log.e("TAGs", productid.toString())
                    if (isdelete) {
//                        Log.e("TAGs", productid.toString())
                        db?.updateMyCart(qty, subtotal, productid, discount, tax, shipping_cost)
                        db?.deleteMyCardItem(productid)
                        getCartList = db?.getMyCardList() as ArrayList<MyCartEntity>
                        (binding.rvCart.adapter as CartViewAdapter).addCartItem(getCartList)
                        checkMiniPrice(db?.getSubTotal())
                        var total = db?.getSubTotal()
                        var count = db?.getAllCartCount()
                        if (count!! > 0) {
                            var tax = db?.getTotalTax()
                            var discount = db?.getTotalDiscount()
                            binding.contentOrder.root.visibility = View.VISIBLE
                            binding.btnCheckout.visibility = View.VISIBLE
                            binding.tvEmptyCart.visibility = View.GONE
                            binding.contentOrder.tvSubTotal.text =
                                activity?.remove_demical(total.toString())
                            binding.contentOrder.tvDiscount.text =
                                activity?.remove_demical(discount.toString())
                            binding.contentOrder.tvTaxValue.text =
                                activity?.remove_demical(tax.toString())
                            binding.contentOrder.tvTotal.text =
                                activity?.remove_demical(total.toString())
                        } else {
                            binding.contentOrder.root.visibility = View.GONE
                            binding.btnCheckout.visibility = View.GONE
                            binding.tvEmptyCart.visibility = View.VISIBLE
                        }

                    } else {
                        db?.updateMyCart(qty, subtotal, productid, discount, tax, shipping_cost)
                        getCartList = db?.getMyCardList() as ArrayList<MyCartEntity>
                        (binding.rvCart.adapter as CartViewAdapter).addCartItem(getCartList)
                        checkMiniPrice(db?.getSubTotal())
                        var total = db?.getSubTotal()
                        var tax = db?.getTotalTax()
                        var discount = db?.getTotalDiscount()
                        binding.contentOrder.tvSubTotal.text =
                            activity?.remove_demical(total.toString())
                        binding.contentOrder.tvDiscount.text =
                            activity?.remove_demical(discount.toString())
                        binding.contentOrder.tvTaxValue.text =
                            activity?.remove_demical(tax.toString())
                        binding.contentOrder.tvTotal.text =
                            activity?.remove_demical(total.toString())
                    }
                }
        }
//        Log.e("TAGs", " -- x --")
//        Log.e("TAGs", cartItems.toString())
        (binding.rvCart.adapter as CartViewAdapter).addCartItem(cartItems)
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

    private fun checkMiniPrice(subTotal: Double?) {
        Log.e("subtotal>>>", subTotal.toString())
        Log.e("getMiniAmount>>>", PreferenceManager(requireContext()).getMiniAmount())
        if (PreferenceManager(requireContext()).getMiniAmount().toDouble() >= subTotal!!) {
            Log.e("subtotal1>>>", subTotal.toString())
            Log.e("getMiniAmount2>>>", PreferenceManager(requireContext()).getMiniAmount())
            binding.contentOrder.relativeAwayPrice.visibility = View.VISIBLE
            binding.contentOrder.tvAwayPrice.text = "Price"
            binding.contentOrder.tvAwayPriceValue.text = subTotal.toString()
            binding.contentOrder.tvAwayDescription.text =
                "Your Order Away from Rs ${
                    PreferenceManager(requireContext()).getMiniAmount()
                        .toDouble() - subTotal!!
                }"

        } else {
            Log.e("subtotal3>>>", subTotal.toString())
            Log.e("getMiniAmount3>>>", PreferenceManager(requireContext()).getMiniAmount())
            binding.contentOrder.relativeAwayPrice.visibility = View.GONE
        }
    }


}