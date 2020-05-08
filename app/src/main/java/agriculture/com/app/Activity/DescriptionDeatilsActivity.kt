package agriculture.com.app.Activity

import agriculture.com.app.R
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_description_deatils.*
import kotlinx.android.synthetic.main.activity_description_deatils.view.*
import android.content.Intent

class DescriptionDeatilsActivity : AppCompatActivity() {
    var title:String? =""
    var description:String?=""
    var number:String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_deatils)

        //ActionBar
        setSupportActionBar(description_tool_bar)
        // supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        description_tool_bar.setTitleTextColor(Color.parseColor("#FFFFFF"))
        // back arrow for white
       // val upArrow = ContextCompat.getDrawable(this, R.drawable.white_arrow)
       // upArrow!!.setColorFilter(ContextCompat.getColor(this,R.color.colorWhite), PorterDuff.Mode.SRC_ATOP)
      //  supportActionBar!!.setHomeAsUpIndicator(upArrow)
        var bundle = intent.extras!!
        title = bundle.getString("activity_name")
        description = bundle.getString("description")
        number = bundle.getString("num")


        supportActionBar!!.title = title
        title_text_view.text = title
        deatil_description.text = description

        when(number){
            "1"-> detail_image_view.setImageResource(R.drawable.gardening)
            "2"-> detail_image_view.setImageResource(R.drawable.watering)
            "3"-> detail_image_view.setImageResource(R.drawable.agriculture)
            "4"-> detail_image_view.setImageResource(R.drawable.farmer)

            else->  detail_image_view.setImageResource(R.drawable.gardening)


        }


    }

   }
