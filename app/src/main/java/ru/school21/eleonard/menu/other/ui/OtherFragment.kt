package ru.school21.eleonard.menu.other.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.FragmentOtherBinding
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.helpers.toolbar.ToolbarConfigurator
import ru.school21.eleonard.mainWindow.ProgressBarManager
import ru.school21.eleonard.mainWindow.ToolbarManager
import ru.school21.eleonard.menu.other.viewModels.SettingsViewModel

@AndroidEntryPoint
class OtherFragment : Fragment() {
	private lateinit var binding: FragmentOtherBinding
	private lateinit var settingsViewModel: SettingsViewModel

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		ToolbarConfigurator().configureToolbar(
			menu,
			inflater,
			requireActivity() as? ToolbarManager,
			ToolbarStates.STATE_OTHER_MAIN,
			resources.getString(R.string.bnv_other)
		)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentOtherBinding.inflate(layoutInflater, container, false)
		initViewModels()
		return binding.root
	}

	private fun initViewModels() {
		settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		configureViews()
		initListeners()
		initObservers()
	}

	private fun initObservers() {
		observeOnCurrentTheme()
	}

	private fun observeOnCurrentTheme() {
		settingsViewModel.currentTheme.observe(viewLifecycleOwner, Observer {
			configureSelectedTheme()
		})
	}

	private fun configureSelectedTheme() {
		when (settingsViewModel.currentTheme.value) {
			AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.tvSelectedTheme.text = resources.getString(R.string.night_mode_system)
			AppCompatDelegate.MODE_NIGHT_NO -> binding.tvSelectedTheme.text = resources.getString(R.string.night_mode_never)
			AppCompatDelegate.MODE_NIGHT_YES -> binding.tvSelectedTheme.text = resources.getString(R.string.night_mode_always)
		}
	}

	private fun configureViews() {
		setHasOptionsMenu(true)
		configureSelectedTheme()
	}

	private fun initListeners() {
		binding.btnLogout.setOnClickListener {
			LogoutDialogFragment().show(parentFragmentManager, "")
		}

		binding.clTheme.setOnClickListener {
			NightModeDialogFragment().show(childFragmentManager, "")
		}
	}
}