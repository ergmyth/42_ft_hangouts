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

	@Index
	var senderContactId: Int = 0
	@Index
	var receiverContactId: Int = 0
	@Required
	var text: String = ""

	var date: Long = 0L
	var status: Boolean = false

	companion object {
		fun createMessageModel(schema: RealmSchema) {
			val messageModel = schema.create("MessageModel")
			messageModel?.apply {
				addField("messageId", String::class.java, FieldAttribute.PRIMARY_KEY)
				addField("senderContactId", Int::class.java, FieldAttribute.INDEXED)
				addField("receiverContactId", Int::class.java, FieldAttribute.INDEXED)
				addField("text", String::class.java, FieldAttribute.REQUIRED)
				addField("date", Long::class.java)
				addField("status", Boolean::class.java)
			}
		}
	}
}