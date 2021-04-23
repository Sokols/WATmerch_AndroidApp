package pl.sokols.watmerch.data.remote.services

import pl.sokols.watmerch.data.model.Category
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.model.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("api/categories")
    suspend fun getCategories(): Category

    @GET("api/products")
    suspend fun getProducts(): ProductResponse

    @GET("api/products/{barcode}")
    suspend fun getProductByBarcode(@Path("barcode") barcode: Int): Product
}