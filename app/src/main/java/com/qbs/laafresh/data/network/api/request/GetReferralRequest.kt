package com.qbs.laafresh.data.network.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetReferralRequest  {

    @SerializedName("referer_id")
    @Expose
    var referer_id : String?=null

}