package com.ahmetozen.incomeexpense.domain.repository

import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import com.ahmetozen.incomeexpense.data.source.local.ExpenseDao
import com.ahmetozen.incomeexpense.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseDaoImpl @Inject constructor(
    private val expenseDao:ExpenseDao) {

    suspend fun insert(expense: ExpenseEntity): Resource<Unit> {
        return try {
            Resource.Loading
            expenseDao.insert(expense)
            Resource.Success(Unit)

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun delete(expense: ExpenseEntity): Resource<Unit> {
        return try {
            Resource.Loading
            expenseDao.delete(expense)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun getAll(): Flow<Resource<List<ExpenseEntity>>> = flow {
        try {
            emit(Resource.Loading)
            val data = expenseDao.getAll()
            val resultFlow = data.map { expenseEntity -> expenseEntity }
            resultFlow.collect { emit(Resource.Success(it)) }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    fun getTotal(): Flow<Resource<Double?>> = flow {
        try {
            emit(Resource.Loading)
            val data= expenseDao.getTotal()
            val resultFlow = data.map { expensetotal -> expensetotal }
            resultFlow.collect{emit(Resource.Success(it))}
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

}




/*
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

 */