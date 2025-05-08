import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.example.passwordmanager.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddAccountBottomSheet(private val onAccountAdded: (String, String, String) -> Unit) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val accountField = view.findViewById<EditText>(R.id.accountName)
        val usernameField = view.findViewById<EditText>(R.id.username)
        val passwordField = view.findViewById<EditText>(R.id.password)

        saveButton.setOnClickListener {
            val account = accountField.text.toString()
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            // Call lambda to update the activity
            onAccountAdded(account, username, password)

            dismiss()
        }

        return view

    }
}