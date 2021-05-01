package pl.sokols.watmerch.ui.account.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.databinding.SettingsItemBinding

class SettingsAdapter(
    private val dataSet: List<String>
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {
    inner class SettingsViewHolder(private val binding: SettingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(setting: String) {
            binding.setting = setting

            binding.settingItem.setOnClickListener { view ->
                
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return SettingsViewHolder(
            SettingsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}