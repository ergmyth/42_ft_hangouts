package ru.school21.eleonard.menu.graphics.ui

import android.os.Bundle
import android.view.*
import dagger.hilt.android.AndroidEntryPoint
import ru.school21.eleonard.R
import ru.school21.eleonard.databinding.FragmentGraphicsBinding
import ru.school21.eleonard.helpers.base.BaseFragment
import ru.school21.eleonard.helpers.toolbar.ToolbarStates

@AndroidEntryPoint
class GraphicsFragment : BaseFragment(R.layout.fragment_graphics) {
	override val binding by viewBinding(FragmentGraphicsBinding::bind)
	override val hasOptionMenu: Boolean = true
	override val toolbarState = ToolbarStates.STATE_GRAPHICS_MAIN

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