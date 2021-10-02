package ru.school21.eleonard.helpers.utils

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import es.dmoral.toasty.Toasty
import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.R
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.*

object UtilsUI {
	fun makeCoolToast(text: String, type: ToastStates) {
		when (type) {
			ToastStates.NORMAL -> Toasty.normal(BaseApp.getInstance(), text, Toast.LENGTH_SHORT).show()
			ToastStates.ERROR -> Toasty.error(BaseApp.getInstance(), text, Toast.LENGTH_SHORT, true).show()
			ToastStates.SUCCESS -> Toasty.success(BaseApp.getInstance(), text, Toast.LENGTH_SHORT, true).show()
			ToastStates.WARNING -> Toasty.warning(BaseApp.getInstance(), text, Toast.LENGTH_SHORT, true).show()
		}
	}

	fun convertTimestampToDate(timestamp: Long): String {
		val calendar: Calendar = Calendar.getInstance()
		calendar.timeInMillis = timestamp
		val prefix = "0"
		val day = if (calendar.get(Calendar.DAY_OF_MONTH) < 10) prefix + calendar.get(Calendar.DAY_OF_MONTH) else calendar.get(Calendar.DAY_OF_MONTH)
		val month = if (calendar.get(Calendar.MONTH) + 1 < 10) prefix + (calendar.get(Calendar.MONTH) + 1) else (calendar.get(Calendar.MONTH) + 1)
		val hour = if (calendar.get(Calendar.HOUR_OF_DAY) < 10) prefix + calendar.get(Calendar.HOUR_OF_DAY) else calendar.get(Calendar.HOUR_OF_DAY)
		val minute = if (calendar.get(Calendar.MINUTE) < 10) prefix + calendar.get(Calendar.MINUTE) else calendar.get(Calendar.MINUTE)
		return "$day.$month.${calendar.get(Calendar.YEAR)}, $hour:$minute"
	}

	fun loadAvatar(ivProfile: ShapeableImageView, avatarPath: String) {
		val path = getPath(BaseApp.getInstance(), Uri.parse(avatarPath))
		if (path != null) {
			val file = File(path)
			val inputStream: InputStream = FileInputStream(file)
			val original = BitmapFactory.decodeStream(inputStream)
			val avatar = BitmapDrawable(BaseApp.getInstance().resources, original)
			Glide.with(ivProfile.context)
				.load(avatar)
				.error(R.drawable.ic_avatar_default)
				.into(ivProfile)
		}
	}
}