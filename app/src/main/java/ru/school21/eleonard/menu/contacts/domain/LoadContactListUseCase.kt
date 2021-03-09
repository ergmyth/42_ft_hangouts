package ru.school21.eleonard.menu.contacts.domain

import kotlinx.coroutines.flow.Flow
import ru.school21.eleonard.helpers.requests.Event
import ru.school21.eleonard.menu.contacts.models.Contact

interface LoadContactListUseCase {
	fun invoke(): Flow<Event<List<Contact>>>
}