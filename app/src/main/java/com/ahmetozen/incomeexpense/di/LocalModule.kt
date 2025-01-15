package com.ahmetozen.incomeexpense.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahmetozen.incomeexpense.data.source.local.ExpenseDao
import com.ahmetozen.incomeexpense.data.source.local.IncomeDao
import com.ahmetozen.incomeexpense.data.source.local.MainRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): MainRoomDB {
        return Room.databaseBuilder(
            context,
            MainRoomDB::class.java,
            "Tracking"
        ).build()
    }

    @Provides
    fun provideExpenseDao(database: MainRoomDB): ExpenseDao{
        return database.expenseDao()
    }

    @Provides
    fun provideIncomeDao(database: MainRoomDB): IncomeDao {
        return database.incomeDao()
    }
}