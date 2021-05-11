package pl.sokols.watmerch.data.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val city: String,
    val country: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val postalCode: String,
    val state: String? = null,
    val street: String
) : Parcelable {
    @IgnoredOnParcel
    var id: Int? = null

    override fun toString(): String {
        return "Imię: $firstName\nNazwisko: $lastName\nNr telefonu: $phoneNumber\n"
    }
}
