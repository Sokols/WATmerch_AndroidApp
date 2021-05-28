package pl.sokols.watmerch.data.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    var city: String = "",
    var country: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var postalCode: String = "",
    var state: String? = null,
    var street: String = ""
) : Parcelable {
    @IgnoredOnParcel
    var id: Int? = null

    override fun toString(): String {
        return "\n$firstName\n$lastName\n$phoneNumber\n$street\n$postalCode\n$city\n$country"
    }
}
