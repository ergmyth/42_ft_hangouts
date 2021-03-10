package ru.school21.eleonard.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.school21.eleonard.helpers.requests.data.ErrorHandlerRepository
import ru.school21.eleonard.helpers.requests.data.ErrorHandlerRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ErrorHandlerModule {
	@Singleton
	@Provides
	fun provideErrorHandlerRepository(): ErrorHandlerRepository {
		return ErrorHandlerRepositoryImpl()
	}
}