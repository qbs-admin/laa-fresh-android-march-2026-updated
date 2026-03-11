package com.qbs.laafresh.ui.fragments.checkout

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.dbhelper.LaaFreshDB
import com.qbs.laafresh.data.dbhelper.table.MyCartEntity
import com.qbs.laafresh.data.network.api.reponse.CouponDetail
import com.qbs.laafresh.data.network.api.request.*
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.viewmodel.VMCheckOut
import com.qbs.laafresh.databinding.FragmentCheckOutBinding
import com.qbs.laafresh.ui.adapter.CheckOutViewAdapter
import com.qbs.laafresh.ui.extension.isConnected
import com.qbs.laafresh.ui.extension.remove_demical
import com.qbs.laafresh.ui.extension.toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class CheckOutFragment : Fragment() {
    private lateinit var binding: FragmentCheckOutBinding
    var db: LaaFreshDAO? = null
    var getCartList = ArrayList<MyCartEntity>()
    var sendCartDataRequest = SendCartDataRequest()
    var instaMojoRequest = InstaMojoRequest()
    val productDetails: ArrayList<ProductDetailsItem> = ArrayList()
    var shippingAddress: ShippingAddress? = null
    var paymentType = ""
    var couponCode = ""
    var isApplied = false
    var isCouponValid = false
    var deliveryTimeSlot: ArrayList<String>? = null
    var couponRequest = CouponRequest()
    val vmCheckOut: VMCheckOut by lazy {
        this.let {
            ViewModelProvider(it, VMCheckOut.Factory(requireContext()))
                .get(VMCheckOut::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = LaaFreshDB.getInstance(context = requireContext())?.laaFreshDAO()
        deliveryTimeSlot = ArrayList()
        binding.contentPlacementOrder.relativeAwayPrice.visibility = View.GONE
        binding.contentPlacementOrder.relativeAddCoupon.visibility = View.VISIBLE
        showDeliveryDate()
        getCartList.clear()
        getCartList = db?.getMyCardList() as ArrayList<MyCartEntity>
        binding.includeDefaultAddress.root.setOnClickListener {
            findNavController().navigate(R.id.action_checkOutFragment_to_settingsFragment)
        }
        binding.rgPaymentType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_cash_on_delivery -> {
                    paymentType = "cash_on_delivery"
                }

                R.id.rb_online_payment -> {
                    paymentType = "instamojo"

                }

            }
        }
        binding.deliveryTime.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                i: Int,
                l: Long
            ) {
                val timeSlot: String =
                    binding.deliveryTime.getItemAtPosition(binding.deliveryTime.selectedItemPosition)
                        .toString()
                //activity?.toast(timeSlot);
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {

            }
        }
        if (isConnected(requireActivity())) {
            vmCheckOut.appliedReferralDiscount(onSuccess = {
                if (it.success.equals("1")) {
                    getReferralDiscount(it.refereeDiscountDetails?.discountValue)
                } else {
                    // activity?.toast(it.message.toString())
                }
            }, onError = {
                activity?.toast(it.toString())

            })
        } else {
            activity?.toast("Please Check the your Internet! ")
        }

        binding.contentPlacementOrder.btnApplyCoupon.setOnClickListener {
            showLoading(true)
            if (isConnected(requireActivity())) {
                if (!binding.contentPlacementOrder.edCouponCode.text.toString().isNullOrEmpty()) {
                    couponRequest.couponcode =
                        binding.contentPlacementOrder.edCouponCode.text.toString()
                    couponRequest.userId = PreferenceManager(requireContext()).getCustomerId()
                    vmCheckOut.getCoupon(
                        couponRequest = couponRequest,
                        onSuccess = {
                            if (it.success.equals("1")) {
                                couponDiscount(it.coupondetails!!)
                            } else {
                                showLoading(false)
                                activity?.toast(it.message.toString())
                            }
                        },
                        onError = {
                            activity?.toast(it.toString())
                            showLoading(false)
                        }
                    )
                } else {
                    showLoading(false)
                    activity?.toast("Please Enter your Coupon ")
                }

            } else {
                showLoading(false)
                activity?.toast("Please Check the your Internet! ")
            }
        }
        /*    val sdf = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("dd-MM-YYYY")
            } else {
            }
            val currentDate = sdf.format(Date())*/
        val currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now())
        } else {
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        }
        if (isConnected(requireActivity())) {
            showLoading(true)
            vmCheckOut.getDefaultAddress(
                onSuccess = {
                    showLoading(false)
                    if (it.success.equals("1")) {
                        binding.orderPlaceLayout.visibility = View.VISIBLE
                        PreferenceManager(requireContext()).setIsDefaultAddressAvailable(true)
                        binding.includeDefaultAddress.tvDefaultAddress.text = ""
                        binding.includeDefaultAddress.tvDefaultName.text = it.address?.get(0)?.name
                        binding.includeDefaultAddress.tvAddressListDeliveryPhoneNumber.text =
                            it.address?.get(0)?.phone
                        binding.includeDefaultAddress.imDefaultAddressIcon.visibility =
                            View.VISIBLE
                        binding.includeDefaultAddress.tvDefaultAddress.text =
                            "${it.address?.get(0)?.address1}\n${it.address?.get(0)?.address2}"
                        shippingAddress = ShippingAddress(
                            it.address?.get(0)?.zip,
                            it.address?.get(0)?.name,
                            currentDate,
                            paymentType,
                            "",
                            it.address?.get(0)?.address2,
                            it.address?.get(0)?.city,
                            it.address?.get(0)?.phone,
                            it.address?.get(0)?.address1,
                            "",
                            "",
                            ""
                        )
                    } else {
                        binding.orderPlaceLayout.visibility = View.VISIBLE
                        binding.includeDefaultAddress.imDefaultAddressIcon.visibility = View.GONE
                        binding.includeDefaultAddress.tvDefaultAddress.text =
                            "Add your Default Address"
                        PreferenceManager(requireContext()).setIsDefaultAddressAvailable(false)

                    }

                },
                onError = {
                    showLoading(false)

                }
            )
        } else {
            showLoading(false)
            activity?.toast("Please Check the your Internet! ")
        }

        binding.rvOrderDetailsList.apply {
            binding.rvOrderDetailsList.adapter = CheckOutViewAdapter()
            (binding.rvOrderDetailsList.adapter as CheckOutViewAdapter).addCartItem(getCartList)
        }
        var total = db?.getSubTotal()
        var tax = db?.getTotalTax()
        var discount = db?.getTotalDiscount()
        binding.contentPlacementOrder.tvSubTotal.text = activity?.remove_demical(total.toString())
        binding.contentPlacementOrder.tvTaxValue.text = activity?.remove_demical(tax.toString())
//        Log.e("TAG","Tax ------------")
//        Log.e("TAG",tax.toString())
        binding.contentPlacementOrder.tvDiscount.text =
            activity?.remove_demical(discount.toString())
        binding.contentPlacementOrder.tvOverallShippingCostValue.text =
            activity?.remove_demical(PreferenceManager(requireContext()).getShippingCost())
                .toString()
        binding.contentPlacementOrder.tvTotal.text = activity?.remove_demical(
            (total?.plus(
                binding.contentPlacementOrder.tvOverallShippingCostValue.text.toString().toDouble()
            )).toString()
        )
        binding.btnPlacement.setOnClickListener {
            if (PreferenceManager(requireContext()).isDefaultAddressAvailable()) {
                if (!binding.deliveryDate.text.toString().startsWith("Select", ignoreCase = true)) {
                    if (!paymentType.isNullOrEmpty()) {
                        /*if (paymentType == "cash_on_delivery") {*/
                        if (isConnected(requireActivity())) {
                            getCartList.forEach {
                                val options = Options(it.priceId)
                                productDetails.add(
                                    ProductDetailsItem(
                                        it.productURI,
                                        0,
                                        couponCode,
                                        it.subtotal.toInt(),
                                        it.subtotal.toInt(),
                                        it.orderQty,
                                        it.productName,
                                        options,
                                        it.tax.toString(),
                                        it.productCode
                                    )
                                )
                            }
                            sendCartDataRequest.productDetails = productDetails
                            sendCartDataRequest.shippingAddress = shippingAddress
                            sendCartDataRequest.deliveryDate = binding.deliveryDate.text.toString()
                            sendCartDataRequest.paymentType = paymentType
                            sendCartDataRequest.orderedDate = currentDate
                            sendCartDataRequest.subTotal = total.toString()
                            sendCartDataRequest.grandTotal =
                                binding.contentPlacementOrder.tvTotal.text.toString()
                            sendCartDataRequest.deliveryTimeslot =
                                if (binding.deliveryTime.selectedItem != null) binding.deliveryTime.selectedItem.toString() else ""
                            sendCartDataRequest.couponDiscount =
                                binding.contentPlacementOrder.tvCouponValue.text.toString()
                            sendCartDataRequest.referralPhoneNo =
                                binding.contentPlacementOrder.ReferralNumber.text.toString()
                            sendCartDataRequest.buyer =
                                PreferenceManager(requireContext()).getCustomerId()
//                            Log.e("TAG", "-----------C-----------")
//                            Log.e("TAG", sendCartDataRequest.toString())
                            showLoading(true)
                            vmCheckOut.placingOrders(
                                sendCartDataRequest = sendCartDataRequest,
                                onSuccess = {
                                    if (it.success.equals("1")) {
                                        showLoading(false)
                                        if (it.payment_type!! == "cash_on_delivery") {
                                            db?.deleteAllMyCardItem()
                                            activity?.toast("thanks for your order . We will get back to you soon")
                                            findNavController().navigate(R.id.homeFragment, null,
                                                navOptions {
                                                    popUpTo(R.id.homeFragment) {
                                                        inclusive = true
                                                    }
                                                })
                                        } else if (it.payment_type == "instamojo") {
                                            instaMojoRequest.firstname = it.username
                                            instaMojoRequest.email = it.email
                                            instaMojoRequest.phone = it.phone
                                            instaMojoRequest.grandTotal = it.grand_total.toString()
                                            instaMojoRequest.orderId = it.saleId.toString()
                                            it.longurl?.let { it1 -> Log.e("it.longurl>>>", it1) }
                                            val bundle = Bundle()
                                            bundle.putString("url", it.longurl)
                                            bundle.putString("title", "OnLine Payment")
                                            findNavController().navigate(
                                                R.id.action_checkOutFragment_to_instaMojoWebViewFragment,
                                                bundle
                                            )
                                            /*vmCheckOut.instaMojoPayment(
                                                instaMojoRequest = instaMojoRequest,
                                                onSuccess = {instaMojoResponse ->
                                                },
                                                onError = {instaError->
                                                    activity?.toast(instaError.toString())
                                                }

                                            )*/
                                        }
                                    } else {
                                        showLoading(false)
                                        activity?.toast(it.message.toString())
                                    }
                                },
                                onError = {
                                    showLoading(false)
                                    activity?.toast(it.toString())
                                })
                        } else {
                            showLoading(false)
                            activity?.toast("Please Check the your Internet! ")
                        }
                        /* } else {
                             activity?.toast("Online Payment cannot be done now! ")
                         }*/
                    } else {
                        activity?.toast("Please Select the Payment type ")
                    }
                } else {
                    placeOrder()
                }
            } else {
                activity?.toast("Please Add your address")
            }


        }


    }

    private fun couponDiscount(couponDetail: ArrayList<CouponDetail>) {
        try {
            showLoading(false)
            val currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            } else {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            }
            val todayDate = currentDate.format(Date())
            val checkCoupon = compareDates(todayDate, couponDetail[0].till)
            var total = db?.getSubTotal()
            var alreadyExecuted = false

            if (checkCoupon) {

                couponCode = binding.contentPlacementOrder.edCouponCode!!.text.toString()
                couponDetail.forEach { coupon ->
                    when (coupon.spec!!.setType!!) {
                        "all_products" -> {
                            binding.contentPlacementOrder.edCouponCode.isFocusable = false
                            binding.contentPlacementOrder.edCouponCode.isFocusableInTouchMode =
                                false
                            binding.contentPlacementOrder.edCouponCode.clearFocus()
                            binding.contentPlacementOrder.btnApplyCoupon.text = "APPLIED"
                            binding.contentPlacementOrder.btnApplyCoupon.isEnabled = false
                            showLoading(false)
                            if (coupon.spec.discountType!! == "percent") {
                                getCoupon(couponDetail[0].spec?.discountValue)
                                couponCode = couponDetail[0].code.toString()
                            } else if (coupon.spec.discountType!! == "amount") {
                                if (!alreadyExecuted) {
                                    couponDetail[0].spec?.discountValue?.let {
                                        Log.e(
                                            "disamount>>",
                                            it
                                        )
                                    }
                                    getCouponPrice(couponDetail[0].spec?.discountValue)
                                    alreadyExecuted = true
                                }
                            }

                        }

                        "category" -> {
                            if (coupon.spec!!.setType!! == "category") {
                                coupon.spec.set!!.forEach { couponCategory ->

                                    db!!.getCartByCategory(couponCategory).forEach { category ->
                                        getCartList.forEach {
                                            db!!.getMaxSubTotal(couponCategory)
                                            if (it.categoryCode == category.categoryCode) {
                                                val productDiscountAmount =
                                                    getDiscountAmount(
                                                        category.subtotal,
                                                        coupon.spec.discountType,
                                                        coupon.spec.discountValue,
                                                        category.orderQty
                                                    )
                                                val numWihoutDecimal: String =
                                                    productDiscountAmount.toString()
                                                        .split("\\.".toRegex()).toTypedArray()[0]
                                                binding.contentPlacementOrder.tvCouponValue.text =
                                                    numWihoutDecimal
                                            }
                                        }

                                    }
                                    binding.contentPlacementOrder.edCouponCode.isFocusable = false
                                    binding.contentPlacementOrder.edCouponCode.isFocusableInTouchMode =
                                        false
                                    binding.contentPlacementOrder.edCouponCode.clearFocus()
                                    binding.contentPlacementOrder.btnApplyCoupon.text = "APPLIED"
                                    binding.contentPlacementOrder.btnApplyCoupon.isEnabled = false
                                    val finalTotal =
                                        (binding.contentPlacementOrder.tvTotal.text.toString()).toInt()
                                            ?.minus(
                                                binding.contentPlacementOrder.tvCouponValue.text.toString()
                                                    .toInt()
                                            )
                                    binding.contentPlacementOrder.tvTotal.text =
                                        activity?.remove_demical(finalTotal.toString())
                                }

                            } else {
                                activity?.toast("Coupon not applicable for this product......")
                            }
                        }

                        "sub_category" -> {
                            activity?.toast("Coupon Not Available for now...")

                        }

                        "product" -> {
                            activity?.toast("Coupon Not Available for now...")

                        }


                    }

                }
            } else {
                activity?.toast("Coupon Expired...")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getDiscountAmount(
        subTotal: Double,
        getDiscountType: String?,
        discountValue: String?,
        orderQty: Int
    ): Double {
        try {
            var discount = 0.0
            if (getDiscountType.equals("percent", ignoreCase = true)) {
                discount = ((subTotal / 100) * (discountValue!!.toDouble() * orderQty))

            } else if (getDiscountType.equals("amount", ignoreCase = true)) {
                discount = discountValue!!.toDouble() * orderQty
            }
            return discount
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0.0
    }

    private fun getCoupon(discountValue: String?) {
        isApplied = true
        var total = db?.getSubTotal()
        val discountValue = (total?.times((discountValue?.toInt()?.div(100f)!!)))
        Log.e("discountValue>>", discountValue.toString())
        val numWihoutDecimal: String = activity?.remove_demical(discountValue.toString()).toString()
        // discountValue.toString().split("\\.".toRegex()).toTypedArray()[0]
        binding.contentPlacementOrder.tvCouponValue.text = numWihoutDecimal
        val finalTotal = ((binding.contentPlacementOrder.tvTotal.text.toString()).toDouble()
            .minus(numWihoutDecimal.toDouble()))
        //.plus(((PreferenceManager(requireContext()).getShippingCost()).toDouble()))
        binding.contentPlacementOrder.tvTotal.text = activity?.remove_demical(finalTotal.toString())
    }

    private fun getReferralDiscount(discountValue: String?) {
        isApplied = true
        var total = db?.getSubTotal()
        val discountValue = (total?.times((discountValue?.toInt()?.div(100f)!!)))
        Log.e("discountValue>>", discountValue.toString())
        val numWihoutDecimal: String = activity?.remove_demical(discountValue.toString()).toString()
        // discountValue.toString().split("\\.".toRegex()).toTypedArray()[0]
        binding.contentPlacementOrder.tvReferralValue.text = numWihoutDecimal
        val finalTotal = ((binding.contentPlacementOrder.tvTotal.text.toString()).toDouble()
            .minus(numWihoutDecimal.toDouble()))
        //.plus(((PreferenceManager(requireContext()).getShippingCost()).toDouble()))
        binding.contentPlacementOrder.tvTotal.text = activity?.remove_demical(finalTotal.toString())
    }

    private fun getCouponPrice(discountValue: String?) {
        isApplied = true
        var total = db?.getSubTotal()
        val discountValue = ((total?.minus((discountValue.toString()).toDouble()))
                // ?.plus(((PreferenceManager(requireContext()).getShippingCost()).toDouble()))
                )
        Log.e("discountValue>>", discountValue.toString())
        binding.contentPlacementOrder.tvTotal.text =
            activity?.remove_demical(discountValue.toString())
    }


    fun compareDates(d1: String?, d2: String?): Boolean {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val date1 = sdf.parse(d1)
            val date2 = sdf.parse(d2)
            if (date1.after(date2)) {
                isCouponValid = false
            }
            if (date1.before(date2)) {
                isCouponValid = true
            }
            if (date1 == date2) {
                isCouponValid = true
            }
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
        return isCouponValid
    }

    private fun showDeliveryDate() {
        try {
            binding.deliveryDateBtn.setOnClickListener {
                datePickerDialog()
                if (binding.deliveryDate.text.toString() != null) timeSlotByDate()
                /*val sdf = SimpleDateFormat("dd/MM/YYYY")
                 val date = sdf.format(Date())*/
            }
            binding.deliveryDate.setOnClickListener {
                datePickerDialog()
                if (binding.deliveryDate.text.toString() != null) timeSlotByDate()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun timeSlotByDate() {
        if (isConnected(requireActivity())) {
            deliveryTimeSlot?.clear()
            val deliveryTimeRequest = DeliveryTimeRequest()
            deliveryTimeRequest.date = binding.deliveryDate.text.toString()
            vmCheckOut.getDeliveryTimes(deliveryTimeRequest, onSuccess = {
                if (if (it.delivery != null) it.delivery.isNotEmpty() else throw NullPointerException(
                        "Expression 'it.delivery' must not be null"
                    )
                ) {
                    it.delivery?.forEach { deliveryTimeSlotList ->
                        deliveryTimeSlot!!.add(deliveryTimeSlotList.toString())
                        binding.deliveryTime.adapter = ArrayAdapter<String>(
                            requireActivity(),
                            android.R.layout.simple_spinner_dropdown_item,
                            deliveryTimeSlot!!
                        )
                    }
                }


            }, onError = {

            })
        } else {
            activity?.toast("Please Check the your Internet! ")
        }
    }

    private fun datePickerDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                binding.deliveryDate.text =
                    dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year.toString()
                binding.deliveryDate.background = resources.getDrawable(R.drawable.spinner_bg)
                timeSlotByDate()
            },
            year,
            month,
            day
        )
        dpd.datePicker.minDate = System.currentTimeMillis()
        dpd.show()
    }

    private fun placeOrder() {
        try {

            if (binding.deliveryDate.text.toString().startsWith("Select", ignoreCase = true)) {
                binding.deliveryDate.background = resources.getDrawable(R.drawable.spinner_error_bg)
                activity?.toast("Select Delivery Date")
            } else {
                binding.deliveryDate.background = resources.getDrawable(R.drawable.spinner_bg)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (binding.pbCheckOut != null) {
            if (isLoading)
                binding.pbCheckOut.visibility = View.VISIBLE
            else
                binding.pbCheckOut.visibility = View.GONE
        }
    }

}