package ru.school21.eleonard.menu.other.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.databinding.FragmentDialogNightModeBinding
import ru.school21.eleonard.Constants
import ru.school21.eleonard.menu.other.viewModels.SettingsViewModel

@AndroidEntryPoint
internal class NightModeDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogNightModeBinding
    private var currentTheme: Int = 0
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDialogNightModeBinding.inflate(inflater, container, false)
        initViewModels()
        return binding.root
    }

    private fun initViewModels() {
        settingsViewModel = ViewModelProvider(requireParentFragment()).get(SettingsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        initListeners()
    }

    private fun configureViews() {
        when (settingsViewModel.currentTheme.value) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.rbSystem.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> binding.rbTurnOff.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> binding.rbTurnOn.isChecked = true
        }
    }

    private fun initListeners() {
        binding.rbSystem.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
        binding.rbTurnOff.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        binding.rbTurnOn.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun setNightMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        BaseApp.getSharedPref().edit().putInt(Constants.SP_CHOSEN_THEME, mode).apply()
        settingsViewModel.currentTheme.value = mode
    }
}