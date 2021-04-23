package pl.sokols.watmerch.data.model

data class User(
    val id: Int,
    val email: String,
    val enabled: Boolean,
    val password: String,
    val username: String,
    val billingAddress: Address?,
    val role: Role?,
    val shippingAddress: Address?,
    val userDetails: UserDetails?,
    val creditCards: List<CreditCard>?
) {
    constructor(email: String, enabled: Boolean, password: String, username: String) :
            this(
                Int.MIN_VALUE,
                email,
                enabled,
                password,
                username,
                null,
                null,
                null,
                null,
                ArrayList<CreditCard>()
            )
}