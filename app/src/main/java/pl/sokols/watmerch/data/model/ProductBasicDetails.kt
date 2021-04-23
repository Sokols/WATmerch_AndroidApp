package pl.sokols.watmerch.data.model

data class ProductBasicDetails(
    val id: Int,
    val discountPercent: Float?,
    val logoImage: String?,
    val shortDescription: String
)