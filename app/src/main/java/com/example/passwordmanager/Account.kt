package com.example.passwordmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountName: String,
    val username: String,
    val password: String,
    val notes: String? = null
)
