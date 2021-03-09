package ru.school21.eleonard.menu.contacts.data

import kotlinx.coroutines.flow.Flow
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.helpers.requests.Event
import ru.school21.eleonard.menu.contacts.models.Contact

interface ContactsRepository {
	fun loadContactList(): Flow<Event<List<Contact>>>
	fun getContacts(isFavorite: Boolean): List<ContactRealmModel>
}