package pl.sokols.watmerch.data.model

data class OrderProduct(
    val id: Int,
    val quantity: Int,
    val product: Product?,
    val purchase: Purchase?
)