package pl.sokols.watmerch.ui.cart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.data.model.Product
import pl.sokols.watmerch.databinding.OrderItemBinding
import pl.sokols.watmerch.utils.OnItemClickListener
import pl.sokols.watmerch.utils.Utils

class CartListAdapter(
    private val dataSet: List<Product>,
    private val deleteListener: OnItemClickListener,
    private val incrementListener: OnItemClickListener,
    private val decrementListener: OnItemClickListener
) : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {

    inner class CartListViewHolder(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var amount: ObservableField<Int> = ObservableField(1)

        fun bind(
            product: Product,
            deleteListener: OnItemClickListener,
            incrementListener: OnItemClickListener,
            decrementListener: OnItemClickListener
        ) {
            binding.viewHolder = this
            binding.product = product
            binding.imageCartImageView.setImageBitmap(Utils.getBitmapFromString(product.basicDetails?.logoImage))

            binding.deleteOrderImageView.setOnClickListener {
                deleteListener.onClick(product)
            }

            binding.plusOrderButton.setOnClickListener {
                amount.set(amount.get()?.plus(1))
                incrementListener.onClick(product)
            }

            binding.minusOrderButton.setOnClickListener {
                if (amount.get() != 1) {
                    amount.set(amount.get()?.minus(1))
                    decrementListener.onClick(product)
                }
            }

            binding.executePendingBindings()
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
        holder.bind(dataSet[position], deleteListener, incrementListener, decrementListener)
    }

    override fun getItemCount() = dataSet.size
}