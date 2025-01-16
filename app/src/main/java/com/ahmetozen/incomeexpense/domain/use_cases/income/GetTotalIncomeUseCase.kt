package com.ahmetozen.incomeexpense.domain.use_cases.income

import com.ahmetozen.incomeexpense.domain.repository.IncomeDaoImpl
import com.ahmetozen.incomeexpense.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalIncomeUseCase @Inject constructor(private val incomeDaoImpl: IncomeDaoImpl) {
    operator fun invoke(): Flow<Resource<Double?>> {
        return incomeDaoImpl.getTotal()
    }
}