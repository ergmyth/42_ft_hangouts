package ru.school21.eleonard.data.db

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent
import io.realm.Realm
import io.realm.RealmModel
import ru.school21.eleonard.BaseApp
import java.lang.IllegalStateException

class RealmUtils {
	@EntryPoint
	@InstallIn(ApplicationComponent::class)
	interface RealmUtilsEntryPoint {
		fun provideRealm(): Realm
	}

	var realm: Realm
	companion object {
		private var instance: RealmUtils = RealmUtils()

		fun getInstance(): RealmUtils {
			return instance
		}

	}

	init {
		val appContext = BaseApp.getInstance().applicationContext ?: throw IllegalStateException()
		val hiltEntryPoint = EntryPointAccessors.fromApplication(appContext, RealmUtilsEntryPoint::class.java)

		realm = hiltEntryPoint.provideRealm()
	}

	fun insertOrUpdate(realmObject: RealmModel) {
		if (!realm.isInTransaction)
			realm.beginTransaction()
		realm.insertOrUpdate(realmObject)
		realm.commitTransaction()
	}
}