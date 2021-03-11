package ru.school21.eleonard.data.db.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.school21.eleonard.data.db.DbUtils
import ru.school21.eleonard.data.db.RealmMigration
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DbRealmModule {

	@Singleton
	@Provides
	fun providesDatabaseInstance(@ApplicationContext appContext: Context): Realm {
		if (Realm.getDefaultConfiguration() == null) {
			Realm.init(appContext)
			val config = RealmConfiguration.Builder()
				.name("eleonard.realm")
				.schemaVersion(0L)
				.migration(RealmMigration())
				.build()
			Realm.setDefaultConfiguration(config)
			return Realm.getDefaultInstance()
		} else
			return Realm.getDefaultInstance()
	}

	@Singleton
	@Provides
	fun providesDbUtils(realm: Realm): DbUtils {
		return DbUtils(realm)
	}
}