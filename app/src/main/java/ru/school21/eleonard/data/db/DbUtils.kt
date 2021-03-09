package ru.school21.eleonard.data.db

import io.realm.Realm
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import javax.inject.Inject

class DbUtils @Inject constructor(var realm: Realm) {

	fun getAllContacts(): List<ContactRealmModel> {
		return realm
			.where(ContactRealmModel::class.java)
			.findAll() ?: listOf()
	}

	fun getFavoriteContacts(): List<ContactRealmModel> {
		return realm
			.where(ContactRealmModel::class.java)
			.equalTo("isFavorite", true)
			.findAll() ?: listOf()
	}
}