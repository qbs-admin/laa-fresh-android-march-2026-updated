package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class ReferralResponse(

	@SerializedName("referee_discount_details")
	val refereeDiscountDetails: RefereeDiscountDetails? = null,

	@SerializedName("success")
	val success: String? = null,

	@SerializedName("message")
	val message: String? = null
)

data class RefereeDiscountDetails(

	@SerializedName("sale_id")
	val saleId: String? = null,

	@SerializedName("referrer_id")
	val referrerId: String? = null,

	@SerializedName("user_id")
	val userId: String? = null,

	@SerializedName("valid_to")
	val validTo: String? = null,

	@SerializedName("discount_value")
	val discountValue: String? = null,

	@SerializedName("id")
	val id: String? = null,

	@SerializedName("created_date")
	val createdDate: String? = null,

	@SerializedName("purchase_date")
	val purchaseDate: String? = null,

	@SerializedName("discount_type")
	val discountType: String? = null,

	@SerializedName("is_claimed")
	val isClaimed: String? = null,

	@SerializedName("refer_config")
	val referConfig: Any? = null
)
