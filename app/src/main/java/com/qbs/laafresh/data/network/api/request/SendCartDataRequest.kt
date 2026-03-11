package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.SerializedName


data class SendCartDataRequest(

	@SerializedName("payment_type")
	var paymentType: String? = null,

	@SerializedName("delivery_date")
	var deliveryDate: String? = null,

	@SerializedName("shipping")
	val shipping: String? = null,

	@SerializedName("ordered_date")
	var orderedDate: String? = null,

	@SerializedName("shipping_address")
	var shippingAddress: ShippingAddress? = null,

	@SerializedName("subTotal")
	var subTotal: String? = null,

	@SerializedName("grand_total")
	var grandTotal: String? = null,

	@SerializedName("product_details")
	var productDetails: List<ProductDetailsItem?>? = null,

	@SerializedName("delivery_timeslot")
	var deliveryTimeslot: String? = null,

	@SerializedName("coupon_discount")
	var couponDiscount: String? = null,
	@SerializedName("referral_phoneno")
	var referralPhoneNo: String? = null,

	@SerializedName("buyer")
	var buyer: String? = null
)

data class Options(

	@SerializedName("priceid")
	val priceid: String? = null
)

data class ProductDetailsItem(

	@SerializedName("image")
	val image: String? = null,

	@SerializedName("shipping")
	val shipping: Int? = null,

	@SerializedName("coupon")
	val coupon: String? = null,

	@SerializedName("price")
	val price: Int? = null,

	@SerializedName("subtotal")
	val subtotal: Int? = null,

	@SerializedName("qty")
	val qty: Int? = null,

	@SerializedName("name")
	var name: String? = null,

	@SerializedName("options")
	val options: Options? = null,

	@SerializedName("tax")
	val tax: String? = null,

	@SerializedName("id")
	val id: String? = null,

	@SerializedName("priceid")
	val priceid: String? = null
)

data class ShippingAddress(

	@SerializedName("zip")
	val zip: String? = null,

	@SerializedName("firstname")
	val firstname: String? = null,

	@SerializedName("delivery_date")
	val deliveryDate: String? = null,

	@SerializedName("payment_type")
	val paymentType: String? = null,

	@SerializedName("langlat")
	val langlat: String? = null,

	@SerializedName("address2")
	val address2: String? = null,

	@SerializedName("city")
	val city: String? = null,

	@SerializedName("phone")
	val phone: String? = null,

	@SerializedName("address1")
	val address1: String? = null,

	@SerializedName("delivery_timeslot")
	val deliveryTimeslot: String? = null,

	@SerializedName("email")
	val email: String? = null,

	@SerializedName("lastname")
	val lastname: String? = null
)
