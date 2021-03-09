package ru.school21.eleonard.menu.other.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.school21.eleonard.helpers.requests.Event
import ru.school21.eleonard.menu.contacts.domain.LoadContactListUseCase
import ru.school21.eleonard.menu.contacts.models.Contact
import kotlinx.coroutines.flow.collect
import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.data.db.realmModels.ContactRealmModel
import ru.school21.eleonard.helpers.Constants
import ru.school21.eleonard.menu.contacts.domain.getContactList.GetContactListUseCase

class SettingsViewModel @ViewModelInject constructor() : ViewModel() {
	var currentTheme = MutableLiveData<Int>()

	init {
		currentTheme.value = BaseApp.getSharedPref().getInt(Constants.CHOSEN_THEME, 0)
	}
}