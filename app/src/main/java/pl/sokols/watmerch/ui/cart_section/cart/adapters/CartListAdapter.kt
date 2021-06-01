package pl.sokols.watmerch.ui.cart_section.cart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.data.model.OrderProduct
import pl.sokols.watmerch.databinding.OrderItemBinding
import pl.sokols.watmerch.utils.callbacks.OnItemClickCallback
import pl.sokols.watmerch.utils.callbacks.OrderProductDiffCallback
import pl.sokols.watmerch.utils.Utils

class CartListAdapter(
    private val deleteCallback: OnItemClickCallback,
    private val incrementCallback: OnItemClickCallback,
    private val decrementCallback: OnItemClickCallback
) : ListAdapter<OrderProduct, CartListAdapter.CartListViewHolder>(OrderProductDiffCallback) {

    inner class CartListViewHolder(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            orderProduct: OrderProduct,
            deleteCallback: OnItemClickCallback,
            incrementCallback: OnItemClickCallback,
            decrementCallback: OnItemClickCallback
        ) {
            binding.orderProduct = orderProduct
            binding.imageCartImageView.setImageBitmap(Utils.getBitmapFromString(orderProduct.product!!.basicDetails?.logoImage))

            binding.deleteOrderImageView.setOnClickListener {
                deleteCallback.onClick(orderProduct)
            }

            binding.plusOrderButton.setOnClickListener {
                if (orderProduct.quantity < 9) {
                    incrementCallback.onClick(orderProduct)
                }
            }

            binding.minusOrderButton.setOnClickListener {
                if (orderProduct.quantity > 1) {
                    decrementCallback.onClick(orderProduct)
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
            getItem(position),
            deleteCallback,
            incrementCallback,
            decrementCallback
        )
    }
}