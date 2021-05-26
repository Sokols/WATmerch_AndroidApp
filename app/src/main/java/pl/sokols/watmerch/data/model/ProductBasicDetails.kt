package pl.sokols.watmerch.data.model

data class ProductBasicDetails(
    val discountPercent: Float? = null,
    val logoImage: String? = null,
    val shortDescription: String
) {
    var id: Int? = null
}