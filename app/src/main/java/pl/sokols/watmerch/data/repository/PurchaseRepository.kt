package pl.sokols.watmerch.data.repository

import pl.sokols.watmerch.data.model.Purchase
import pl.sokols.watmerch.data.remote.services.purchase.PurchaseHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PurchaseRepository @Inject constructor(
    private val purchaseHelper: PurchaseHelper
) {
    suspend fun makePurchase(purchase: Purchase) = purchaseHelper.makePurchase(purchase)
}