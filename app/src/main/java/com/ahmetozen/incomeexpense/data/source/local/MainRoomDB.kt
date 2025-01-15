package com.ahmetozen.incomeexpense.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import com.ahmetozen.incomeexpense.data.model.IncomeEntity

@Database(entities = [IncomeEntity::class, ExpenseEntity::class], version = 1, exportSchema = false)
abstract class MainRoomDB : RoomDatabase() {
    abstract fun incomeDao(): IncomeDao
    abstract fun expenseDao() : ExpenseDao
}