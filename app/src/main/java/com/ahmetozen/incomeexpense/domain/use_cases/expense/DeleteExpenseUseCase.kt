package com.ahmetozen.incomeexpense.domain.use_cases.expense
import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import com.ahmetozen.incomeexpense.domain.repository.ExpenseDaoImpl
import javax.inject.Inject

class DeleteExpenseUseCase @Inject constructor(private val expenseDaoImpl: ExpenseDaoImpl) {
    suspend operator fun invoke(expense:ExpenseEntity){
        return expenseDaoImpl.delete(expense)
    }
}