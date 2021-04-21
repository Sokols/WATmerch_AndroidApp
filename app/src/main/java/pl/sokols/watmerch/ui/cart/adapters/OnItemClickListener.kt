package pl.sokols.watmerch.ui.cart.adapters

import pl.sokols.watmerch.data.model.Product

interface OnItemClickListener {
    fun onClick(product: Product)
}