package com.qbs.laafresh.data.preference

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(private val context: Context) : IPreferenceManager {

    private val pref: SharedPreferences =
        context.getSharedPreferences("delivery_fresh_preference", Context.MODE_PRIVATE)


    override fun saveToken(token: String?) = pref.edit().putString(TOKEN, token).apply()


    override fun getToken(): String = pref.getString(TOKEN, "")!!

    override fun setIsLoggedIn(status: Boolean) = pref.edit().putBoolean(LOGGED_IN, status).apply()

    override fun getIsLoggedIn(): Boolean = pref.getBoolean(LOGGED_IN, false)
    override fun setIsDefaultAddressAvailable(status: Boolean) =
        pref.edit().putBoolean(DEFAULT_ADDRESS, status).apply()

    override fun isDefaultAddressAvailable(): Boolean= pref.getBoolean(DEFAULT_ADDRESS, false)

    override fun setIsStoreSelected(status: Boolean) =
        pref.edit().putBoolean(STORE_SETECTED, status).apply()

    override fun getIsStoreSelected(): Boolean = pref.getBoolean(STORE_SETECTED, false)


    override fun setCustomerId(customerId: String) =
        pref.edit().putString(CUSTOMER_ID, customerId).apply()

    override fun getCustomerId(): String = pref.getString(CUSTOMER_ID, "")!!


    override fun setCartCout(customerId: String) =
        pref.edit().putString(CART_COUNT, customerId).apply()

    override fun getCartCout(): String = pref.getString(CART_COUNT, "")!!


    override fun setDeviceToken(deviceToken: String) =
        pref.edit().putString(DEVICE_TOKEN, deviceToken).apply()

    override fun getDeviceToken(): String = pref.getString(DEVICE_TOKEN, "")!!


    override fun setMiniAmount(miniAmount: String) =
        pref.edit().putString(MINI_AMOUNT, miniAmount).apply()

    override fun getMiniAmount(): String = pref.getString(MINI_AMOUNT, "")!!
    override fun setShippingCost(shippingAmount: String)=pref.edit().putString(SHIPPING_COST,shippingAmount).apply()

    override fun getShippingCost(): String=pref.getString(SHIPPING_COST,"")!!


    override fun setDeliveryType(deliveryType: String) =
        pref.edit().putString(DELIVERY_TYPE, deliveryType).apply()

    override fun getDeliveryType(): String = pref.getString(DELIVERY_TYPE, "")!!


    override fun setFirebaseToken(firebaseToken: String) =
        pref.edit().putString(FIREBASE_TOKEN, firebaseToken).apply()

    override fun getFirebaseToken(): String = pref.getString(FIREBASE_TOKEN, "")!!

    override fun setUserPhoneNumber(PhoneNumber: String) =
        pref.edit().putString(PHONE_NUMBER, PhoneNumber).apply()

    override fun getUserPhoneNumber(): String = pref.getString(PHONE_NUMBER, "")!!


    override fun setDateSelected(DateSelected: String) =
        pref.edit().putString(DATE_SELECTED, DateSelected).apply()

    override fun getDateSelected(): String = pref.getString(DATE_SELECTED, "")!!


    override fun setTimeSelected(TimeSelected: String) =
        pref.edit().putString(TIME_SELECTED, TimeSelected).apply()

    override fun getTimeSelected(): String = pref.getString(TIME_SELECTED, "")!!


    override fun setSelectedStore(storeSelected: String) =
        pref.edit().putString(STORE_SELECTED_NAME, storeSelected).apply()

    override fun getSelectedStore(): String = pref.getString(STORE_SELECTED_NAME, "")!!

    override fun setHomeViewType(ViewType: Int) =
        pref.edit().putInt(HOME_VIEW_TYPE, ViewType).apply()

    override fun getHomeViewType(): Int = pref.getInt(HOME_VIEW_TYPE, 0)!!

    override fun clearPreferenceManager() {
        pref.edit().clear().apply()
    }

    companion object {
        const val TOKEN = "token"
        const val LOGGED_IN = "status"
        const val DEFAULT_ADDRESS = "default_address"
        const val CUSTOMER_ID = "customerId"
        const val CART_COUNT = "cart_cout"
        const val DEVICE_TOKEN = "device_token"
        const val FIREBASE_TOKEN = "firebase_token"
        const val PHONE_NUMBER = "phone_number"
        const val HOME_VIEW_TYPE = "home_view"
        const val MINI_AMOUNT = "miniAmount"
        const val SHIPPING_COST = "shippingCost"
        const val STORE_SETECTED = "store_selected_boolean"
        const val DELIVERY_TYPE = "delivery_type"
        const val DATE_SELECTED = "date_selected"
        const val TIME_SELECTED = "time_selected"
        const val STORE_SELECTED_NAME = "store_selected_name"
    }


}