package com.ahmetozen.incomeexpense.domain.repository

import com.ahmetozen.incomeexpense.data.model.IncomeEntity
import com.ahmetozen.incomeexpense.data.source.local.IncomeDao
import com.ahmetozen.incomeexpense.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IncomeDaoImpl @Inject constructor(
    private val incomeDao:IncomeDao)  {

     suspend fun insert(income: IncomeEntity) :Resource<Unit> {
        return try {
            Resource.Loading
            incomeDao.insert(income)
            Resource.Success(Unit)
        }
        catch (e:Exception){
            Resource.Error(e)
        }
    }

     suspend fun delete(income: IncomeEntity):Resource<Unit> {
         return try {
             Resource.Loading
             incomeDao.delete(income)
             Resource.Success(Unit)
         }
         catch (e:Exception){
             Resource.Error(e)
         }
    }

     fun getAll(): Flow<Resource<List<IncomeEntity>>> = flow {
         try {
             emit(Resource.Loading)
             val data=incomeDao.getAll()
             val resultFlow=data.map { incomeEntity-> incomeEntity }
             resultFlow.collect{emit(Resource.Success(it))}
         }
         catch (e:Exception){
             emit(Resource.Error(e))
         }
    }

     fun getTotal(): Flow<Resource<Double?>> = flow {
         try {
             emit(Resource.Loading)
             val data= incomeDao.getTotal()
             val resultFlow=data.map { total-> total }
             resultFlow.collect{emit(Resource.Success(it))}
         }
         catch (e:Exception){
             emit(Resource.Error(e))
         }
    }
}