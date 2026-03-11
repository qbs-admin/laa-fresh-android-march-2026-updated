package com.qbs.laafresh.data.network.api.reponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SlidersResponse(

    @SerializedName("slides")
    @Expose
    val slides: List<SlidesItem?>? = null,

    @SerializedName("success")
    @Expose
    val success: Int? = null,

    @SerializedName("message")
    @Expose
    val message: String? = null
)

class SlidesItem(

    @SerializedName("images")
    @Expose
    val images: String? = null
)