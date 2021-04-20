package pl.sokols.watmerch.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.R
import pl.sokols.watmerch.utils.Utils
import pl.sokols.watmerch.data.model.Merch
import pl.sokols.watmerch.databinding.MerchItemBinding

class MerchListAdapter(
    private val dataSet: Array<Merch>
) : RecyclerView.Adapter<MerchListAdapter.MerchListViewHolder>() {

    inner class MerchListViewHolder(private val binding: MerchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(merch: Merch) {
            binding.titleMerchItem.text = merch.name
            binding.priceMerchItem.text =
                String.format(itemView.context.getString(R.string.price), merch.price)

            binding.cardMerchItem.setOnClickListener { view ->
                val bundle = bundleOf(Utils.MERCH_ITEM_KEY to merch)
                view.findNavController().navigate(R.id.action_mainFragment_to_merchFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchListViewHolder {
        return MerchListViewHolder(
            MerchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MerchListViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}