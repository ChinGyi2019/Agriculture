package agriculture.com.app.Activity

import agriculture.com.app.Adapter.AgricultureListGridRecyclerAdapter
import agriculture.com.app.R
import agriculture.com.app.RecyclerView_Helper.GridItemDecoration
import agriculture.com.app.model.Agrictlutre
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_second.*
import android.content.SharedPreferences
import android.content.Context
import android.util.Log
import android.view.Menu
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.miguelcatalan.materialsearchview.MaterialSearchView


class RecyclerViewGridLayoutActivity : SecondActivity() {
    var key1:String = ""
    var key2:String ? = ""
    val Tag:String = ""
    private lateinit var db: FirebaseFirestore
    private lateinit var agri_List: ArrayList<Agrictlutre>
    private lateinit var  agriListAdapter: AgricultureListGridRecyclerAdapter
    lateinit var  mRef:CollectionReference
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //ActionBar
        setSupportActionBar(second_tool_bar)
        // supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        second_tool_bar.setTitleTextColor(Color.parseColor("#FFFFFF"))
        // back arrow for white
        val upArrow = ContextCompat.getDrawable(this, R.drawable.back_arrow)
        upArrow!!.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP)

        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        key1 = intent.getStringExtra("key1").toString()
        key2 = intent.getStringExtra("key2").toString()
        supportActionBar!!.title = key1.toString()

        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        db.setFirestoreSettings(settings)
        db.enableNetwork()

         mRef =db.collection("agriculture_db")






       materialsearchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {

            }

            override fun onSearchViewClosed() {
               //
                //firebaseSearch()
            }
        })

        materialsearchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                firebaseSearch()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && !newText.isEmpty()) {

                    firebaseSearch(newText)

                } else {
                    firebaseSearch()
                }
                return true
            }

        })


        initView()
        }











    private fun firebaseSearch() {
        agri_List = ArrayList<Agrictlutre>()
        agriListAdapter = AgricultureListGridRecyclerAdapter(agri_List)
       mRef.whereEqualTo("v_f",key2!!).orderBy("id")
           .addSnapshotListener(EventListener { documentSnapshots, firebaseFirestoreException ->
               if(firebaseFirestoreException != null){
                   // Log.w(TAG, "Error" + firebaseFirestoreException.message)

               }
               for (doc in documentSnapshots!!.getDocumentChanges())
               {
                   var agrictlutre:Agrictlutre = doc.document.toObject(Agrictlutre::class.java)

                   if(doc.type == DocumentChange.Type.ADDED){
                       agri_List.add(agrictlutre)
                       //  agriListAdapter.notifyDataSetChanged()


                   }

                   agriListAdapter.notifyDataSetChanged()
               }

           })
        recyclerViewAgriculture.adapter = agriListAdapter
        agriListAdapter.setAgriList(agri_List)

    }

    private fun firebaseSearch(text:String){
        agri_List = ArrayList<Agrictlutre>()
        agriListAdapter = AgricultureListGridRecyclerAdapter(agri_List)
        val searchText = text.toLowerCase().toString()
        run{

          mRef.whereEqualTo("v_f",key2!!).orderBy("name").startAt(searchText).endAt(searchText + "\uf8ff")
            .addSnapshotListener(EventListener { documentSnapshots, firebaseFirestoreException ->
                if(firebaseFirestoreException != null){
                    // Log.w(TAG, "Error" + firebaseFirestoreException.message)

                }
                for (doc in documentSnapshots!!.getDocumentChanges())
                {
                    var agrictlutre:Agrictlutre = doc.document.toObject(Agrictlutre::class.java)

                    if(doc.type == DocumentChange.Type.ADDED){
                        agri_List.add(agrictlutre)
                        //  agriListAdapter.notifyDataSetChanged()


                    }

                    agriListAdapter.notifyDataSetChanged()
                }

            })


           /* recyclerViewAgriculture.adapter = agriListAdapter */

           /* mRef.orderBy("name").startAt(searchText).endAt(searchText + "\uf8ff")
                .get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(Tag, "${document.id} => ${document.data}")
                        var agrictlutre:Agrictlutre = document.toObject(Agrictlutre::class.java)
                        agri_List.add(agrictlutre)
                    }
                    agriListAdapter.notifyDataSetChanged()


                }
                .addOnFailureListener { exception ->
                    Log.d(Tag, "get failed with ", exception)*/
                }
            recyclerViewAgriculture.adapter = agriListAdapter
            agriListAdapter.setAgriList(agri_List)




    }


    private fun initView() {

        recyclerViewAgriculture.layoutManager = GridLayoutManager(this,3)

        //This will for default android divider
        recyclerViewAgriculture.addItemDecoration(GridItemDecoration(10, 2))
      //  recyclerViewAgriculture.adapter = agriListAdapter


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        materialsearchView.setMenuItem(item)
        return true
    }

    override fun onStart() {
        super.onStart()
        agri_List = ArrayList<Agrictlutre>()
        agriListAdapter = AgricultureListGridRecyclerAdapter(agri_List)
        mRef.whereEqualTo("v_f",key2!!).orderBy("id")
            .addSnapshotListener(EventListener { documentSnapshots, firebaseFirestoreException ->
                if(firebaseFirestoreException != null){
                    // Log.w(TAG, "Error" + firebaseFirestoreException.message)

                }
                for (doc in documentSnapshots!!.getDocumentChanges())
                {
                    var agrictlutre:Agrictlutre = doc.document.toObject(Agrictlutre::class.java)

                    if(doc.type == DocumentChange.Type.ADDED){
                        agri_List.add(agrictlutre)
                        //  agriListAdapter.notifyDataSetChanged()


                    }

                    agriListAdapter.notifyDataSetChanged()
                }

            })
        recyclerViewAgriculture.adapter = agriListAdapter
        agriListAdapter.setAgriList(agri_List)
    }
    }
 /*   private fun generateDummyData(): List<Agrictlutre> {
        val listOfMovie = mutableListOf<Agrictlutre>()

        var movieModel = Agrictlutre(1, "Avengers", "Haah One", R.drawable.one)
        listOfMovie.add(movieModel)

        movieModel = Agrictlutre(2, "Mango", "Haah One", R.drawable.two)
        listOfMovie.add(movieModel)

        movieModel = Agrictlutre(3, "Banana", "Haah One", R.drawable.three)
        listOfMovie.add(movieModel)

        movieModel = Agrictlutre(4, "Grape", "Haah One", R.drawable.four)
        listOfMovie.add(movieModel)

        movieModel = Agrictlutre(5, "Tomatos", "Haah One", R.drawable.five)
        listOfMovie.add(movieModel)

        movieModel = Agrictlutre(6, "Watermellon", "Haah One", R.drawable.six)
        listOfMovie.add(movieModel)

        movieModel = Agrictlutre(7, "Lemon", "Haah One", R.drawable.seven)
        listOfMovie.add(movieModel)

        movieModel = Agrictlutre(8, "Avengers", "Haah One", R.drawable.eight)
        listOfMovie.add(movieModel)

        return listOfMovie
    }*/





