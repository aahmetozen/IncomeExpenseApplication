package com.ahmetozen.incomeexpense.ui.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import com.ahmetozen.incomeexpense.data.model.IncomeEntity
import com.ahmetozen.incomeexpense.domain.use_cases.income.DeleteIncomeUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.income.GetAllIncomesUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.income.GetTotalIncomeUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.income.InsertIncomeUseCase
import com.ahmetozen.incomeexpense.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage.asStateFlow()

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
            val result=deleteIncomeUseCase(income)
            when(result){
                is Resource.Loading -> _isLoading.value=true
                is Resource.Success -> {
                    _errorMessage.value=null
                    _isLoading.value=false
                }
                is Resource.Error -> {
                    _errorMessage.value= "gelir silinirken hata olustu"
                    _isLoading.value=false
                }
            }
        }
    }

    fun getAll() {
        viewModelScope.launch {
            getAllIncomesUseCase().collect {
                when (it){
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value="gelirler alinirken hata olustu"
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

    fun getTotal(){
        viewModelScope.launch {
            getTotalIncomeUseCase().collect{
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "toplam gelir yüklenirken bir hata oluştu"
                        _isLoading.value = false
                    }
                    is Resource.Success -> {
                        _total.value = it.data
                        println(it.data)
                        _isLoading.value = false
                    }
                }
            }
        }
    }

}