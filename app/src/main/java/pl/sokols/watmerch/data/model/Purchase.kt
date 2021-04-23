package pl.sokols.watmerch.data.model

import java.time.LocalDate

data class Purchase(
    val id: Int,
    val isFinished: Boolean?,
    val isPaid: Boolean?,
    val purchaseDate: LocalDate?,
    val billingAddress: Address?,
    val shippingAddress: Address?,
    val user: User?
)
