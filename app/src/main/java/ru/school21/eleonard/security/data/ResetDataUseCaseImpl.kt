package ru.school21.eleonard.security.data

import ru.school21.eleonard.security.domain.ResetDataUseCase
import ru.school21.eleonard.security.domain.SecurityRepository
import javax.inject.Inject

class ResetDataUseCaseImpl @Inject constructor(
	private val securityRepository: SecurityRepository
) : ResetDataUseCase {
	override fun invoke() {
		securityRepository.resetData()
	}
}