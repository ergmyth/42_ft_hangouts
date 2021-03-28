package ru.school21.eleonard.menu.schoolInfo.data

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import ru.school21.eleonard.Constants
import ru.school21.eleonard.data.network.helpers.Event
import javax.inject.Inject
import ru.school21.eleonard.data.network.ApiRepository
import ru.school21.eleonard.data.network.api.models.UserResponse
import ru.school21.eleonard.data.network.helpers.ApiErrors
import ru.school21.eleonard.data.network.helpers.ResponseWrapper
import ru.school21.eleonard.data.network.repositories.CompositeDisposableRepository
import ru.school21.eleonard.data.network.repositories.ErrorHandlerRepository

class SchoolInfoRepositoryImpl @Inject constructor(
	private val apiRepository: ApiRepository,
	private val errorHandlerRepository: ErrorHandlerRepository,
	private val compositeDisposableRepository: CompositeDisposableRepository,
) : SchoolInfoRepository {
	override fun loadUserInfo(userName: String, userInfoLoadingResponse: MutableLiveData<Event<UserResponse>>) {
		val functionName: String? = object {}.javaClass.enclosingMethod?.name
		userInfoLoadingResponse.value = Event.loading()
		compositeDisposableRepository.getCompositeDisposable().add(
			apiRepository.getUserInfo(userName)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(object : DisposableObserver<ResponseWrapper<UserResponse>>() {
					override fun onNext(response: ResponseWrapper<UserResponse>) {
						if (response.statusId == Constants.HTTP_SUCCESSFUL) {
							if (response.data == null)
								errorHandlerRepository.handleApiError(functionName, ApiErrors.EMPTY_DATA, userInfoLoadingResponse)
							else
								userInfoLoadingResponse.postValue(Event.success(response.data))
						} else
							errorHandlerRepository.handleApiError(functionName, ApiErrors.IS_NOT_200, userInfoLoadingResponse)
					}

					override fun onComplete() {}

					override fun onError(e: Throwable) {
						errorHandlerRepository.handleErrorResponse(e, functionName, userInfoLoadingResponse)
					}
				})
		)
	}
}