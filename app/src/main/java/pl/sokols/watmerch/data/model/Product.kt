package pl.sokols.watmerch.data.model

import androidx.room.Ignore

data class Product(
    var barcode: Int,
    var name: String,
    var price: Float,
    @Ignore
    var vat: Float? = null,
    @Ignore
    var category: Category? = null,
    @Ignore
    var basicDetails: ProductBasicDetails? = null,
    @Ignore
    var details: ProductDetails? = null
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

    override fun toString(): String {
        return "$barcode - $name"
    }
}