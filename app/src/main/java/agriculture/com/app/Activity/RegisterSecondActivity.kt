package agriculture.com.app.Activity

import agriculture.com.app.MainActivity
import agriculture.com.app.R
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_register_second.*
import java.util.concurrent.TimeUnit

class RegisterSecondActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var phone_number:String
    lateinit var mVerificationId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_second)

        mAuth = FirebaseAuth.getInstance()
        phone_number = intent.getStringExtra("phone_number")

        sendVerificationCode(phone_number);

        LoginRegisterBtn.setOnClickListener{
            var code = LoginEditTestView.getText().toString().trim();
            if (code.isEmpty() || code.length < 6) {
                LoginEditTestView.setError("Enter valid code");
                LoginEditTestView.requestFocus();
                return@setOnClickListener;
            }

            //verifying the code entered manually
            verifyVerificationCode(code);

        }

    }

    private fun verifyVerificationCode(code: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId, code)

        //signing the user
        signInWithPhoneAuthCredential(credential)
    }



    private fun sendVerificationCode(phone_number: String?) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+95" + phone_number,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallbacks);

    }

    /* private val  mCallbacks:PhoneAuthProvider.
     OnVerificationStateChangedCallbacks = PhoneAuthProvider.OnVerificationStateChangedCallbacks()*/


    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

            //Getting the code sent by SMS
            val code = phoneAuthCredential.smsCode

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code

            if (code != null) {
                LoginEditTestView.setText(code)
                //verifying the code
                verifyVerificationCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@RegisterSecondActivity, e.message, Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(s: String?, forceResendingToken: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(s, forceResendingToken)

            //storing the verification id that is sent to the user
            mVerificationId = s.toString()
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this@RegisterSecondActivity, object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful()) {
                        //verification successful we will start the profile activity
                        val intent = Intent(this@RegisterSecondActivity, SplashScreenActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)

                    } else {

                        //verification unsuccessful.. display an error message

                        var message = "Somthing is wrong, we will fix it soon..."

                        if (task.getException() is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }


                    }
                }
            })
    }
}
