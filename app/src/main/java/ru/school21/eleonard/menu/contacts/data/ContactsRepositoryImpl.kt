package ru.school21.eleonard.menu.contacts.data

import kotlinx.coroutines.flow.Flow
import ru.school21.eleonard.helpers.requests.Event
import ru.school21.eleonard.menu.contacts.models.Contact
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.school21.eleonard.data.ApiRepository
import ru.school21.eleonard.data.db.DbUtils
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.helpers.requests.ErrorHandlerRepository

class ContactsRepositoryImpl @Inject constructor(
	private val apiRepository: ApiRepository,
	private val errorHandlerRepository: ErrorHandlerRepository,
	private val dbUtils: DbUtils,
) : ContactsRepository {
	override fun loadContactList(): Flow<Event<List<Contact>>> = flow {
		emit(Event.loading())

		val apiResponse = apiRepository.getContacts()

		if (apiResponse.isSuccessful && apiResponse.body()?.data != null)
			emit(Event.success(apiResponse.body()?.data))
		//todo определить в каком поле приходит ошибка body().error или errorBody
		/*
		else
		emit(Event.error(errorHandlerRepository.handleErrorMessage(apiResponse.body()?.error.toString())))*/
	}.catch { e ->
		emit(Event.error("Ошибка при отправке запроса на получение списка авторизованных договоров."))
		e.printStackTrace()
	}

	override fun getContacts(isFavorite: Boolean): List<ContactRealmModel> {
		return if (isFavorite)
			dbUtils.getFavoriteContacts()
		else
			dbUtils.getAllContacts()
	}
}