package pl.sokols.watmerch.data.model

data class Product(
    val barcode: Int,
    val name: String,
    val price: Float,
    val vat: Float?,
    val category: Category?,
    val basicDetails: ProductBasicDetails?,
    val details: ProductDetails?
)