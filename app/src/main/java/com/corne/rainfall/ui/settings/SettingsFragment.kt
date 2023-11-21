package com.corne.rainfall.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentSettingsBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import java.util.Locale

/**
 * Fragment for the Settings screen.
 *
 * This fragment is responsible for displaying the settings and interacting with the SettingsViewModel.
 * It extends the BaseStateFragment with a state of type SettingsState and a ViewModel of type SettingsViewModel.
 */
class SettingsFragment :
    BaseStateFragment<FragmentSettingsBinding, SettingsState, SettingsViewModel>() {

    /**
     * The ViewModel for this fragment.
     * It is obtained using the hiltMainNavGraphViewModels extension function.
     */
    override val viewModel: SettingsViewModel by hiltMainNavGraphViewModels()

    /**
     * Updates the state of the fragment.
     *
     * This function is called when the state of the ViewModel changes.
     * It should be used to update the UI based on the new state.
     *
     * @param state The new state.
     */
    override fun updateState(state: SettingsState) {

    }

    /**
     * Adds content to the view.
     *
     * This function is called to add content to the view.
     * It is a suspend function, so it can perform long-running operations.
     */
    override suspend fun addContentToView() {
        runDarkUpdate()
        runLanguageUpdate()
    }

    /**
     * Updates the language setting.
     *
     * This function is called to update the language setting.
     * It is a suspend function, so it can perform long-running operations.
     */
    private suspend fun runLanguageUpdate() {
        val language = viewModel.getLanguage()
        val default = Locale.getDefault()

        /*  binding.languageDropdown.adapter = LanguageDropdownAdapter(
              requireContext(),
              R.layout.language_dropdown_item,
              listOf(default, "AA", "AB","AC" )
          )*/

    }

    /**
     * Updates the dark mode setting.
     *
     * This function is called to update the dark mode setting.
     * It is a suspend function, so it can perform long-running operations.
     */
    private suspend fun runDarkUpdate() {
        val darkModeEnabled = viewModel.isDarkModeEnabled()
        val buttonTextRes = if (darkModeEnabled) R.string.set_light_mode else R.string.set_dark_mode

        binding.updateDarkBtn.setText(buttonTextRes)
        binding.updateDarkBtn.setOnClickListener {
            viewModel.setDarkMode(!darkModeEnabled)
        }
    }

    /**
     * Creates the view binding for this fragment.
     *
     * This function is called to create the view binding for this fragment.
     * It inflates the layout for this fragment.
     *
     * @param inflater The LayoutInflater used to inflate the layout.
     * @param container The ViewGroup that the layout is inflated into.
     * @return The created view binding.
     */
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false)
}
