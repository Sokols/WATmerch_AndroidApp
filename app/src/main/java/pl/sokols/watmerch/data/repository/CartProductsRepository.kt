package pl.sokols.watmerch.data.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import pl.sokols.watmerch.data.dao.ProductDao
import pl.sokols.watmerch.data.model.Product

class CartProductsRepository(private val productDao: ProductDao) {

    val allProduct: Flow<List<Product>> = productDao.getAlphabetizedMerch()

    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insertAll(product)
    }

    @WorkerThread
    suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    @WorkerThread
    suspend fun deleteAll() {
        productDao.deleteAll()
    }
}