package ru.school21.eleonard.security.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.school21.eleonard.data.db.DbUtils
import ru.school21.eleonard.security.data.SecurityRepository
import ru.school21.eleonard.security.data.SecurityRepositoryImpl
import ru.school21.eleonard.security.domain.ResetDataUseCase
import ru.school21.eleonard.security.domain.ResetDataUseCaseImpl

@Module
@InstallIn(ApplicationComponent::class)
object SecurityModule {
	//todo binds?
	@Provides
	fun provideResetDataUseCase(securityRepository: SecurityRepository): ResetDataUseCase {
		return ResetDataUseCaseImpl(securityRepository)
	}

	@Provides
	fun provideSecurityRepository(dbUtils: DbUtils): SecurityRepository {
		return SecurityRepositoryImpl(dbUtils)
	}
}