package pl.sokols.watmerch.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.R

class MerchListAdapter(
    private val dataSet: Array<String>
) : RecyclerView.Adapter<MerchListAdapter.MerchListViewHolder>() {

    class MerchListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleMerchItem: TextView = view.findViewById(R.id.titleMerchItem)
        fun bind(title: String) {
            titleMerchItem.text = title
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