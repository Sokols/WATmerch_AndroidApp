package pl.sokols.watmerch.data.model.response

import pl.sokols.watmerch.data.model.Product

data class ProductResponse(
    val content: List<Product>
)