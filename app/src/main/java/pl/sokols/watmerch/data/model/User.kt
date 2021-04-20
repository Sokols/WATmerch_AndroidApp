package pl.sokols.watmerch.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val email: String,
    val enabled: Boolean,
    val password: String,
    val username: String,
    @SerializedName("billing_address_id")
    val billingAddressId: Int?,
    @SerializedName("role_id")
    val roleId: Int?,
    @SerializedName("shipping_addres_id")
    val shippingAddressId: Int?,
    @SerializedName("user_details_id")
    val userDetailsId: Int?,
) {
    constructor(email: String, enabled: Boolean, password: String, username: String) :
            this(
                Int.MIN_VALUE,
                email,
                enabled,
                password,
                username,
                Int.MIN_VALUE,
                Int.MIN_VALUE,
                Int.MIN_VALUE,
                Int.MIN_VALUE
            )
}