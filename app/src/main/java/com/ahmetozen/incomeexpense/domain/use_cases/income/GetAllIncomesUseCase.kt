package com.ahmetozen.incomeexpense.domain.use_cases.income

import com.ahmetozen.incomeexpense.data.model.IncomeEntity
import com.ahmetozen.incomeexpense.domain.repository.IncomeDaoImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllIncomesUseCase @Inject constructor(private val incomeDaoImpl: IncomeDaoImpl){
     operator fun invoke():Flow<List<IncomeEntity>>{
        return incomeDaoImpl.getAll()
    }
}