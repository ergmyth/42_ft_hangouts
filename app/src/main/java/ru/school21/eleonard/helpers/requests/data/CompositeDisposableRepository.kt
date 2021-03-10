package ru.school21.eleonard.helpers.requests.data

import io.reactivex.disposables.CompositeDisposable

interface CompositeDisposableRepository {
	fun getCompositeDisposable() : CompositeDisposable

	fun dispose()
}