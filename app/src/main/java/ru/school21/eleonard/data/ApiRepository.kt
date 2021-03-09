package ru.school21.eleonard.data

import retrofit2.Response
import ru.school21.eleonard.data.api.IContactsApi
import ru.school21.eleonard.helpers.requests.ResponseWrapper
import ru.school21.eleonard.menu.contacts.models.Contact
import javax.inject.Inject

class ApiRepository @Inject constructor(
	val contactsApi: IContactsApi,
) {
	suspend fun getContacts(): Response<ResponseWrapper<List<Contact>>> {
		return contactsApi.getContacts()
	}
}