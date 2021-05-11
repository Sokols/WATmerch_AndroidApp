package pl.sokols.watmerch.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.sokols.watmerch.data.model.OrderProduct

@Dao
interface OrderProductDao {

    @Query("SELECT * FROM order_products")
    fun getAllOrderProducts(): Flow<List<OrderProduct>>

    @Query("SELECT * FROM order_products WHERE productBarcode = :productBarcode")
    suspend fun getOrderProductByBarcode(productBarcode: Int): OrderProduct

    @Update
    @Transaction
    suspend fun updateOrderProduct(orderProduct: OrderProduct)

    @Query("DELETE FROM order_products")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(orderProduct: OrderProduct)

    @Delete
    suspend fun delete(orderProduct: OrderProduct)

    @Query("DELETE FROM order_products WHERE productBarcode = :productBarcode")
    suspend fun deleteOrderProductByBarcode(productBarcode: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM order_products WHERE productBarcode = :productBarcode LIMIT 1)")
    suspend fun isInTheCart(productBarcode: Int): Boolean
}