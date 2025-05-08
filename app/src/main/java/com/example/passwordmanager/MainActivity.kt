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