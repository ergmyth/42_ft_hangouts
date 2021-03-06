package ru.school21.eleonard.data.db

import android.util.Log
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

	fun deleteContact(contactId: String) {
		if (!realm.isInTransaction)
			realm.beginTransaction()
		realm.where(ContactRealmModel::class.java).equalTo("contactId", contactId).findAll().deleteAllFromRealm()
		realm.commitTransaction()
	}


	fun purgeBase() {
		try {
			GlobalScope.launch(Dispatchers.Main) {
				val localRealmInstance = Realm.getDefaultInstance()
				localRealmInstance.executeTransaction {
					localRealmInstance.deleteAll()
				}
			}
		} catch (e: Exception) {
			Log.d("PurgeBaseException", e.message ?: "Error message is empty")
		}
	}
}