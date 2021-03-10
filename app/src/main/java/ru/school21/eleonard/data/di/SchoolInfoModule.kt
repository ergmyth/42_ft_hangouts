package ru.school21.eleonard.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.school21.eleonard.data.ApiRepository
import ru.school21.eleonard.helpers.requests.data.CompositeDisposableRepository
import ru.school21.eleonard.helpers.requests.data.ErrorHandlerRepository
import ru.school21.eleonard.menu.schoolInfo.data.SchoolInfoRepository
import ru.school21.eleonard.menu.schoolInfo.data.SchoolInfoRepositoryImpl
import ru.school21.eleonard.menu.schoolInfo.domain.LoadUserInfoUseCase
import ru.school21.eleonard.menu.schoolInfo.domain.LoadUserInfoUseCaseImpl

@Module
@InstallIn(ApplicationComponent::class)
object SchoolInfoModule {
	@Provides
	fun provideSchoolInfoRepository(
		apiRepository: ApiRepository,
		errorHandlerRepository: ErrorHandlerRepository,
		compositeDisposableRepository: CompositeDisposableRepository,
	): SchoolInfoRepository {
		return SchoolInfoRepositoryImpl(apiRepository, errorHandlerRepository, compositeDisposableRepository)
	}

	@Provides
	fun provideLoadUserInfoUseCase(schoolInfoRepository: SchoolInfoRepository): LoadUserInfoUseCase {
		return LoadUserInfoUseCaseImpl(schoolInfoRepository)
	}
}