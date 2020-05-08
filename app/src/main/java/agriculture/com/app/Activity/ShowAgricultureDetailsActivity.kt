package agriculture.com.app.Activity

import agriculture.com.app.R
import agriculture.com.app.model.Agrictlutre
import agriculture.com.app.model.Favourite
import agriculture.com.app.model.User_Fav_Lists
import android.R.attr.*
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.activity_show_agriculture_details.*
import kotlinx.android.synthetic.main.activity_show_agriculture_details.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.show_agriculture_detail_layout.view.*
import java.security.AccessController.getContext
import android.app.Activity
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.R.id.edit
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import java.lang.Math.log
import kotlin.collections.MutableMap as MutableMap1


class ShowAgricultureDetailsActivity : AppCompatActivity() {
    lateinit var name:String
    lateinit var desp_one:String
    lateinit var desp_two:String
    lateinit var desp_three:String
    lateinit var desp_four:String
   // lateinit var favourite:String
    lateinit var id:String
    lateinit var picture:String
    lateinit var bundle:Bundle
    lateinit var db:FirebaseFirestore
    lateinit var mAuth:FirebaseAuth

     var currentUserId:String? =""
     var toogle = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_agriculture_details)

        //ActionBar
        setSupportActionBar(details_tool_bar)
        // supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        details_tool_bar.setTitleTextColor(Color.parseColor("#FFFFFF"))
        // back arrow for white
        val upArrow = ContextCompat.getDrawable(this, R.drawable.white_arrow)
        upArrow!!.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        db = FirebaseFirestore.getInstance()



        bundle = intent.extras!!
        mAuth = FirebaseAuth.getInstance()
        var currentUser: FirebaseUser? = mAuth.currentUser
        currentUserId = mAuth.currentUser!!.uid

        name = bundle.getString("name")
        id = bundle.getString("id")
        desp_one = bundle.getString("desp_one")
        desp_two = bundle.getString("desp_two")
        desp_three = bundle.getString("desp_three")
        desp_four = bundle.getString("desp_four")





        /*  docRef.addSnapshotListener(EventListener { documentSnapshots, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    // Log.w(TAG, "Error" + firebaseFirestoreException.message)

                }
                for (doc in documentSnapshots!!.getDocumentChanges()) {

                   run {
                        var agrictlutre:Agrictlutre = doc.document.toObject(Agrictlutre::class.java)

                            if(agrictlutre.favourite == "1"){
                                favouriteBtn.setImageResource(R.drawable.star_pink)
                                toogle = true

                            }else if(agrictlutre.favourite == "0")
                            {
                                favouriteBtn.setImageResource(R.drawable.star)
                                toogle = false
                            }
                            else {
                                favouriteBtn.setImageResource(R.drawable.star)
                            }

                    }
                }
            })*/



        loadPreRefrence()

        app_bar_title.text = name.toString()
        agri_name.text = name.toString()
        picture = bundle?.getString("agri_Picture")
        Glide.with(application).load(picture).placeholder(R.drawable.farmer).into(agri_picture)

        val docRef = db.collection("agriculture_db/$name/favourite")
            .addSnapshotListener( { documentSnapshots, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {

                }

                for (doc in documentSnapshots!!.getDocumentChanges()) {
                    var favourite: Favourite = doc.document.toObject(Favourite::class.java)


                    if (doc.type == DocumentChange.Type.ADDED) {
                        if (favourite.userID == currentUserId.toString())
                            favouriteBtn.setImageResource(R.drawable.star_pink)
                        Log.w("star status", "set pink color")
                        toogle = true

                    } else {
                        favouriteBtn.setImageResource(R.drawable.star)
                        Log.w("star status", "set white color")
                        toogle = false
                    }


                }

            })

        //favBtn Listener
        favouriteBtn.setOnClickListener {


            var favourite: HashMap<String?, Any?> = hashMapOf<String?, Any?>("userID" to currentUserId.toString())
            var fav_Lists = User_Fav_Lists(name, currentUserId.toString(), desp_one, desp_two, desp_three, desp_four, picture)


            if (!toogle) {
                favouriteBtn.setImageResource(R.drawable.star_pink)
                Toast.makeText(this, "Favourite", Toast.LENGTH_SHORT).show()

                //  db.collection("Users").document(currentUserId).set(favourite as Map<String, Any>).addOnSuccessListener {
                Toast.makeText(this, "favourite", Toast.LENGTH_SHORT).show()


                db.collection("agriculture_db").document("$name/favourite/$currentUserId")
                    .set(favourite)
                    .addOnSuccessListener {
                        Toast.makeText(this, "added To favourite", Toast.LENGTH_SHORT).show()

                    }

                db.collection("Users").document(name).set(fav_Lists).addOnSuccessListener() {
                    //  Toast.makeText(this, "added To favourite", Toast.LENGTH_SHORT).show()
                    Log.w("User", "added to user complete")

                }
                toogle = true
            }
            else {
                favouriteBtn.setImageResource(R.drawable.star)
                Toast.makeText(this, "Unfavourite", Toast.LENGTH_SHORT).show()


                // db.collection("Users").document(currentUserId).delete().addOnSuccessListener {

                db.collection("agriculture_db").document(name)
                    .collection("favourite").document(currentUserId.toString())
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "removed from favourite", Toast.LENGTH_SHORT).show()
                        toogle = false

                    }
                db.collection("Users").document(name).delete().addOnSuccessListener() {
                    //  Toast.makeText(this, "added To favourite", Toast.LENGTH_SHORT).show()
                    Log.w("User", "deleted to user complete")


                }


            }
        }



        log()
        //listener for DeatilsDescription
        Listener_For_Buttons()
    }
    fun log() {  Log.w("name", name)
        Log.w("id", id)
        Log.w("desp_one", desp_one)
        //Log.w("favourite", favourite)
        Log.w("desp_two", desp_two)
        Log.w("desp_three", desp_three)
        Log.w("desp_four", desp_four)
    }

    fun Listener_For_Buttons() {

        val bundle:Bundle = Bundle()
        first_desp.setOnClickListener {
            val intent = Intent(this, DescriptionDeatilsActivity::class.java)
            bundle.putString("activity_name", "စိုက်ပျိုးနည်း")
            bundle.putString("num","1")
            bundle.putString("description",desp_one)
            intent.putExtras(bundle)
            startActivity(intent,bundle)
        }

        second_desp.setOnClickListener {
            val intent = Intent(this, DescriptionDeatilsActivity::class.java)
            bundle.putString("activity_name", "မြေဆီလွှာ")
            bundle.putString("num","2")
            bundle.putString("description",desp_two)
            intent.putExtras(bundle)
            startActivity(intent, bundle)
        }
        third_desp.setOnClickListener {
            val intent = Intent(this, DescriptionDeatilsActivity::class.java)
            bundle.putString("activity_name", "ပေါင်းသင်နည်း")
            bundle.putString("num","3")
            bundle.putString("description",desp_three)
            intent.putExtras(bundle)
            startActivity(intent,bundle)
        }
        fourth_desp.setOnClickListener {
            val intent = Intent(this, DescriptionDeatilsActivity::class.java)
            bundle.putString("activity_name", "ရိတ်သိမ်းချိန်")
            bundle.putString("num","4")
            bundle.putString("description",desp_four)
            intent.putExtras(bundle)
            startActivity(intent,bundle)
        }


    }
   override fun onStart() {
       super.onStart()
       bundle= intent.extras!!
       name = bundle.getString("name")
       id = bundle.getString("id")
     //  favourite = bundle.getString("favourite")
       desp_one = bundle.getString("desp_one")
       desp_two = bundle.getString("desp_two")
       desp_three = bundle.getString("desp_three")
       desp_four = bundle.getString("desp_four")
       picture = bundle.getString("agri_Picture")

      /* Log.w("name", name)
       Log.w("id", id)
       Log.w("desp_one", desp_one)
       //Log.w("favourite", favourite)
       Log.w("desp_two", desp_two)
       Log.w("desp_three", desp_three)
       Log.w("desp_four", desp_four)*/
   }

    override fun onResume() {
        super.onResume()


    }

    override fun onPause() {
        super.onPause()

    }
    fun loadPreRefrence()
    {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val state = sharedPreferences.getBoolean("state",false)
        if(state == true){
            favouriteBtn.setImageResource(R.drawable.star_pink)
        }else if (state == false){
            favouriteBtn.setImageResource(R.drawable.star)

        }
    }

   /*private fun SavePreferences() {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if(favourite == "1"){
            editor.putBoolean("state", true)
        }else if(favourite == "0")
            editor.putBoolean("state", false)

        editor.commit()   // I missed to save the data to preference here,.
    }*/
    override fun onBackPressed() {
       // SavePreferences()
        super.onBackPressed()
    }


}
