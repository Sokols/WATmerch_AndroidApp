package pl.sokols.watmerch.data.remote.services.product

import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.model.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProductService {

    @GET("api/products")
    suspend fun getProducts(@QueryMap options: Map<String, String>): ProductResponse

    @GET("api/products/{barcode}")
    suspend fun getProductByBarcode(@Path("barcode") barcode: Int): Product
}