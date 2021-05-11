package pl.sokols.watmerch.ui.cart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.data.model.OrderProduct
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.OrderItemBinding
import pl.sokols.watmerch.utils.OnItemClickListener
import pl.sokols.watmerch.utils.Utils

class CartListAdapter(
    private val orderProducts: List<OrderProduct>,
    private val products: List<Product>,
    private val deleteListener: OnItemClickListener,
    private val incrementListener: OnItemClickListener,
    private val decrementListener: OnItemClickListener
) : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {

    inner class CartListViewHolder(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            product: Product,
            orderProduct: OrderProduct,
            deleteListener: OnItemClickListener,
            incrementListener: OnItemClickListener,
            decrementListener: OnItemClickListener
        ) {
            binding.product = product
            binding.orderProduct = orderProduct
            binding.imageCartImageView.setImageBitmap(Utils.getBitmapFromString(product.basicDetails?.logoImage))

            binding.deleteOrderImageView.setOnClickListener {
                deleteListener.onClick(product)
            }

            binding.plusOrderButton.setOnClickListener {
                if (orderProduct.quantity < 10) {
                    incrementListener.onClick(product)
                }
            }

            binding.minusOrderButton.setOnClickListener {
                if (orderProduct.quantity > 1) {
                    decrementListener.onClick(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        return CartListViewHolder(
            OrderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        holder.bind(
            products[position],
            Utils.findOrderProductByProduct(orderProducts, products[position]),
            deleteListener,
            incrementListener,
            decrementListener
        )
    }

    override fun getItemCount() = products.size
}