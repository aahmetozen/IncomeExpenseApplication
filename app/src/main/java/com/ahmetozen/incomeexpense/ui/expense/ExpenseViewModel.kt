package com.ahmetozen.incomeexpense.ui.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import com.ahmetozen.incomeexpense.domain.use_cases.expense.DeleteExpenseUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.expense.GetAllExpenseUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.expense.GetTotalExpenseUseCase
import com.ahmetozen.incomeexpense.domain.use_cases.expense.InsertExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
            deleteExpenseUseCase(expense)
        }
    }

    fun getAll() {
        viewModelScope.launch {
            getAllExpensesUseCase().collect { result ->
                _expenses.value = result // Flow'dan gelen liste güncellenir
            }
        }
    }

    fun getTotal(){
        viewModelScope.launch {
            getTotalExpenseUseCase().collect{result -> result.let { _total.value=it }}
        }
    }

}
