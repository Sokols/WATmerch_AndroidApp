package pl.sokols.watmerch.ui.cart

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.sokols.watmerch.data.model.OrderProduct
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.data.repository.OrderProductRepository
import pl.sokols.watmerch.data.repository.ProductRepository
import pl.sokols.watmerch.utils.Resource
import pl.sokols.watmerch.utils.Utils
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
        orderProductRepository.insertOrderProduct(OrderProduct(productBarcode = productBarcode))
    }

    fun updateProducts(orderProducts: List<OrderProduct>) = liveData {
        emit(Resource.loading(data = null))
        try {
            val productList: MutableList<Product> = mutableListOf()
            for (orderProduct: OrderProduct in orderProducts) {
                productList.add(productRepository.getProductByBarcode(orderProduct.productBarcode!!))
            }
            setTotal(orderProducts, productList.toList())
            emit(Resource.success(data = productList.toList()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occurred"))
            Log.e("ERROR", exception.message.toString())
        }
    }

    fun updateTotal(product: Product, isIncrement: Boolean) = viewModelScope.launch {
        val orderProduct = orderProductRepository.getOrderProductByBarcode(product.barcode)
        if (isIncrement) {
            orderProduct.quantity += 1
        } else {
            orderProduct.quantity -= 1
        }
        orderProductRepository.updateOrderProduct(orderProduct)
    }

    private fun setTotal(orderProducts: List<OrderProduct>, products: List<Product>) {
        var sum = 0f
        for (product: Product in products) {
            sum += product.price * Utils.findOrderProductByProduct(orderProducts, product).quantity
        }
        total.set(sum)
    }
}