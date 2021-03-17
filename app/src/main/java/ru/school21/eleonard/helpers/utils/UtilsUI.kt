package ru.school21.eleonard.helpers.utils

import android.widget.Toast
import es.dmoral.toasty.Toasty
import ru.school21.eleonard.BaseApp

object UtilsUI {
	fun makeCoolToast(text: String, type: ToastStates) {
		when (type) {
			ToastStates.NORMAL -> Toasty.normal(BaseApp.getInstance(), text, Toast.LENGTH_SHORT).show()
			ToastStates.ERROR -> Toasty.error(BaseApp.getInstance(), text, Toast.LENGTH_SHORT, true).show()
			ToastStates.SUCCESS -> Toasty.success(BaseApp.getInstance(), text, Toast.LENGTH_SHORT, true).show()
			ToastStates.WARNING -> Toasty.warning(BaseApp.getInstance(), text, Toast.LENGTH_SHORT, true).show()
		}
	}
}