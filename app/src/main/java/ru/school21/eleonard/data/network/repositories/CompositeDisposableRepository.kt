package ru.school21.eleonard.data.network.repositories

import io.reactivex.disposables.CompositeDisposable

interface CompositeDisposableRepository {
	fun getCompositeDisposable() : CompositeDisposable

	fun dispose()
}