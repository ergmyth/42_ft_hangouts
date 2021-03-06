package ru.school21.eleonard.menu.other.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.school21.eleonard.BaseApp
import ru.school21.eleonard.Constants

class SettingsViewModel @ViewModelInject constructor() : ViewModel() {
	var currentTheme = MutableLiveData<Int>()

	init {
		currentTheme.value = BaseApp.getSharedPref().getInt(Constants.SP_CHOSEN_THEME, 0)
	}
}