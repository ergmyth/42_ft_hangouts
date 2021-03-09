package ru.school21.eleonard.menu.contacts.domain

import kotlinx.coroutines.flow.Flow
import ru.school21.eleonard.helpers.requests.Event
import ru.school21.eleonard.menu.contacts.models.Contact
import ru.school21.eleonard.menu.contacts.data.ContactsRepository
import javax.inject.Inject

class LoadContactListUseCaseImpl @Inject constructor(
	private val conractsRepository: ContactsRepository
) : LoadContactListUseCase {
	override fun invoke(): Flow<Event<List<Contact>>> = conractsRepository.loadContactList()

}