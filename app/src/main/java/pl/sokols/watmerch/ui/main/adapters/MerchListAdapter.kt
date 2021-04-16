package pl.sokols.watmerch.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.R
import pl.sokols.watmerch.data.Merch

class MerchListAdapter(
    private val dataSet: Array<Merch>
) : RecyclerView.Adapter<MerchListAdapter.MerchListViewHolder>() {

    class MerchListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cardMerchItem: CardView = view.findViewById(R.id.cardMerchItem)
        private val titleMerchItem: TextView = view.findViewById(R.id.titleMerchItem)
        private val priceMerchItem: TextView = view.findViewById(R.id.priceMerchItem)

        fun bind(merch: Merch) {
            titleMerchItem.text = merch.name
            priceMerchItem.text =
                String.format(itemView.context.getString(R.string.price), merch.price)

            cardMerchItem.setOnClickListener { view ->
                val bundle = bundleOf("merchItem" to merch)
                view.findNavController().navigate(R.id.action_mainFragment_to_merchFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.merch_item, parent, false)

        return MerchListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MerchListViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}