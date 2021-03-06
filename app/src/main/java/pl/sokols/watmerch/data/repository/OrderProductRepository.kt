package pl.sokols.watmerch.data.repository

import kotlinx.coroutines.flow.Flow
import pl.sokols.watmerch.data.local.dao.OrderProductDao
import pl.sokols.watmerch.data.model.OrderProduct
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderProductRepository @Inject constructor(
    private val orderProductDao: OrderProductDao
) {
    val allOrderProducts: Flow<List<OrderProduct>> = orderProductDao.getAllOrderProducts()

    suspend fun updateOrderProduct(orderProduct: OrderProduct) =
        orderProductDao.updateOrderProduct(orderProduct)

    suspend fun insertOrderProduct(orderProduct: OrderProduct) =
        orderProductDao.insert(orderProduct)

    suspend fun deleteAllOrderProducts() = orderProductDao.deleteAll()

    suspend fun deleteOrderProductByBarcode(productBarcode: Int) =
        orderProductDao.deleteOrderProductByBarcode(productBarcode)

    fun isInTheCart(productBarcode: Int) = orderProductDao.isInTheCart(productBarcode)
}