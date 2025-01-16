package com.ahmetozen.incomeexpense.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetozen.incomeexpense.domain.use_cases.expense.GetTotalExpenseUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.income.GetTotalIncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTotalExpenseUseCase: GetTotalExpenseUseCase,
    private val getTotalIncomeUseCase: GetTotalIncomeUseCase
) : ViewModel() {

    private val _totalincome = MutableStateFlow<Double?>(0.0)
    val totalincome:StateFlow<Double?> = _totalincome

    private val _totalexpense = MutableStateFlow<Double?>(0.0)
    val totalexpense:StateFlow<Double?> = _totalexpense

    init {
        getTotalExpenses()
        getTotalIncomes()
    }

    fun getTotalExpenses(){
        viewModelScope.launch {
            getTotalExpenseUseCase().collect{result -> result.let { _totalexpense.value=it }}
        }
    }
    fun getTotalIncomes(){
        viewModelScope.launch {
            getTotalIncomeUseCase().collect{result -> result.let { _totalincome.value=it }}
        }
    }
}