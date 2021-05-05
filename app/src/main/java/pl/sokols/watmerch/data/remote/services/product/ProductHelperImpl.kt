package pl.sokols.watmerch.data.remote.services.product

import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.model.response.ProductResponse
import javax.inject.Inject

class ProductHelperImpl @Inject constructor(private val productService: ProductService) :
    ProductHelper {
    override suspend fun getProducts(options: Map<String, String>): ProductResponse = productService.getProducts(options)

    override suspend fun getProductByBarcode(barcode: Int): Product = productService.getProductByBarcode(barcode)
}