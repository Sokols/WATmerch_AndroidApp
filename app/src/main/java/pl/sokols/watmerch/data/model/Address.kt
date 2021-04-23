package pl.sokols.watmerch.data.model

data class Address(
    val id: Int,
    val city: String,
    val country: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val postalCode: String,
    val state: String?,
    val street: String
)
