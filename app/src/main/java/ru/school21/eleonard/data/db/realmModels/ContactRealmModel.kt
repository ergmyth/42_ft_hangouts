package ru.school21.eleonard.data.db.realmModels

import io.realm.FieldAttribute
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmSchema
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.*

open class ContactRealmModel : RealmObject() {
	@PrimaryKey
	var contactId: String = UUID.randomUUID().toString()

	@Required
	var name: String = ""
	@Required
	var number: String = ""

	var isFavorite: Boolean = false
	var company: String = ""
	var address: String = ""
	var position: String = ""
	var avatar: String = ""

	companion object {
		fun createContactModel(schema: RealmSchema) {
			val contactModel = schema.create("ContactModel")
			contactModel?.apply {
				addField("contactId", String::class.java, FieldAttribute.PRIMARY_KEY)
				addField("name", String::class.java, FieldAttribute.REQUIRED)
				addField("number", String::class.java, FieldAttribute.REQUIRED)
				addField("address", String::class.java)
				addField("position", String::class.java)
				addField("company", String::class.java)
				addField("avatar", String::class.java)
				addField("isFavorite", Boolean::class.java)
			}
		}
	}
}