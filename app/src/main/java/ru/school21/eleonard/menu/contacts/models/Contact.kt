package ru.school21.eleonard.menu.contacts.models

import android.graphics.drawable.Drawable

data class Contact(
    var name: String,
    var company: String?,
    var number: String,
    var address: String?,
    var position: String?,
    var avatar: Drawable
)