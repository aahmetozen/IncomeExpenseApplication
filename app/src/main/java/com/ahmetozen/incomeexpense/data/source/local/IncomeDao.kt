package com.ahmetozen.incomeexpense.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmetozen.incomeexpense.data.model.IncomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(income:IncomeEntity)

    @Delete
    suspend fun delete(income: IncomeEntity)

    @Query("SELECT * FROM income ORDER BY createdAt DESC")
    fun getAll():Flow<List<IncomeEntity>>

    @Query("SELECT SUM(amount) FROM income")
    fun getTotal(): Flow<Double?>
}