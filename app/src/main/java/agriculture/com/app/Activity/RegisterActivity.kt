package agriculture.com.app.Activity

import agriculture.com.app.MainActivity
import agriculture.com.app.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import android.content.Intent
import com.google.firebase.auth.AuthResult
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class RegisterActivity : AppCompatActivity() {

    lateinit var phone_number:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        continueBtn.setOnClickListener{

            phone_number = phoneInputEdit.editableText.toString()
            validNo(phone_number)
            val intent = Intent(this, RegisterSecondActivity::class.java)
            intent.putExtra("phone_number",phone_number )
            startActivity(intent)
        }
    }



    private fun validNo(no: String) {
        if (no.isEmpty() || no.length < 9) {
            phoneInputEdit.setError("Enter a valid mobile")
            phoneInputEdit.requestFocus()
            return
        }
}
}
