package com.ahmetozen.incomeexpense.domain.use_cases.expense

import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import com.ahmetozen.incomeexpense.domain.repository.ExpenseDaoImpl
import com.ahmetozen.incomeexpense.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExpenseUseCase @Inject constructor(private val expenseDaoImpl: ExpenseDaoImpl) {
     operator fun invoke(): Flow<Resource<List<ExpenseEntity>>>{
        return expenseDaoImpl.getAll()
    }
}