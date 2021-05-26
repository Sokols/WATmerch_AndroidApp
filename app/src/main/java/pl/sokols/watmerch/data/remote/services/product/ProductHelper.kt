package pl.sokols.watmerch.data.remote.services.product

import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.model.response.ProductResponse

interface ProductHelper {

    suspend fun getProducts(options: Map<String, String>): ProductResponse

    suspend fun getProductByBarcode(barcode: Int): Product

    suspend fun addEditProduct(product: Product): Product
}