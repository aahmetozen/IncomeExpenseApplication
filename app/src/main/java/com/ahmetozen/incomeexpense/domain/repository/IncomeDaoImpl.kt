package com.ahmetozen.incomeexpense.domain.repository

import com.ahmetozen.incomeexpense.data.model.IncomeEntity
import com.ahmetozen.incomeexpense.data.source.local.IncomeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IncomeDaoImpl @Inject constructor(
    private val incomeDao:IncomeDao
) :IncomeDao {
    override suspend fun insert(income: IncomeEntity) {
        incomeDao.insert(income)
    }

    override suspend fun delete(income: IncomeEntity) {
        incomeDao.delete(income)
    }

    override fun getAll(): Flow<List<IncomeEntity>> {
        return incomeDao.getAll()
    }

    override fun getTotal(): Flow<Double?> {
        return incomeDao.getTotal()
    }

}