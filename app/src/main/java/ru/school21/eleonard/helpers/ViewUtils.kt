package ru.school21.eleonard.helpers

import android.view.View

fun View.show() {
	this.visibility = View.VISIBLE
}

fun View.hide() {
	this.visibility = View.GONE
}