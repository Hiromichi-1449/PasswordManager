package com.example.passwordmanager

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PasswordGeneratorBottomSheet : BottomSheetDialogFragment() {

    private lateinit var passwordField: TextView
    private lateinit var copyButton: ImageButton
    private lateinit var generateButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.bottomsheet_password_generator, container, false)

        passwordField = view.findViewById(R.id.generatedPassword)
        copyButton = view.findViewById(R.id.btnCopy)
        generateButton = view.findViewById(R.id.btnGenerate)

        generateButton.setOnClickListener {
            val lengthInput = view.findViewById<EditText>(R.id.inputLength)
            val switchNumbers = view.findViewById<Switch>(R.id.switchNumbers)
            val switchSymbols = view.findViewById<Switch>(R.id.switchSymbols)
            val switchUppercase = view.findViewById<Switch>(R.id.switchUppercase)

            val length = lengthInput.text.toString().toIntOrNull() ?: 12
            val useNumbers = switchNumbers.isChecked
            val useSymbols = switchSymbols.isChecked
            val useUppercase = switchUppercase.isChecked

            val password = generatePassword(length, useNumbers, useSymbols, useUppercase)
            passwordField.text = password
        }

        copyButton.setOnClickListener {
            val clipboard = ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
            val clip = ClipData.newPlainText("Generated Password", passwordField.text)
            clipboard?.setPrimaryClip(clip)
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun generatePassword(length: Int, useNumbers: Boolean, useSymbols: Boolean, useUppercase: Boolean): String {
        var chars = "abcdefghijklmnopqrstuvwxyz"

        if (useUppercase) chars += "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        if (useNumbers) chars += "0123456789"
        if (useSymbols) chars += "!@#\$%^&*()-_=+<>?"

        if (chars.isEmpty()) return ""

        return (1..length).map { chars.random() }.joinToString("")
    }
}
