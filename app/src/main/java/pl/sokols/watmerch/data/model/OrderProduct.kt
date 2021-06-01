package pl.sokols.watmerch.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "order_products")
data class OrderProduct(
    var quantity: Int = 1,
    @Embedded(prefix = "product_")
    var product: Product? = null
) : Cloneable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @Ignore
    var purchase: Purchase? = null

    public override fun clone(): OrderProduct {
        val clone: OrderProduct
        try {
            clone = super.clone() as OrderProduct
        } catch (e: CloneNotSupportedException) {
            throw RuntimeException(e)
        }
        return clone
    }
}