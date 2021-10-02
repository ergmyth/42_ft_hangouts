package ru.school21.eleonard.menu.schoolInfo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.school21.eleonard.data.network.ApiRepository
import ru.school21.eleonard.data.network.repositories.CompositeDisposableRepository
import ru.school21.eleonard.data.network.repositories.ErrorHandlerRepository
import ru.school21.eleonard.menu.schoolInfo.data.SchoolInfoRepository
import ru.school21.eleonard.menu.schoolInfo.data.SchoolInfoRepositoryImpl
import ru.school21.eleonard.menu.schoolInfo.domain.GetUserInfoUseCase
import ru.school21.eleonard.menu.schoolInfo.domain.GetUserInfoUseCaseImpl
import ru.school21.eleonard.menu.schoolInfo.domain.GetUsersUseCase
import ru.school21.eleonard.menu.schoolInfo.domain.GetUsersUseCaseImpl

@Module
@InstallIn(ApplicationComponent::class)
object SchoolInfoModule {
	@Provides
	fun provideSchoolInfoRepository(
		apiRepository: ApiRepository,
		errorHandlerRepository: ErrorHandlerRepository,
	): SchoolInfoRepository {
		return SchoolInfoRepositoryImpl(apiRepository, errorHandlerRepository)
	}

	@Provides
	fun provideLoadUserInfoUseCase(schoolInfoRepository: SchoolInfoRepository): GetUserInfoUseCase {
		return GetUserInfoUseCaseImpl(schoolInfoRepository)
	}

	@Provides
	fun provideLoadUsersUseCase(schoolInfoRepository: SchoolInfoRepository): GetUsersUseCase {
		return GetUsersUseCaseImpl(schoolInfoRepository)
	}
}