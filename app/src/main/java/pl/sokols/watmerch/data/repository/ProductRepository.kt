package pl.sokols.watmerch.data.repository

import pl.sokols.watmerch.data.remote.services.product.ProductHelper
import pl.sokols.watmerch.data.remote.services.product.ProductService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productHelper: ProductHelper
) {
    suspend fun getProducts(options: Map<String, String>) = productHelper.getProducts(options)

    suspend fun getProductByBarcode(barcode: Int) = productHelper.getProductByBarcode(barcode)
}