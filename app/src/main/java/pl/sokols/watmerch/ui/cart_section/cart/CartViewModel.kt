package pl.sokols.watmerch.ui.cart_section.cart

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.sokols.watmerch.data.model.OrderProduct
import pl.sokols.watmerch.data.repository.OrderProductRepository
import pl.sokols.watmerch.data.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val orderProductRepository: OrderProductRepository
) : ViewModel() {

    var total: ObservableField<Float> = ObservableField(0f)
    var products: LiveData<List<OrderProduct>> =
        orderProductRepository.allOrderProducts.asLiveData()

    fun deleteProduct(productBarcode: Int) = viewModelScope.launch {
        orderProductRepository.deleteOrderProductByBarcode(productBarcode)
    }

    fun insertProduct(productBarcode: Int) = viewModelScope.launch {
        orderProductRepository.insertOrderProduct(
            OrderProduct(product = productRepository.getProductByBarcode(productBarcode))
        )
    }

    fun updateTotal(orderProduct: OrderProduct, isIncrement: Boolean) = viewModelScope.launch {
        if (isIncrement) {
            orderProduct.quantity += 1
        } else {
            orderProduct.quantity -= 1
        }
        orderProductRepository.updateOrderProduct(orderProduct)
    }

    fun setTotal(orderProducts: List<OrderProduct>) {
        var sum = 0f
        for (orderProduct: OrderProduct in orderProducts) {
            sum += orderProduct.product!!.price * orderProduct.quantity
        }
        total.set(sum)
    }
}