package ru.school21.eleonard.menu.contacts.data

import javax.inject.Inject
import ru.school21.eleonard.data.db.DbUtils
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.menu.contacts.domain.ContactsRepository

class ContactsRepositoryImpl @Inject constructor(
	private val dbUtils: DbUtils,
) : ContactsRepository {

	override fun getContacts(isFavorite: Boolean): List<ContactRealmModel> {
		return if (isFavorite)
			dbUtils.getFavoriteContacts()
		else
			dbUtils.getAllContacts()
	}

	override fun deleteContact(contactId: String) {
		dbUtils.deleteContact(contactId)
	}
}