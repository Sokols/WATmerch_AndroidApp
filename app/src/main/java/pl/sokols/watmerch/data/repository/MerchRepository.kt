package pl.sokols.watmerch.data.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import pl.sokols.watmerch.data.dao.MerchDao
import pl.sokols.watmerch.data.model.Merch

class MerchRepository(private val merchDao: MerchDao) {

    val allMerch: Flow<List<Merch>> = merchDao.getAlphabetizedMerch()

    @WorkerThread
    suspend fun insert(merch: Merch) {
        merchDao.insertAll(merch)
    }

    @WorkerThread
    suspend fun delete(merch: Merch) {
        merchDao.delete(merch)
    }

    @WorkerThread
    suspend fun deleteAll() {
        merchDao.deleteAll()
    }
}