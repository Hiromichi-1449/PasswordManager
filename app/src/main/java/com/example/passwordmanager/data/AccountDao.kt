package com.example.passwordmanager.data

import com.example.passwordmanager.Account
import androidx.room.*

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account)

    @Delete
    suspend fun delete(account: Account)

    @Query("SELECT * FROM accounts ORDER BY id DESC")
    suspend fun getAll(): List<Account>
}