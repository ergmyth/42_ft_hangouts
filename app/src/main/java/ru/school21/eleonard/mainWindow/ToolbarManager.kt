package ru.school21.eleonard.mainWindow

import ru.school21.eleonard.helpers.toolbar.ToolbarStates

interface ToolbarManager {
	fun configureToolbar(title: String, toolbarState: ToolbarStates)
}
