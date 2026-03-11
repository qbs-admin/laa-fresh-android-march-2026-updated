package com.qbs.laafresh.data.network.api

import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.network.api.request.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface IAccountApi {

    @POST(EDIT_PROFILE)
    fun editProfile(
        @Body editProfileRequest: EditProfileRequest
    ): Deferred<Response<EditProfileResponse>>


    @POST(GET_PROFILE)
    fun getProfile(
        @Body getWishListRequest: GetWishListRequest
    ): Deferred<Response<GetProfileResponse>>

    @POST(GET_ADDRESS)
    fun getAddress(
        @Body getWishListRequest: GetWishListRequest
    ): Deferred<Response<GetAddressResponse>>

    @POST(ADD_ADDRESS)
    fun addAddress(
        @Body addAddressRequest: AddAddressRequest
    ): Deferred<Response<AddAddressResponse>>

    @POST(UPDATE_ADDRESS)
    fun updateAddress(
        @Body addAddressRequest: UpdateAddressRequest
    ): Deferred<Response<AddAddressResponse>>

    @POST(DELETE_ADDRESS)
    fun deleteAddress(
        @Body getAddressDetailsRequest: GetAddressDetailsRequest
    ): Deferred<Response<AddAddressResponse>>

    @POST(PIN_CODE)
    fun pinCode(
        @Body pincodeRequest: PincodeRequest
    ): Deferred<Response<PincodeResponse>>

    @POST(GET_ADDRESS_DETAILS)
    fun getAddressDetails(
        @Body getAddressDetailsRequest: GetAddressDetailsRequest
    ): Deferred<Response<GetDefaultAddressResponse>>


    companion object {
        const val EDIT_PROFILE: String = "editprofile.php"
        const val GET_PROFILE: String = "profile.php"
        const val GET_ADDRESS: String = "address.php"
        const val ADD_ADDRESS: String = "add_address.php"
        const val PIN_CODE: String = "isvalidpincode.php"
        const val GET_ADDRESS_DETAILS: String = "getaddressdetails.php"
        const val UPDATE_ADDRESS: String = "update_address.php"
        const val DELETE_ADDRESS: String = "delete_address.php"

    }
}