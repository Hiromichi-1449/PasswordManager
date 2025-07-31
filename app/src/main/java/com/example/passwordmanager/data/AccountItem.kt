package com.example.passwordmanager.data

import android.provider.ContactsContract

data class AccountItem(
    val website: ContactsContract.CommonDataKinds.Website,
    val username : String,
    val password : String
)
