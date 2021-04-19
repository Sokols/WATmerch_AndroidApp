package pl.sokols.watmerch.ui.cart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Merch

class CartListAdapter(
    private val dataSet: List<Merch>,
    private val deleteListener: OnItemClickListener
) : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {

    class CartListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val amount: Int = 1
        private val titleOrderItem: TextView = view.findViewById(R.id.titleOrderItem)
        private val priceOrderItem: TextView = view.findViewById(R.id.priceOrderItem)
        private val deleteOrderImageView: ImageView = view.findViewById(R.id.deleteOrderImageView)
        private val minusOrderButton: Button = view.findViewById(R.id.minusOrderButton)
        private val plusOrderButton: Button = view.findViewById(R.id.plusOrderButton)
        private val orderAmountTextView: TextView = view.findViewById(R.id.orderAmountTextView)

        fun bind(merch: Merch, deleteListener: OnItemClickListener) {
            titleOrderItem.text = merch.name
            priceOrderItem.text =
                String.format(itemView.context.getString(R.string.price), merch.price)

            deleteOrderImageView.setOnClickListener {
                deleteListener.onClick(merch)
            }

            plusOrderButton.setOnClickListener {
                orderAmountTextView.text =
                    (orderAmountTextView.text.toString().toInt() + 1).toString()
            }

            minusOrderButton.setOnClickListener {
                if (orderAmountTextView.text.toString() != "0") {
                    orderAmountTextView.text =
                        (orderAmountTextView.text.toString().toInt() - 1).toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item, parent, false)

        return CartListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        holder.bind(dataSet[position], deleteListener)
    }

    override fun getItemCount() = dataSet.size
}