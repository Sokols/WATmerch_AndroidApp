package pl.sokols.watmerch.data.model

data class User(
    val id: Int,
    val email: String,
    val enabled: Boolean,
    val password: String,
    val username: String,
    var billingAddress: Address?,
    var role: Role?,
    var shippingAddress: Address?,
    var userDetails: UserDetails?,
    var creditCards: List<CreditCard>?
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
                userDetails = UserDetails(),
                creditCards = ArrayList<CreditCard>()
            )

    constructor(password: String, username: String) :
            this(
                id = Int.MIN_VALUE,
                email = "",
                enabled = true,
                password= password,
                username = username,
                billingAddress = null,
                role = null,
                shippingAddress = null,
                userDetails = UserDetails(),
                creditCards = ArrayList<CreditCard>()
            )
}