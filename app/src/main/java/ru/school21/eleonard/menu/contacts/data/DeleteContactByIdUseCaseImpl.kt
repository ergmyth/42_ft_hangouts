package ru.school21.eleonard.menu.contacts.data

import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.menu.contacts.domain.ContactsRepository
import ru.school21.eleonard.menu.contacts.domain.DeleteContactByIdUseCase
import ru.school21.eleonard.menu.contacts.domain.GetContactListUseCase
import javax.inject.Inject

class DeleteContactByIdUseCaseImpl @Inject constructor(
	private val contactsRepository: ContactsRepository
) : DeleteContactByIdUseCase {
	override fun invoke(contact: ContactRealmModel)= contactsRepository.deleteContact(contact.contactId)
}