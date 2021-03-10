package ru.school21.eleonard.menu.schoolInfo.data

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.school21.eleonard.helpers.requests.Event
import javax.inject.Inject
import ru.school21.eleonard.data.ApiRepository
import ru.school21.eleonard.data.api.models.UserResponse
import ru.school21.eleonard.helpers.requests.data.CompositeDisposableRepository
import ru.school21.eleonard.helpers.requests.data.ErrorHandlerRepository

class SchoolInfoRepositoryImpl @Inject constructor(
	private val apiRepository: ApiRepository,
	private val errorHandlerRepository: ErrorHandlerRepository,
	private val compositeDisposableRepository: CompositeDisposableRepository,
) : SchoolInfoRepository {
	override fun loadUserInfo(userName: String, userInfoLoadingResponse: MutableLiveData<Event<UserResponse>>) {
		userInfoLoadingResponse.value = Event.loading()
		compositeDisposableRepository.getCompositeDisposable().add(
			apiRepository.getUserInfo(userName)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.unsubscribeOn(Schedulers.io())
				.subscribeWith(object : DisposableObserver<UserResponse>() {
					override fun onNext(response: UserResponse) {
						userInfoLoadingResponse.postValue(Event.success(response))
					}

					override fun onComplete() {}

					override fun onError(e: Throwable) {
						userInfoLoadingResponse.postValue(Event.error(errorHandlerRepository.handleErrorMessage(e.message!!)))
					}
				})
		)
	}
}