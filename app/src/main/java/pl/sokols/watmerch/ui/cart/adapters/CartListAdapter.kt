package pl.sokols.watmerch.ui.cart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.model.Merch

class CartListAdapter(
    private val dataSet: List<Merch>
) : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>() {

    class CartListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cardMerchItem: CardView = view.findViewById(R.id.cardMerchItem)
        private val titleMerchItem: TextView = view.findViewById(R.id.titleMerchItem)
        private val priceMerchItem: TextView = view.findViewById(R.id.priceMerchItem)

        fun bind(merch: Merch) {
            titleMerchItem.text = merch.name
            priceMerchItem.text =
                String.format(itemView.context.getString(R.string.price), merch.price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.merch_item, parent, false)

        return CartListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}