package com.ahmetozen.incomeexpense.domain.use_cases.income

import com.ahmetozen.incomeexpense.data.model.IncomeEntity
import com.ahmetozen.incomeexpense.domain.repository.IncomeDaoImpl
import javax.inject.Inject

class InsertIncomeUseCase @Inject constructor(private val incomeDaoImpl: IncomeDaoImpl) {
    suspend operator fun invoke(income:IncomeEntity) {
        return incomeDaoImpl.insert(income)
    }
}