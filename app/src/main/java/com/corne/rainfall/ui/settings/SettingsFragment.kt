package com.corne.rainfall.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.corne.rainfall.R
import com.corne.rainfall.databinding.FragmentSettingsBinding
import com.corne.rainfall.ui.base.state.BaseStateFragment
import com.corne.rainfall.ui.hiltMainNavGraphViewModels
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

/**
 * Fragment for the Settings screen.
 *
 * This fragment is responsible for displaying the settings and interacting with the SettingsViewModel.
 * It extends the BaseStateFragment with a state of type SettingsState and a ViewModel of type SettingsViewModel.
 */
class SettingsFragment :
    BaseStateFragment<FragmentSettingsBinding, ISettingsState, SettingsViewModel>() {

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
    override fun updateState(state: ISettingsState) {
        if (state.loggedIn) {
            binding.onlineBackupToggle.isChecked = true
            binding.cloudBackupLayout.visibility = View.VISIBLE
            binding.email.text = FirebaseAuth.getInstance().currentUser?.email

            binding.LoginRegisterBtn.text = getString(R.string.logout)
            binding.LoginRegisterBtn.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                viewModel.checkLoggedIn()
            }
        } else {
            binding.email.text = getString(R.string.not_loggend_in)
            binding.LoginRegisterBtn.text = getString(R.string.login_create_account)
            binding.LoginRegisterBtn.setOnClickListener  {
                findNavController().navigate(R.id.action_navigation_settings_to_loginFragment)
            }
        }
    }

    /**
     * Adds content to the view.
     *
     * This function is called to add content to the view.
     * It is a suspend function, so it can perform long-running operations.
     */
    override suspend fun addContentToView() {
        runDarkUpdate()
        runOfflineUpdate()
        runLanguageUpdate()
        runBackupUpdate()

        viewModel.checkLoggedIn()

        binding.exportCloudBtn.setOnClickListener {
            viewModel.exportDataCloud()

        }
        binding.importLocalBtn.setOnClickListener {
            // Here we will import the database file
            viewModel.importDataLocal(requireContext())
        }

        binding.exportLocalBtn.setOnClickListener {
            // Here we will export the database file
            viewModel.exportDataLocal(requireContext())
        }

        binding.LoginRegisterBtn.setOnClickListener  {
            findNavController().navigate(R.id.action_navigation_settings_to_loginFragment)
        }
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
        /*   val darkModeEnabled = viewModel.isDarkModeEnabled()
           if (darkModeEnabled) binding.nightToggle.isChecked = true
           binding.nightToggle.setOnClickListener {
               if (binding.nightToggle.isChecked) viewModel.setDarkMode(true)
               else viewModel.setDarkMode(false)
           }
   */
        val darkModeEnabled = viewModel.isDarkModeEnabled()
        val nightToggle = binding.nightToggle
        nightToggle.isChecked = darkModeEnabled
        nightToggle.setOnClickListener {
            viewModel.setDarkMode(nightToggle.isChecked)
        }
    }

    /**
     * Updates the offline mode setting.
     *
     * This function is called to update the offline mode setting.
     * It is a suspend function, so it can perform long-running operations.
     */
    private suspend fun runOfflineUpdate() {
        val onlineEnabled = viewModel.isOfflineModeEnabled()
        val onlineToggle = binding.onlineToggle
        onlineToggle.isChecked = onlineEnabled
        onlineToggle.setOnClickListener {
            viewModel.setOfflineMode(onlineToggle.isChecked)
//            viewModel.setOfflineMode(onlineToggle.isChecked)
        }
    }

    /**
     * Updates the Backup setting.
     *
     * This function is called to update the backup setting.
     * It is a suspend function, so it can perform long-running operations.
     */
    private suspend fun runBackupUpdate() {
        //Guess have to add some stuff to view-model to check
        //backup last date local and online, also check account

        val usingCloudBackup = false
        if (!usingCloudBackup) binding.cloudBackupLayout.visibility = View.GONE

        binding.onlineBackupToggle.setOnClickListener {
            if (binding.onlineBackupToggle.isChecked) binding.cloudBackupLayout.visibility =
                View.VISIBLE
            else binding.cloudBackupLayout.visibility = View.GONE
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
