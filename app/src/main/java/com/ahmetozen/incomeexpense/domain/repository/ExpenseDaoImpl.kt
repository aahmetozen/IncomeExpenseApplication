package com.ahmetozen.incomeexpense.domain.repository

import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import com.ahmetozen.incomeexpense.data.source.local.ExpenseDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseDaoImpl @Inject constructor(
    private val expenseDao:ExpenseDao)
    :ExpenseDao {

    override suspend fun insert(expense: ExpenseEntity) {
        expenseDao.insert(expense)
    }

    override suspend fun delete(expense: ExpenseEntity) {
        expenseDao.delete(expense)
    }

    override fun getAll(): Flow<List<ExpenseEntity>> {
        return expenseDao.getAll()
    }

    override fun getTotal(): Flow<Double?> {
        return expenseDao.getTotal()
    }

}