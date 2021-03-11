package ru.school21.eleonard.menu.schoolInfo.data

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.HttpException
import ru.school21.eleonard.Constants
import ru.school21.eleonard.data.network.helpers.Event
import javax.inject.Inject
import ru.school21.eleonard.data.network.ApiRepository
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.data.network.helpers.ResponseWrapper
import ru.school21.eleonard.data.network.repositories.CompositeDisposableRepository
import ru.school21.eleonard.data.network.repositories.ErrorHandlerRepository

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
				.subscribeWith(object : DisposableObserver<ResponseWrapper<UserResponse>>() {
					override fun onNext(response: ResponseWrapper<UserResponse>) {
						if (response.statusId == Constants.HTTP_SUCCESSFULL) {
							response.data?.let {
								userInfoLoadingResponse.postValue(Event.success(it))
							} ?: userInfoLoadingResponse.postValue(Event.error(errorHandlerRepository.handleErrorMessage("getUserInfo", "Data is empty")))
						} else {
							userInfoLoadingResponse.postValue(Event.error(errorHandlerRepository.handleErrorMessage("getUserInfo", "Status code is not 200")))
						}
					}
					override fun onComplete() {}
					override fun onError(e: Throwable) {
						(e as? HttpException)?.let {
							userInfoLoadingResponse.postValue(Event.error(errorHandlerRepository.handleHttpException("getUserInfo", it.code())))
						} ?: userInfoLoadingResponse.postValue(Event.error(errorHandlerRepository.handleErrorMessage("getUserInfo", e.message ?: "ErrorMessage is empty")))
					}
				})
		)
	}
}