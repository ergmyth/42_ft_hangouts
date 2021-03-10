package ru.school21.eleonard.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.school21.eleonard.data.db.DbUtils
import ru.school21.eleonard.menu.contacts.data.ContactsRepository
import ru.school21.eleonard.menu.contacts.data.ContactsRepositoryImpl
import ru.school21.eleonard.menu.contacts.domain.GetContactListUseCase
import ru.school21.eleonard.menu.contacts.domain.GetContactListUseCaseImpl

@Module
@InstallIn(ApplicationComponent::class)
object ContactsModule {
	@Provides
	fun provideContactsRepository(
		dbUtils: DbUtils,
	): ContactsRepository {
		return ContactsRepositoryImpl(dbUtils)
	}

	@Provides
	fun provideGetContactListUseCase(contactsRepository: ContactsRepository): GetContactListUseCase {
		return GetContactListUseCaseImpl(contactsRepository)
	}
}