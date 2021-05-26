package pl.sokols.watmerch.data.remote.services.purchase

import pl.sokols.watmerch.data.model.Purchase
import javax.inject.Inject

class PurchaseHelperImpl @Inject constructor(private val purchaseService: PurchaseService) :
    PurchaseHelper {
    override suspend fun makePurchase(purchase: Purchase): String =
        purchaseService.makePurchase(purchase)
}