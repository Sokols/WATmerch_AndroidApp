package pl.sokols.watmerch.data.model

data class Purchase(
    val id: Int? = null,
    val isFinished: Boolean? = false,
    val isPaid: Boolean? = false,
    val purchaseDate: String? = null,
    val billingAddress: Address? = null,
    val shippingAddress: Address? = null,
    val user: User? = null,
    val orderProducts: List<OrderProduct>? = null
)
