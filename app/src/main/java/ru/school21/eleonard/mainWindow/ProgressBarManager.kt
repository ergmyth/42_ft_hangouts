package ru.school21.eleonard.mainWindow

interface ProgressBarManager {
	fun startLoading(text: String?)
	fun finishLoading()
}
