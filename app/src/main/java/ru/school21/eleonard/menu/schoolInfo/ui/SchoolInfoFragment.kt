package ru.school21.eleonard.menu.schoolInfo.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.FragmentSchoolInfoBinding
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.helpers.toolbar.ToolbarConfigurator
import ru.school21.eleonard.mainWindow.ToolbarManager

@AndroidEntryPoint
class SchoolInfoFragment : Fragment(){
	private lateinit var binding: FragmentSchoolInfoBinding

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		ToolbarConfigurator().configureToolbar(menu,
			inflater,
			activity as? ToolbarManager,
			ToolbarStates.STATE_SCHOOL_MAIN,
			resources.getString(R.string.bnv_companion))
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSchoolInfoBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		configureViews()
		initListeners()
	}

	private fun configureViews() {
		setHasOptionsMenu(true)
	}

	private fun initListeners() {

	}
}