package pl.sokols.watmerch.ui.cart.adapters

import pl.sokols.watmerch.data.model.Merch

interface OnItemClickListener {
    fun onClick(merch: Merch)
}