package ru.school21.eleonard.menu.contacts.data

import ru.school21.eleonard.data.db.realmModels.ContactRealmModel

interface ContactsRepository {
	fun getContacts(isFavorite: Boolean): List<ContactRealmModel>
}