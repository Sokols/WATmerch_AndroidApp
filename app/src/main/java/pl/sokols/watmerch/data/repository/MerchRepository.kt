package pl.sokols.watmerch.data.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import pl.sokols.watmerch.data.dao.MerchDao
import pl.sokols.watmerch.data.model.Product

class MerchRepository(private val merchDao: MerchDao) {

    val allProduct: Flow<List<Product>> = merchDao.getAlphabetizedMerch()

    @WorkerThread
    suspend fun insert(product: Product) {
        merchDao.insertAll(product)
    }

    @WorkerThread
    suspend fun delete(product: Product) {
        merchDao.delete(product)
    }

    @WorkerThread
    suspend fun deleteAll() {
        merchDao.deleteAll()
    }
}