package ergmyth.almagest.ft_hangouts_eleonard.models

import android.graphics.drawable.Drawable

data class Contact(
    var name: String,
    var company: String?,
    var number: String,
    var address: String?,
    var position: String?,
    var avatar: Drawable
)