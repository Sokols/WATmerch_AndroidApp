package pl.sokols.watmerch.data.model

data class CreditCard(
    val id: Int,
    val cardNumber: String,
    val expirationDate: String,
    val securityCode: String,
    val user: User?
)
