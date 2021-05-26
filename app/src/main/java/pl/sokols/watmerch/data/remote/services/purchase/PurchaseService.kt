package pl.sokols.watmerch.data.remote.services.purchase

import pl.sokols.watmerch.data.model.Purchase
import retrofit2.http.Body
import retrofit2.http.POST

interface PurchaseService {

    @POST("api/buy")
    suspend fun makePurchase(@Body purchase: Purchase): String
}