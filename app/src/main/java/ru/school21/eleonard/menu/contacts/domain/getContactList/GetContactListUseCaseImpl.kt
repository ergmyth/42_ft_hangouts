package ru.school21.eleonard.menu.contacts.domain.getContactList

import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.menu.contacts.data.ContactsRepository
import javax.inject.Inject

class GetContactListUseCaseImpl @Inject constructor(
	private val contactsRepository: ContactsRepository
) : GetContactListUseCase {
	override fun invoke(isFavorite: Boolean): List<ContactRealmModel> = contactsRepository.getContacts(isFavorite)
}