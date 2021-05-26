package pl.sokols.watmerch.data.remote.services.product

import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.model.response.ProductResponse
import retrofit2.http.*

interface ProductService {

    @GET("api/products")
    suspend fun getProducts(@QueryMap options: Map<String, String>): ProductResponse

    @GET("api/products/{barcode}")
    suspend fun getProductByBarcode(@Path("barcode") barcode: Int): Product

    @POST("api/products")
    suspend fun addEditProduct(@Body product: Product): Product
}