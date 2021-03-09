package ru.school21.eleonard.authorization.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.mainWindow.MainActivityViewPagerManager
import ru.school21.eleonard.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : Fragment() {
	private lateinit var binding: FragmentLoginBinding

	override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
		binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		configureViews()
		initListeners()
	}

	private fun configureViews() {

	}

	private fun initListeners() {
		binding.btnLogin.setOnClickListener {
			(requireActivity() as? MainActivityViewPagerManager)?.configureActivityForAuthorizedUser()
		}
	}
}