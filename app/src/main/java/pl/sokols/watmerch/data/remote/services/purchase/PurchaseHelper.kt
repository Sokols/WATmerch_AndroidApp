package pl.sokols.watmerch.data.remote.services.purchase

import pl.sokols.watmerch.data.model.Purchase

interface PurchaseHelper {

    suspend fun makePurchase(purchase: Purchase): String
}