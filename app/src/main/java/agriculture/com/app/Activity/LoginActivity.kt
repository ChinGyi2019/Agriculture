package agriculture.com.app.Activity

import agriculture.com.app.R
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var phone_number =""// login_Edit.editableText.toString()
        Login_already_btn.setOnClickListener{

            phone_number = phoneInputEdit.editableText.toString()
            validNo(phone_number)
            val intent = Intent(this, RegisterSecondActivity::class.java)
            intent.putExtra("phone_number",phone_number )
            startActivity(intent)
        }

    }

    private fun validNo(no: String) {
        if (no.isEmpty() || no.length < 10) {
            login_Edit.setError("Enter a valid mobile")
            login_Edit.requestFocus()
            return
        }
    }
}
