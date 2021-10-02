package ru.school21.eleonard.menu.other.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.FragmentOtherBinding
import ru.school21.eleonard.helpers.base.BaseFragment
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.menu.other.viewModels.SettingsViewModel
import ru.school21.eleonard.security.ui.PinFragment

@AndroidEntryPoint
class OtherFragment : BaseFragment(R.layout.fragment_other) {
	override val binding by viewBinding(FragmentOtherBinding::bind)
	override val hasOptionMenu: Boolean = true
	override val toolbarState = ToolbarStates.STATE_OTHER_MAIN

	private lateinit var settingsViewModel: SettingsViewModel

	private fun initViewModels() {
		settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initViewModels()
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
		configureSelectedTheme()
	}

	private fun initListeners() {
		binding.clTheme.setOnClickListener {
			NightModeDialogFragment().show(childFragmentManager, "")
		}

		binding.tvSecurity.setOnClickListener {
			//todo открывать другой фрагмент где будет пин и прочее
			childFragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
				.add(binding.root.id, PinFragment())
				.addToBackStack(null)
				.commit()
		}
	}
}