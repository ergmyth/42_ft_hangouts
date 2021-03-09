package ru.school21.eleonard.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.school21.eleonard.data.ApiRepository
import ru.school21.eleonard.data.db.DbUtils
import ru.school21.eleonard.helpers.requests.ErrorHandlerRepository
import ru.school21.eleonard.menu.contacts.domain.LoadContactListUseCase
import ru.school21.eleonard.menu.contacts.domain.LoadContactListUseCaseImpl
import ru.school21.eleonard.menu.contacts.data.ContactsRepository
import ru.school21.eleonard.menu.contacts.data.ContactsRepositoryImpl
import ru.school21.eleonard.menu.contacts.domain.getContactList.GetContactListUseCase
import ru.school21.eleonard.menu.contacts.domain.getContactList.GetContactListUseCaseImpl

@Module
@InstallIn(ApplicationComponent::class)
object ContactsModule {
	@Provides
	fun provideContactsRepository(
		apiRepository: ApiRepository,
		errorHandlerRepository: ErrorHandlerRepository,
		dbUtils: DbUtils,
	): ContactsRepository {
		return ContactsRepositoryImpl(apiRepository, errorHandlerRepository, dbUtils)
	}

	@Provides
	fun provideContactListLoadingUseCase(contactsRepository: ContactsRepository): LoadContactListUseCase {
		return LoadContactListUseCaseImpl(contactsRepository)
	}

	@Provides
	fun provideGetContactListUseCase(contactsRepository: ContactsRepository): GetContactListUseCase {
		return GetContactListUseCaseImpl(contactsRepository)
	}
}