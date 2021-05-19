package pl.sokols.watmerch.data.model

import androidx.room.Ignore

data class Product(
    val barcode: Int,
    var name: String,
    val price: Float,
    @Ignore
    val vat: Float? = null,
    @Ignore
    val category: Category? = null,
    @Ignore
    val basicDetails: ProductBasicDetails? = null,
    @Ignore
    val details: ProductDetails? = null
) {
    constructor(barcode: Int, name: String, price: Float) : this(
        barcode = barcode,
        name = name,
        price = price,
        vat = null,
        category = null,
        basicDetails = null,
        details = null
    )
}