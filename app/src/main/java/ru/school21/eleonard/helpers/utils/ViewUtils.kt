package ru.school21.eleonard.helpers.utils

import android.view.View
import android.widget.TextView
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

fun View.show() {
	this.visibility = View.VISIBLE
}

fun View.hide() {
	this.visibility = View.GONE
}

fun TextView.setPhoneFormat() {
	val mask = "+7 (___) ___-__-__"
	val slots = UnderscoreDigitSlotsParser().parseSlots(mask)
	val maskImpl = MaskImpl.createTerminated(slots)
	val watcher = MaskFormatWatcher(maskImpl)
	watcher.installOn(this)
}
