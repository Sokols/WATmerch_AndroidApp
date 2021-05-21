package pl.sokols.watmerch.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import pl.sokols.watmerch.R
import pl.sokols.watmerch.utils.AppPreferences

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var prefs: AppPreferences
    private lateinit var languagePref: ListPreference
    private lateinit var themePref: SwitchPreferenceCompat

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        setPreferencesStartPositions()
        setPreferenceChangeListeners()
    }

    private fun setPreferencesStartPositions() {
        prefs = AppPreferences(requireContext())
        languagePref = findPreference(getString(R.string.choose_language))!!
        themePref = findPreference(getString(R.string.change_theme))!!

        if (prefs.language?.equals(getString(R.string.polish)) == true) {
            languagePref.setValueIndex(0)
        } else {
            languagePref.setValueIndex(1)
        }

        themePref.isChecked = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    private fun setPreferenceChangeListeners() {
        languagePref.setOnPreferenceChangeListener { _, newValue ->
            when (newValue as String) {
                getString(R.string.polish) -> {
                    prefs.language = getString(R.string.polish)
                    true
                }
                getString(R.string.english) -> {
                    prefs.language = getString(R.string.english)
                    true
                }
                else -> {
                    true
                }
            }
        }

        themePref.setOnPreferenceChangeListener { _, isChecked ->
            if (isChecked as Boolean) {
                prefs.theme = getString(R.string.dark_theme)
            } else {
                prefs.theme = getString(R.string.light_theme)
            }
            true
        }
    }
}