package pl.sokols.watmerch.ui.cart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.OrderItemBinding

class CartListAdapter(
    private val dataSet: List<Product>,
    private val deleteListener: OnItemClickListener
) : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {

    inner class CartListViewHolder(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            product: Product,
            deleteListener: OnItemClickListener
        ) {
            binding.titleOrderItem.text = product.name
            binding.priceOrderItem.text =
                String.format(itemView.context.getString(R.string.price), product.price)

            binding.deleteOrderImageView.setOnClickListener {
                deleteListener.onClick(product)
            }

            binding.plusOrderButton.setOnClickListener {
                binding.orderAmountTextView.text =
                    (binding.orderAmountTextView.text.toString().toInt() + 1).toString()
            }

            binding.minusOrderButton.setOnClickListener {
                if (binding.orderAmountTextView.text.toString() != "1") {
                    binding.orderAmountTextView.text =
                        (binding.orderAmountTextView.text.toString().toInt() - 1).toString()
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
        holder.bind(dataSet[position], deleteListener)
    }

    override fun getItemCount() = dataSet.size
}