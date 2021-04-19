package pl.sokols.watmerch.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.sokols.watmerch.data.model.Merch

@Dao
interface MerchDao {
    @Query("SELECT * FROM merch")
    fun getAll(): List<Merch>

    @Query("SELECT * FROM merch WHERE id IN (:merchIds)")
    fun loadAllByIds(merchIds: IntArray): List<Merch>

    @Query("SELECT * FROM merch WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Merch

    @Query("SELECT * FROM merch ORDER BY name ASC")
    fun getAlphabetizedMerch(): Flow<List<Merch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg merch: Merch)

    @Delete
    suspend fun delete(merch: Merch)

    @Query("DELETE FROM merch")
    suspend fun deleteAll()
}