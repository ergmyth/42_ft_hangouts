package ru.school21.eleonard.security.domain

import ru.school21.eleonard.security.data.SecurityRepository
import javax.inject.Inject

class ResetDataUseCaseImpl @Inject constructor(
	private val securityRepository: SecurityRepository
) : ResetDataUseCase {
	override fun invoke() {
		securityRepository.resetData()
	}
}