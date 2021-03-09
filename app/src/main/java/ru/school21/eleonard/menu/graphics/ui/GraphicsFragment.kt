package ru.school21.eleonard.menu.graphics.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.FragmentGraphicsBinding
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.helpers.toolbar.ToolbarConfigurator
import ru.school21.eleonard.mainWindow.ToolbarManager

@AndroidEntryPoint
class GraphicsFragment : Fragment() {
	private lateinit var binding: FragmentGraphicsBinding

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		ToolbarConfigurator().configureToolbar(menu,
			inflater,
			activity as? ToolbarManager,
			ToolbarStates.STATE_GRAPHICS_MAIN,
			resources.getString(R.string.bnv_proteins))
	}

	override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
		binding = FragmentGraphicsBinding.inflate(layoutInflater, container, false)
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