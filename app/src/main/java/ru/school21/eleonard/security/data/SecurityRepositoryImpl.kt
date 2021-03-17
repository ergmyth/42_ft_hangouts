package ru.school21.eleonard.security.data

import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.R
import ru.school21.eleonard.data.db.DbUtils
import ru.school21.eleonard.data.network.NetworkHolder
import ru.school21.eleonard.data.network.TokenRepository
import ru.school21.eleonard.helpers.utils.ToastStates
import ru.school21.eleonard.helpers.utils.UtilsUI
import ru.school21.eleonard.security.helpers.EncryptionUtils
import javax.inject.Inject

class SecurityRepositoryImpl @Inject constructor(
	private val dbUtils: DbUtils,
) : SecurityRepository {
	override fun resetData() {
		dbUtils.purgeBase()
		TokenRepository.deleteToken()
		NetworkHolder.httpClient.dispatcher.cancelAll()
		EncryptionUtils.clear()
		BaseApp.getSharedPref().edit().clear().apply()
		UtilsUI.makeCoolToast(BaseApp.getInstance().resources.getString(R.string.pin_reset_data), ToastStates.WARNING)
	}
}