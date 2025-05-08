package com.example.passwordmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.passwordmanager.R
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InspectionEditBottomSheet : BottomSheetDialogFragment() {

    /** Callbacks to hook into from your Activity */
    var onDelete: (() -> Unit)? = null
    var onUpdate: ((newName: String, newUser: String, newPass: String) -> Unit)? = null

    private lateinit var accountNameEdit: EditText
    private lateinit var usernameEdit:    EditText
    private lateinit var passwordEdit:    EditText
    private lateinit var editButton:      Button
    private lateinit var deleteButton:    Button

    private var accountName: String? = null
    private var username:    String? = null
    private var password:    String? = null
    private var isEditing:  Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accountName = it.getString(ARG_ACCOUNT_NAME)
            username    = it.getString(ARG_USERNAME)
            password    = it.getString(ARG_PASSWORD)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.inspection_edit, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountNameEdit = view.findViewById(R.id.accountNameEdit)
        usernameEdit    = view.findViewById(R.id.usernameEdit)
        passwordEdit    = view.findViewById(R.id.passwordEdit)
        editButton      = view.findViewById(R.id.editButton)
        deleteButton    = view.findViewById(R.id.deleteButton)

        // populate and lock
        accountNameEdit.setText(accountName)
        usernameEdit   .setText(username)
        passwordEdit   .setText(password)
        setEditingEnabled(false)

        editButton.setOnClickListener {
            if (!isEditing) {
                // switch to edit mode
                isEditing = true
                setEditingEnabled(true)
                editButton.text = "Save"

                // ———————— NEW: focus & show keyboard ————————
                accountNameEdit.requestFocus()
                accountNameEdit.setSelection(accountNameEdit.text?.length ?: 0)
                val imm = requireContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(accountNameEdit, InputMethodManager.SHOW_IMPLICIT)
            } else {
                // Save...
                onUpdate?.invoke(
                    accountNameEdit.text.toString(),
                    usernameEdit   .text.toString(),
                    passwordEdit   .text.toString()
                )
                dismiss()
            }
        }

        deleteButton.setOnClickListener {
            onDelete?.invoke()
            dismiss()
        }
    }

    private fun setEditingEnabled(enabled: Boolean) {
        accountNameEdit.isEnabled = enabled
        usernameEdit   .isEnabled = enabled
        passwordEdit   .isEnabled = enabled
    }

    companion object {
        private const val ARG_ACCOUNT_NAME = "account_name"
        private const val ARG_USERNAME     = "username"
        private const val ARG_PASSWORD     = "password"

        fun newInstance(accountName: String, username: String, password: String) =
            InspectionEditBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(ARG_ACCOUNT_NAME, accountName)
                    putString(ARG_USERNAME,     username)
                    putString(ARG_PASSWORD,     password)
                }
            }
    }
}