package ru.school21.eleonard.menu.contacts.data

import javax.inject.Inject
import ru.school21.eleonard.data.db.DbUtils
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel

class ContactsRepositoryImpl @Inject constructor(
	private val dbUtils: DbUtils,
) : ContactsRepository {

	override fun getContacts(isFavorite: Boolean): List<ContactRealmModel> {
		return if (isFavorite)
			dbUtils.getFavoriteContacts()
		else
			dbUtils.getAllContacts()
	}
}