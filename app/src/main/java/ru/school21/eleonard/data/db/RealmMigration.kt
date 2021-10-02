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
			if (schema[ContactRealmModel.MODEL_NAME] == null)
				ContactRealmModel.createModel(schema)
			if (schema[MessageRealmModel.MODEL_NAME] == null)
				MessageRealmModel.createModel(schema)
		}
		if (oldVersion++ == 1L) {

		}
	}
}