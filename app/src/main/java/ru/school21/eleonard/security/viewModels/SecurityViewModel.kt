package ru.school21.eleonard.security.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import ru.school21.eleonard.security.domain.ResetDataUseCase
import ru.school21.eleonard.security.helpers.PinState

class SecurityViewModel @ViewModelInject constructor(
	private val resetDataUseCase: ResetDataUseCase,
) : ViewModel() {
	lateinit var currentPinState: PinState
	lateinit var confirmPin: String
	var pinCode = ""
	var attemptsLeft = 0

	fun resetData() {
		resetDataUseCase.invoke()
	}
}