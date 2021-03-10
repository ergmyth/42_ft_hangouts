package ru.school21.eleonard.helpers.requests.data

import io.reactivex.disposables.CompositeDisposable

class CompositeDisposableRepositoryImpl : CompositeDisposableRepository {
    private val compositeDisposable = CompositeDisposable()

    override fun getCompositeDisposable() : CompositeDisposable {
        return compositeDisposable
    }

    override fun dispose() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}