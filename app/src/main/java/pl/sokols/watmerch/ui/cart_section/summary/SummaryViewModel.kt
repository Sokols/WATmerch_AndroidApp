package pl.sokols.watmerch.ui.cart_section.summary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import pl.sokols.watmerch.data.model.Address
import pl.sokols.watmerch.data.model.Purchase
import pl.sokols.watmerch.data.repository.OrderProductRepository
import pl.sokols.watmerch.data.repository.PurchaseRepository
import pl.sokols.watmerch.data.repository.UserRepository
import pl.sokols.watmerch.di.PreferencesModule
import pl.sokols.watmerch.utils.Resource
import pl.sokols.watmerch.utils.Utils
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val orderProductRepository: OrderProductRepository,
    private val purchaseRepository: PurchaseRepository,
    private val userRepository: UserRepository,
    private val prefs: PreferencesModule
) : ViewModel() {

    lateinit var shippingAddress: Address
    lateinit var billingAddress: Address

    fun setAddresses(shippingAddress: Address, billingAddress: Address) {
        this.shippingAddress = shippingAddress
        this.billingAddress = billingAddress
    }

    fun makePurchase() = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            val purchase = Purchase(
                purchaseDate = Utils.getStringFromDate(Date()),
//                shippingAddress = shippingAddress,
//                billingAddress = billingAddress,
//                user = userRepository.loginUser(
//                    User(
//                        username = prefs.userUsername.toString(),
//                        password = prefs.userPassword.toString()
//                    )
//                ),
                // TODO: Find a way to add order products.
                // orderProducts = orderProductRepository.allOrderProducts.first()
            )
            purchaseRepository.makePurchase(purchase)
            emit(Resource.success(data = "Złożono zamówienie!"))
            orderProductRepository.deleteAllOrderProducts()
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            Log.i("ERROR", exception.message.toString())
            Log.i("ERROR", exception.printStackTrace().toString())
        }
    }
}