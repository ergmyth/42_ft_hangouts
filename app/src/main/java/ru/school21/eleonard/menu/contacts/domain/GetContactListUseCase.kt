package ru.school21.eleonard.menu.contacts.domain

import ru.school21.eleonard.data.db.realmModels.ContactRealmModel

interface GetContactListUseCase {
	fun invoke(isFavorite: Boolean): List<ContactRealmModel>
}