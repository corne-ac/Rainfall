package com.corne.rainfall.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentSettingsBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import java.util.Locale


class SettingsFragment :
    BaseStateFragment<FragmentSettingsBinding, SettingsState, SettingsViewModel>() {


    override val viewModel: SettingsViewModel by hiltMainNavGraphViewModels()

    override fun updateState(state: SettingsState) {

    }

    override suspend fun addContentToView() {
        runDarkUpdate()
        runLanguageUpdate()
    }

    private suspend fun runLanguageUpdate() {
        val language = viewModel.getLanguage()
        val default = Locale.getDefault()

        /*  binding.languageDropdown.adapter = LanguageDropdownAdapter(
              requireContext(),
              R.layout.language_dropdown_item,
              listOf(default, "AA", "AB","AC" )
          )*/

    }


    private suspend fun runDarkUpdate() {
        val darkModeEnabled = viewModel.isDarkModeEnabled()
        val buttonTextRes = if (darkModeEnabled) R.string.set_light_mode else R.string.set_dark_mode

        binding.updateDarkBtn.setText(buttonTextRes)
        binding.updateDarkBtn.setOnClickListener {
            viewModel.setDarkMode(!darkModeEnabled)
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false)
}
