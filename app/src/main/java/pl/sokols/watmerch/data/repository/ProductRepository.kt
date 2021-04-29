package pl.sokols.watmerch.data.repository


import pl.sokols.watmerch.data.remote.services.ProductService

class ProductRepository(
    private val productService: ProductService
) {
    suspend fun getProducts(options: Map<String, String>) = productService.getProducts(options)

    suspend fun getProductByBarcode(barcode: Int) = productService.getProductByBarcode(barcode)
}