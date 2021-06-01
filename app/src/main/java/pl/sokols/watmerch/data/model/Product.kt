package pl.sokols.watmerch.data.model

import androidx.room.Embedded
import androidx.room.Ignore

data class Product(
    var barcode: Int,
    var name: String,
    var price: Float,
    @Ignore
    var vat: Float? = null,
    @Embedded(prefix = "category_")
    var category: Category? = null,
    @Embedded(prefix = "product_basic_details_")
    var basicDetails: ProductBasicDetails? = null,
    @Embedded(prefix = "product_details_")
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