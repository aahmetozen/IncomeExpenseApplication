package com.ahmetozen.incomeexpense.ui.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import com.ahmetozen.incomeexpense.data.model.IncomeEntity
import com.ahmetozen.incomeexpense.domain.use_cases.expense.GetAllExpenseUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.income.GetAllIncomesUseCase
import com.ahmetozen.incomeexpense.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage.asStateFlow()


    init {
        fetchExpenses()
        fetchIncomes()
    }

    fun fetchExpenses() {
        viewModelScope.launch {
            getAllExpensesUseCase().collect {
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "giderler alinirken hata olustu"
                        _isLoading.value = false
                    }

                    is Resource.Success -> {
                        _expenses.value = it.data
                        println(it.data)
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun fetchIncomes() {
        viewModelScope.launch {
            getAllIncomesUseCase().collect {
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "gelirler alinirken hata olustu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        _incomes.value = it.data
                        println(it.data)
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}


/*
*
* @HiltViewModel
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
*  */
