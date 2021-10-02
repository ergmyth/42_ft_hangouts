package ru.school21.eleonard.helpers.utils

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.school21.eleonard.BaseApp

object UtilsPermissions {

	fun hasPermissions(fragment: Fragment, permissions: Array<String>, requestCode: Int): Boolean {
		val notGrantedPermissions = mutableListOf<String>()
		for (permission in permissions) {
			if (!hasPermission(permission))
				notGrantedPermissions.add(permission)
		}
		return if (notGrantedPermissions.isEmpty()) {
			true
		} else {
			fragment.requestPermissions(
				notGrantedPermissions.toTypedArray(),
				requestCode
			)
			false
		}
	}

	private fun hasPermission(permissionName: String): Boolean {
		return ContextCompat.checkSelfPermission(BaseApp.getInstance(), permissionName) == PackageManager.PERMISSION_GRANTED
	}
}