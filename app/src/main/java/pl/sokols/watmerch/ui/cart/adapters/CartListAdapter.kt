package pl.sokols.watmerch.ui.cart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Merch
import pl.sokols.watmerch.databinding.OrderItemBinding

class CartListAdapter(
    private val dataSet: List<Merch>,
    private val deleteListener: OnItemClickListener
) : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {

    inner class CartListViewHolder(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            merch: Merch,
            deleteListener: OnItemClickListener
        ) {
            binding.titleOrderItem.text = merch.name
            binding.priceOrderItem.text =
                String.format(itemView.context.getString(R.string.price), merch.price)

            binding.deleteOrderImageView.setOnClickListener {
                deleteListener.onClick(merch)
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