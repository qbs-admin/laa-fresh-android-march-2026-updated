package com.qbs.laafresh.data.dbhelper.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class DeliveryAddressEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    var sno: Int = 0
    var addressId: String = ""
    var customerName:String="";
    var mobileNo:String="";
    var address1:String="";
    var address2:String="";
    var city:String="";
    var country:String="";
    var state:String="";
    var pinCode:String="";
    var addressType:String="";

    constructor(addressId: String,customerName: String, mobileNo: String, address1: String, address2: String,city: String, state: String,country: String, pinCode: String, addressType: String) {
        this.addressId = addressId
        this.customerName = customerName
        this.mobileNo = mobileNo
        this.address1 = address1
        this.address2 = address2
        this.city = city
        this.country = country
        this.state = state
        this.pinCode = pinCode
        this.addressType = addressType
    }
}