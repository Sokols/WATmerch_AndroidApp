package pl.sokols.watmerch.data.model

data class ProductDetails(
    val longDescription: String? = null,
    val quantityInStock: Int
) {
    var id: Int? = null
}