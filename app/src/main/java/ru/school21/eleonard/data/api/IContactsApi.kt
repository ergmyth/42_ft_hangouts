package ru.school21.eleonard.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.school21.eleonard.helpers.requests.ResponseWrapper
import ru.school21.eleonard.menu.contacts.models.Contact

interface IContactsApi {
	@GET("api/v0/get_contacts")
	suspend fun getContacts(): Response<ResponseWrapper<List<Contact>>>
}