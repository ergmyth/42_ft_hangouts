package ru.school21.eleonard.helpers.toolbar

import android.view.Menu
import android.view.MenuInflater
import ru.school21.eleonard.R
import ru.school21.eleonard.helpers.toolbar.ToolbarStates
import ru.school21.eleonard.mainWindow.ToolbarManager

class ToolbarConfigurator {
	fun configureToolbar(menu: Menu, inflater: MenuInflater, toolbarManager: ToolbarManager?, toolbarState: ToolbarStates, title: String) {
		menu.clear()
		inflater.inflate(R.menu.main_toolbar_menu, menu)
		menu.findItem(R.id.menuItemSearch).isVisible = toolbarState.isSearchVisible
		menu.findItem(R.id.menuItemFilter).isVisible = toolbarState.isFilterVisible
		menu.findItem(R.id.menuItemSort).isVisible = toolbarState.isSortVisible
		toolbarManager?.configureToolbar(title = title, toolbarState = toolbarState)
	}
}