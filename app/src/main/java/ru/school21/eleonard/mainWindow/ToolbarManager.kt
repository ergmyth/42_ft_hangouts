package ru.school21.eleonard.mainWindow

import ru.school21.eleonard.helpers.toolbar.ToolbarStates

interface ToolbarManager {
	fun configureToolbar(toolbarState: ToolbarStates, customTitle: String)
}
