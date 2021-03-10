package ru.school21.eleonard

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import ru.school21.eleonard.data.TokenRepository
import ru.school21.eleonard.helpers.Constants

//todo Подключить retrofit
//todo recyclerViews: diffUtils, itemDecoration
//todo contactInfo: допилить остальные поля, функциональные кнопки и загрузку аватара
//todo realm purgeBase and shared
//todo Безопасность: заменить логин на пин, Добавить в "ещё" создание пина
//todo Безопасность: фукнцию удаления имён у всех контактов, если пин есть, то запрашиваем пин, если нет, то окно подтверждения

@HiltAndroidApp
class BaseApp : Application() {
	companion object {
		private lateinit var instance: BaseApp
		private lateinit var sharedPreferences: SharedPreferences

		fun getInstance(): BaseApp {
			return instance
		}

		@JvmStatic
		fun getSharedPref(): SharedPreferences {
			return if (::sharedPreferences.isInitialized)
				sharedPreferences
			else
				instance.getSharedPreferences("ru.school21.eleonard-prefs", Context.MODE_PRIVATE)
		}

		init {
			AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
		}
	}

	override fun attachBaseContext(base: Context?) {
		super.attachBaseContext(base)

	}

	private var messageReceiver: BroadcastReceiver? = null
	override fun onCreate() {
		super.onCreate()

		instance = this
		loadTokensFromSharedPrefToRepo()
		loadNightMode()
		messageReceiver = MessageReceiver()
		registerReceiver(messageReceiver, IntentFilter(Constants.BROADCAST_ACTION_MESSAGE))
	}

	private fun loadNightMode() {
		AppCompatDelegate.setDefaultNightMode(getSharedPref().getInt(Constants.CHOSEN_THEME, 0))
	}

	private fun loadTokensFromSharedPrefToRepo() {
		if (getSharedPref().getString(Constants.ACCESS_TOKEN, "")!!.isNotEmpty())
			TokenRepository.loadTokenFromShared(
				getSharedPref().getString(Constants.ACCESS_TOKEN, "")!!,
				getSharedPref().getLong(Constants.ACCESS_TOKEN_LIVE_TIME, 0L),
			)
	}
}