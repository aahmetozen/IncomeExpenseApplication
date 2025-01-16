package com.ahmetozen.incomeexpense.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetozen.incomeexpense.domain.use_cases.expense.GetTotalExpenseUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.income.GetTotalIncomeUseCase
import com.ahmetozen.incomeexpense.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage.asStateFlow()

    init {
        getTotalExpenses()
        getTotalIncomes()
    }

    fun getTotalExpenses(){
        viewModelScope.launch {
            getTotalExpenseUseCase().collect{
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "toplam gider yüklenirken bir hata oluştu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        _totalexpense.value = it.data
                        println(it.data)
                        _isLoading.value = false
                    }
                }
            }
        }
    }
    fun getTotalIncomes(){
        viewModelScope.launch {
            getTotalIncomeUseCase().collect{
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "toplam gelir yüklenirken bir hata oluştu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        _totalincome.value = it.data
                        println(it.data)
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}