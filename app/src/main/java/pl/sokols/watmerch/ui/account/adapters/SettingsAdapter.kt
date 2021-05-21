package pl.sokols.watmerch.ui.account.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.SettingsItemBinding
import pl.sokols.watmerch.utils.Utils

class SettingsAdapter(
    private val dataSet: List<String>
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {
    inner class SettingsViewHolder(private val binding: SettingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(setting: String) {
            binding.setting = setting

            binding.settingItem.setOnClickListener { view ->
                when (setting) {
                    view.resources.getString(R.string.my_data) -> {
                        val bundle =
                            bundleOf(Utils.PARENT_COMPONENT_ID to view.resources.getString(R.string.account_page))
                        view.findNavController()
                            .navigate(R.id.action_accountFragment_to_userFragment, bundle)
                    }
                    view.resources.getString(R.string.app_settings) -> {
                        view.findNavController()
                            .navigate(R.id.action_accountFragment_to_settingsFragment)
                    }
                    // TODO: add another navigation
                }
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