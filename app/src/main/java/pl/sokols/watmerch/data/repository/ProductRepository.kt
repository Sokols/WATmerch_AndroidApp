package pl.sokols.watmerch.data.repository

import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.remote.services.product.ProductHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productHelper: ProductHelper
) {
    suspend fun getProducts(options: Map<String, String>) = productHelper.getProducts(options)

    suspend fun getProductByBarcode(barcode: Int) = productHelper.getProductByBarcode(barcode)

    suspend fun addEditProduct(product: Product) = productHelper.addEditProduct(product)
}