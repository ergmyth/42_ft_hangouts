package ru.school21.eleonard.menu.other.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.school21.eleonard.mainWindow.MainActivityViewPagerManager
import ru.school21.eleonard.databinding.FragmentDialogLogoutBinding

internal class LogoutDialogFragment : DialogFragment() {
	lateinit var binding: FragmentDialogLogoutBinding

	override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
		binding = FragmentDialogLogoutBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initListeners()
	}

	private fun initListeners() {
		binding.tvAction.setOnClickListener {
			resetApplicationData()
			dismiss()
		}
		binding.tvCancel.setOnClickListener {
			dismiss()
		}
	}

	//todo purgeBase, clear sharedPref etc.
	private fun resetApplicationData() {
		(requireActivity() as? MainActivityViewPagerManager)?.configureActivityForNonAuthorizedUser()
	}
}