package ru.school21.eleonard.menu.contacts.data

import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.menu.contacts.domain.ContactsRepository
import ru.school21.eleonard.menu.contacts.domain.GetContactListUseCase
import javax.inject.Inject

class GetContactListUseCaseImpl @Inject constructor(
	private val contactsRepository: ContactsRepository
) : GetContactListUseCase {
	override fun invoke(isFavorite: Boolean): List<ContactRealmModel> = contactsRepository.getContacts(isFavorite)
}