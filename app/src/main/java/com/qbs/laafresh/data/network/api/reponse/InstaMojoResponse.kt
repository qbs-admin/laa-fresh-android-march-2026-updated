package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.SerializedName

data class InstaMojoResponse(

	@SerializedName("payment_request")
	val paymentRequest: PaymentRequest? = null,

	@SerializedName("success")
	val success: Boolean? = null
)

data class PaymentRequest(

	@SerializedName("amount")
	val amount: String? = null,

	@SerializedName("webhook")
	val webhook: Any? = null,

	@SerializedName("purpose")
	val purpose: String? = null,

	@SerializedName("shorturl")
	val shorturl: Any? = null,

	@SerializedName("buyer_name")
	val buyerName: String? = null,

	@SerializedName("send_email")
	val sendEmail: Boolean? = null,

	@SerializedName("created_at")
	val createdAt: String? = null,

	@SerializedName("email_status")
	val emailStatus: Any? = null,

	@SerializedName("allow_repeated_payments")
	val allowRepeatedPayments: Boolean? = null,

	@SerializedName("expires_at")
	val expiresAt: Any? = null,

	@SerializedName("phone")
	val phone: String? = null,

	@SerializedName("longurl")
	val longurl: String? = null,

	@SerializedName("id")
	val id: String? = null,

	@SerializedName("customer_id")
	val customerId: Any? = null,

	@SerializedName("modified_at")
	val modifiedAt: String? = null,

	@SerializedName("send_sms")
	val sendSms: Boolean? = null,

	@SerializedName("email")
	val email: String? = null,

	@SerializedName("sms_status")
	val smsStatus: Any? = null,

	@SerializedName("redirect_url")
	val redirectUrl: String? = null,

	@SerializedName("status")
	val status: String? = null
)
