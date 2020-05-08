 package agriculture.com.app.Activity

import agriculture.com.app.R
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_starting.*




 class StartingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting)



        registerBtn.setOnClickListener {
           // var phone_number= phoneInputEdit.text.toString()
           // validNo(phone_number)
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("too_bar_name", "Register_Activity")
            startActivity(intent)

        }


    }


}
