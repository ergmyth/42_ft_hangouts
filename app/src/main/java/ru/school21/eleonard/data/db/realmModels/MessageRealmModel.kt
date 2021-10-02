package ru.school21.eleonard.data.db.realmModels

import io.realm.*
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.*

open class MessageRealmModel : RealmObject() {
	@PrimaryKey
	var messageId: String = UUID.randomUUID().toString()

	@Required
	var text: String = ""
	var isMyMessage: Boolean = true

	var date: Long = 0L
	var status: Boolean = false

	companion object {
		const val MODEL_NAME = "Message"
		fun createModel(schema: RealmSchema) {
			val messageModel = schema.create(MODEL_NAME)
			messageModel?.apply {
				addField("messageId", String::class.java, FieldAttribute.PRIMARY_KEY)
				addField("isMyMessage", Boolean::class.java)
				addField("text", String::class.java, FieldAttribute.REQUIRED)
				addField("date", Long::class.java)
				addField("status", Boolean::class.java)
			}
		}
	}
}