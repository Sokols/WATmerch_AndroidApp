package pl.sokols.watmerch.data.repository


import pl.sokols.watmerch.data.remote.services.ProductService

class ProductRepository(
    private val productService: ProductService
) {

    suspend fun getCategories() = productService.getCategories()

    suspend fun getProducts() = productService.getProducts()

    suspend fun getProductByBarcode(barcode: Int) = productService.getProductByBarcode(barcode)
}