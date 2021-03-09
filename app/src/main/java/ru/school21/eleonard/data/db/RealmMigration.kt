package ru.school21.eleonard.data.db

import io.realm.DynamicRealm
import io.realm.RealmMigration
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.data.db.realmModels.MessageRealmModel

class RealmMigration : RealmMigration {
	override fun migrate(realm: DynamicRealm, _oldVersion: Long, newVersion: Long) {
		var oldVersion = _oldVersion
		val schema = realm.schema

		if (oldVersion++ == 0L) {
			if (schema["ContactModel"] == null)
				ContactRealmModel.createContactModel(schema)
			if (schema["MessageModel"] == null)
				MessageRealmModel.createMessageModel(schema)
		}
		if (oldVersion++ == 1L) {

		}
	}
}