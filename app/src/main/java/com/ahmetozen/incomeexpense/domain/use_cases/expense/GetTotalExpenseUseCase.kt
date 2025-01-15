package com.ahmetozen.incomeexpense.domain.use_cases.expense

import com.ahmetozen.incomeexpense.domain.repository.ExpenseDaoImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalExpenseUseCase @Inject constructor(private val expenseDaoImpl: ExpenseDaoImpl) {
    operator fun invoke(): Flow<Double?> {
        return expenseDaoImpl.getTotal()
    }
}