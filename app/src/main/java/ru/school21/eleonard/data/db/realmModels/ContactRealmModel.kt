package ru.school21.eleonard.data.db.realmModels

import io.realm.*
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.*

open class ContactRealmModel : RealmObject() {
	@PrimaryKey
	var contactId: String = UUID.randomUUID().toString()

	var name: String = ""
	var number: String = ""

	var isFavorite: Boolean = false
	var company: String = ""
	var address: String = ""
	var position: String = ""
	var avatar: String = ""
	var chat: RealmList<MessageRealmModel> = RealmList()

	companion object {
		const val MODEL_NAME = "Contact"
		fun createModel(schema: RealmSchema) {
			val contactModel = schema.create(MODEL_NAME)
			contactModel?.apply {
				addField("contactId", String::class.java, FieldAttribute.PRIMARY_KEY)
				addField("name", String::class.java)
				addField("number", String::class.java)
				addField("address", String::class.java)
				addField("position", String::class.java)
				addField("company", String::class.java)
				addField("avatar", String::class.java)
				addField("isFavorite", Boolean::class.java)
				addRealmListField("chat", schema[MessageRealmModel.MODEL_NAME]!!)
			}
		}
	}
}