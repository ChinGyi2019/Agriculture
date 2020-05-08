package agriculture.com.app.Activity

import agriculture.com.app.MainActivity
import agriculture.com.app.R
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setStatusBarTrasparent()

        mAuth = FirebaseAuth.getInstance()

        val imgAnim : Animation = AnimationUtils.loadAnimation(this,R.anim.imag_anim)
        val cpyRAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.cpy_anim)

        info_tech.startAnimation(imgAnim)
        copyRight.startAnimation(cpyRAnimation)
        title_name.startAnimation(imgAnim)
        progressBar.visibility = View.VISIBLE
        val SPLASH_TIME_OUT :Long = 3300
        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }

    fun setStatusBarTrasparent(): Unit {
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        setStatusBarTrasparent()
    }

    fun sentToStart() {
        val intent = Intent(this@SplashScreenActivity, StartingActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        var mCurrentUser: FirebaseUser? = mAuth.currentUser

        if (mCurrentUser == null) {
            sentToStart()
        }
    }
}
