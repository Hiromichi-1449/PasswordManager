package com.example.passwordmanager

import AddAccountBottomSheet
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.lifecycle.lifecycleScope
import com.example.passwordmanager.Account
import com.example.passwordmanager.data.AppDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val addButton       = findViewById<Button>(R.id.addButton)
        val accountContainer= findViewById<LinearLayout>(R.id.accountContainer)

        findViewById<Button>(R.id.btnGeneratePassword).setOnClickListener {
            val bottomSheet = PasswordGeneratorBottomSheet()
            bottomSheet.show(supportFragmentManager, "PasswordGenerator")
        }

        val db = AppDatabase.getInstance(this@MainActivity)
        val dao = db.accountDao()

        lifecycleScope.launch {
            val allAccounts = dao.getAll()

            allAccounts.forEach { account ->
                val itemView = layoutInflater.inflate(
                    R.layout.account_item, accountContainer, false
                )
                val titleView = itemView.findViewById<TextView>(R.id.accountTitle)
                val passView  = itemView.findViewById<TextView>(R.id.accountPasswordMask)

                titleView.text = account.accountName
                passView.text = "**********"

                val info = AccountInfo(account.accountName, account.username, account.password)
                itemView.tag = Pair(info, account)

                itemView.setOnClickListener {
                    val (info, storedAccount) = itemView.tag as Pair<AccountInfo, Account>
                    val sheet = InspectionEditBottomSheet.newInstance(
                        info.accountName,
                        info.username,
                        info.password
                    ).apply {
                        onDelete = {
                            accountContainer.removeView(itemView)
                            lifecycleScope.launch {
                                dao.delete(storedAccount)
                            }
                        }
                        onUpdate = { newName, newUser, newPass ->
                            titleView.text = newName
                            itemView.tag = Pair(AccountInfo(newName, newUser, newPass), storedAccount)
                        }
                    }
                    sheet.show(supportFragmentManager, "InspectionEdit")
                }

                accountContainer.addView(itemView)
            }
        }

        addButton.setOnClickListener {
            // Build and show the ADD sheet
            val addSheet = AddAccountBottomSheet { accountName, username, password ->
                // 1) inflate & populate
                val itemView = layoutInflater.inflate(
                    R.layout.account_item, accountContainer, false
                )
                val titleView = itemView.findViewById<TextView>(R.id.accountTitle)
                val passView  = itemView.findViewById<TextView>(R.id.accountPasswordMask)

                titleView.text = accountName
                passView .text = "**********"

                // 2) tag it, so we can edit/delete later
                val info = AccountInfo(accountName, username, password)

                val db = AppDatabase.getInstance(this@MainActivity)
                val dao = db.accountDao()

                lifecycleScope.launch {
                    dao.insert(Account(accountName = accountName, username = username, password = password))
                }

                itemView.tag = info

                // 3) tapping the item opens the INSPECTION sheet
                itemView.setOnClickListener {
                    val sheet = InspectionEditBottomSheet.newInstance(
                        info.accountName,
                        info.username,
                        info.password
                    ).apply {
                        // remove the view on delete
                        onDelete = { accountContainer.removeView(itemView) }
                        // update title & tag on save
                        onUpdate = { newName, newUser, newPass ->
                            titleView.text = newName
                            itemView.tag = AccountInfo(newName, newUser, newPass)
                        }
                    }
                    sheet.show(supportFragmentManager, "InspectionEdit")
                }

                accountContainer.addView(itemView)
            }

            addSheet.show(supportFragmentManager, "AddAccountBottomSheet")
        }
    }
}