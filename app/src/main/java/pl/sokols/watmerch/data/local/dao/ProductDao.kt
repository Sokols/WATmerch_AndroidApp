package pl.sokols.watmerch.data.local.dao

//@Dao
//interface ProductDao {
//    @Query("SELECT * FROM product")
//    fun getAll(): List<Product>
//
//    @Query("SELECT * FROM product WHERE barcode IN (:barcodes)")
//    fun loadAllByIds(barcodes: IntArray): List<Product>
//
//    @Query("SELECT * FROM product WHERE name LIKE :name LIMIT 1")
//    fun findByName(name: String): Product
//
//    @Query("SELECT * FROM product ORDER BY name ASC")
//    fun getAlphabetizedMerch(): Flow<List<Product>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(vararg products: Product)
//
//    @Delete
//    suspend fun delete(product: Product)
//
//    @Query("DELETE FROM product")
//    suspend fun deleteAll()
//}