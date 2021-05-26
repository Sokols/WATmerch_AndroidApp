package pl.sokols.watmerch.ui.account_section.account.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pl.sokols.watmerch.R
import pl.sokols.watmerch.databinding.SettingsItemBinding
import pl.sokols.watmerch.utils.Utils

class SettingsAdapter(
    private val dataSet: List<String>,
    private val userRole: String
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {
    inner class SettingsViewHolder(private val binding: SettingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(setting: String, userRole: String) {
            binding.setting = setting

            // Disable employee panel for normal user
            if (userRole == binding.root.resources.getString(R.string.user_role)
                && setting == binding.root.resources.getString(R.string.employee_panel)
            ) {
                binding.settingItem.visibility = View.GONE
            }

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
                    view.resources.getString(R.string.employee_panel) -> {
                        view.findNavController()
                            .navigate(R.id.action_accountFragment_to_employeeFragment)
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
        holder.bind(dataSet[position], userRole)
    }

    override fun getItemCount() = dataSet.size
}