package com.ahmetozen.incomeexpense.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmetozen.incomeexpense.data.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: ExpenseEntity)

    @Delete
    suspend fun delete(expense: ExpenseEntity)

    @Query("SELECT * FROM expense ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ExpenseEntity>>

    @Query("SELECT SUM(amount) FROM expense")
    fun getTotal(): Flow<Double?>
}