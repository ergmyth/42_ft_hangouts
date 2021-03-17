package ru.school21.eleonard.mainWindow

interface MainActivityViewPagerManager {
	fun openCameras()
	fun openMap()
	fun openMultiplayer()
	fun openOther()

	fun configureActivityForNonAuthorizedUser()
	fun configureActivityForAuthorizedUser()
}
