package ru.school21.eleonard.data.network.repositories

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