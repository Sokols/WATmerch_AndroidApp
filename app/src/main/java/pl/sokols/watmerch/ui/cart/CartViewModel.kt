package pl.sokols.watmerch.ui.cart

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.sokols.watmerch.data.model.OrderProduct
import pl.sokols.watmerch.data.model.Product
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
        orderProductRepository.insertOrderProduct(OrderProduct(productBarcode = productBarcode))
    }

    fun updateProducts(orderProducts: List<OrderProduct>) = liveData {
        val productList: MutableList<Product> = mutableListOf()
        for (orderProduct: OrderProduct in orderProducts) {
            productList.add(productRepository.getProductByBarcode(orderProduct.productBarcode!!))
        }
        emit(productList.toList())
        setTotal(productList.toList())
    }

    fun updateTotal(price: Float) {
        total.set(total.get()?.plus(price))
    }

    private fun setTotal(products: List<Product>) {
        var sum = 0f
        for (product: Product in products) {
            sum += product.price
        }
        total.set(sum)
    }
}