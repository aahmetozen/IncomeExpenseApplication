package com.ahmetozen.incomeexpense.ui.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetozen.incomeexpense.data.model.IncomeEntity
import com.ahmetozen.incomeexpense.domain.use_cases.income.DeleteIncomeUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.income.GetAllIncomesUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.income.GetTotalIncomeUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.income.InsertIncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val deleteIncomeUseCase: DeleteIncomeUseCase,
    private val insertIncomeUseCase: InsertIncomeUseCase,
    private val getAllIncomesUseCase: GetAllIncomesUseCase,
    private val getTotalIncomeUseCase: GetTotalIncomeUseCase
) : ViewModel() {

    private val _incomes = MutableStateFlow<List<IncomeEntity>>(emptyList())
    val incomes: StateFlow<List<IncomeEntity>> = _incomes

    private val _total = MutableStateFlow<Double?>(0.0)
    val total:StateFlow<Double?> = _total

    init {
        getAll()
        getTotal()
    }

    fun insert(income:IncomeEntity){
        viewModelScope.launch {
            insertIncomeUseCase(income)
            getTotal()
        }
    }

    fun delete(income: IncomeEntity){
        viewModelScope.launch {
            deleteIncomeUseCase(income)
        }
    }

    fun getAll(){
        viewModelScope.launch {
            getAllIncomesUseCase().collect{result ->
                _incomes.value=result

            }
        }
    }

    fun getTotal(){
        viewModelScope.launch {
            getTotalIncomeUseCase().collect{result ->
                if (result != null) {
                    _total.value=result
                }
            }
        }
    }
}