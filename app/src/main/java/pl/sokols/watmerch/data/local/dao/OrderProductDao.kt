package pl.sokols.watmerch.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.sokols.watmerch.data.model.OrderProduct

@Dao
interface OrderProductDao {

    @Query("SELECT * FROM order_products")
    fun getAllOrderProducts(): Flow<List<OrderProduct>>

    @Update
    @Transaction
    suspend fun updateOrderProduct(orderProduct: OrderProduct)

    @Query("DELETE FROM order_products")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(orderProduct: OrderProduct)

    @Query("DELETE FROM order_products WHERE product_barcode = :productBarcode")
    suspend fun deleteOrderProductByBarcode(productBarcode: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM order_products WHERE product_barcode = :productBarcode LIMIT 1)")
    fun isInTheCart(productBarcode: Int): Boolean
}