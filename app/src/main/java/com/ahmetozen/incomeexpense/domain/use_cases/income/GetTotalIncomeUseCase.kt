package com.ahmetozen.incomeexpense.domain.use_cases.income

import com.ahmetozen.incomeexpense.domain.repository.IncomeDaoImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalIncomeUseCase @Inject constructor(private val incomeDaoImpl: IncomeDaoImpl) {
    operator fun invoke(): Flow<Double?> {
        return incomeDaoImpl.getTotal()
    }
}