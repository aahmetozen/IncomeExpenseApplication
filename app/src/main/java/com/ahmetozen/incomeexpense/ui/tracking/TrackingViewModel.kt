package com.ahmetozen.incomeexpense.ui.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import com.ahmetozen.incomeexpense.data.model.IncomeEntity
import com.ahmetozen.incomeexpense.domain.use_cases.expense.GetAllExpenseUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.income.GetAllIncomesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor (
    private val getAllExpensesUseCase: GetAllExpenseUseCase,
    private val getAllIncomesUseCase: GetAllIncomesUseCase
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses: StateFlow<List<ExpenseEntity>> = _expenses

    private val _incomes = MutableStateFlow<List<IncomeEntity>>(emptyList())
    val incomes: StateFlow<List<IncomeEntity>> = _incomes
    init {
        fetchExpenses()
        fetchIncomes()
    }
    private fun fetchExpenses() {
        viewModelScope.launch {
            val source=getAllExpensesUseCase() //flowlist
            source.collect{ result-> // veri toplandi it geldi
                _expenses.value=result // collect veriyi topladi result a atti
            }
        }
    }

    fun fetchIncomes() {
        viewModelScope.launch {
            val source=getAllIncomesUseCase()
            source.collect{ result->
                _incomes.value=result
            }
        }
    }
}



/*
*
* class HomeViewModel(
    private val getAllExpensesUseCase: getAllExpenseUC,
    private val getAllIncomesUseCase: getAllIncomesUC
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses: StateFlow<List<ExpenseEntity>> = _expenses

    private val _incomes = MutableStateFlow<List<IncomeEntity>>(emptyList())
    val incomes: StateFlow<List<IncomeEntity>> = _incomes

    fun fetchExpenses() {
        viewModelScope.launch {
            _expenses.value = getAllExpensesUseCase()
        }
    }

    fun fetchIncomes() {
        viewModelScope.launch {
            _incomes.value = getAllIncomesUseCase()
        }
    }
} */