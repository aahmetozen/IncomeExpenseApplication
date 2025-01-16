package com.ahmetozen.incomeexpense.domain.use_cases.expense

import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import com.ahmetozen.incomeexpense.domain.repository.ExpenseDaoImpl
import com.ahmetozen.incomeexpense.util.Resource
import javax.inject.Inject

class InsertExpenseUseCase @Inject constructor(private val expenseDaoImpl: ExpenseDaoImpl) {
    suspend operator fun invoke(expense:ExpenseEntity): Resource<Unit> {
        return expenseDaoImpl.insert(expense)
    }
}