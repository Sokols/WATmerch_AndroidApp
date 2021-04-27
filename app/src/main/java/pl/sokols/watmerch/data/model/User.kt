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
    constructor(email: String, password: String, username: String) :
            this(
                id = Int.MIN_VALUE,
                email = email,
                enabled = true,
                password= password,
                username = username,
                billingAddress = null,
                role = null,
                shippingAddress = null,
                userDetails = null,
                creditCards = ArrayList<CreditCard>()
            )
}