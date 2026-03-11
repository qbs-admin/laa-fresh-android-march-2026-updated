package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyOrderListResponse {

     @SerializedName("success")
     @Expose
     val success: String? = null

     @SerializedName("orders")
     @Expose
     val orders: ArrayList<MyOrderListItem>? = null

     @SerializedName("message")
     @Expose
     val message: String? = null
}

class MyOrderListItem {

     @SerializedName("sale_id")
     @Expose
     val sale_id: String? = null

     @SerializedName("sale_code")
     @Expose
     val sale_code: String? = null

     @SerializedName("payment_status")
     @Expose
     val payment_status: String? = null

     @SerializedName("grand_total")
     @Expose
     val grand_total: String? = null

     @SerializedName("sale_datetime")
     @Expose
     val sale_datetime: String? = null

     @SerializedName("delivery_status")
     @Expose
     val delivery_status: String? = null

}