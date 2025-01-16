package com.ahmetozen.incomeexpense.ui.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import com.ahmetozen.incomeexpense.domain.use_cases.expense.DeleteExpenseUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.expense.GetAllExpenseUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.expense.GetTotalExpenseUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.expense.InsertExpenseUseCase
import com.ahmetozen.incomeexpense.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val getAllExpensesUseCase: GetAllExpenseUseCase,
    private val getTotalExpenseUseCase: GetTotalExpenseUseCase
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses: StateFlow<List<ExpenseEntity>> = _expenses // UI için veri akışı

    private val _total = MutableStateFlow<Double?>(0.0)
    val total:StateFlow<Double?> = _total

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage.asStateFlow()

    init {
        getTotal()
        getAll() // Başlangıçta veriyi yükle
    }

    fun insert(expense:ExpenseEntity){
        viewModelScope.launch {
            insertExpenseUseCase(expense)
            getTotal()
        }
    }

    fun delete(expense:ExpenseEntity){
        viewModelScope.launch {
            val result=deleteExpenseUseCase(expense)
            when(result){
                is Resource.Loading -> _isLoading.value=true
                is Resource.Success -> {
                    _errorMessage.value=null
                    _isLoading.value=false
                }
                is Resource.Error -> {
                    _errorMessage.value= "Gider silinirken hata olustu"
                    _isLoading.value=false
                }
            }
        }
    }

    fun getAll() {
        viewModelScope.launch {
            getAllExpensesUseCase().collect {
                when (it){
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value="giderler alinirken hata olustu"
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

    fun getTotal(){
        viewModelScope.launch {
            getTotalExpenseUseCase().collect{
                when (it) {
                    is Resource.Loading -> _isLoading.value = true
                    is Resource.Error -> {
                        _errorMessage.value = "toplam gider yüklenirken bir hata oluştu"
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
