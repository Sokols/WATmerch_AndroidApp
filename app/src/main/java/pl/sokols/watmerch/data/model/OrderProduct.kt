package pl.sokols.watmerch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_products")
data class OrderProduct(
    var quantity: Int = 1,
    var productBarcode: Int? = null,
    var purchaseId: Int? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}