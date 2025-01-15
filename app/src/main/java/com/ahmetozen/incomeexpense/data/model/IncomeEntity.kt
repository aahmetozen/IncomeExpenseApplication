package com.ahmetozen.incomeexpense.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income")
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val category: String,
    val amount:Double,
    val description:String?,
    val createdAt:Long=System.currentTimeMillis()
)