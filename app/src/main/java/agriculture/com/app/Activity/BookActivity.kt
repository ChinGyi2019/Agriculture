package agriculture.com.app.Activity

import agriculture.com.app.Adapter.BookAdapter
import agriculture.com.app.R
import agriculture.com.app.model.Book
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_book.*


class BookActivity : AppCompatActivity() {

    var key1:String = ""
    var key2:String ? = ""
    private lateinit var linearLayoutManager: LinearLayoutManager
    private final var TAG = "TAG"
    private lateinit var adapter:BookAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var bookList: ArrayList<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        //ActionBar
        setSupportActionBar(book_tool_bar)
        // supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        book_tool_bar.setTitleTextColor(Color.parseColor("#FFFFFF"))
        // back arrow for white
        val upArrow = ContextCompat.getDrawable(this, R.drawable.back_arrow)
        upArrow!!.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP)

        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        key1 = intent.getStringExtra("key1").toString()
        key2 = intent.getStringExtra("key2").toString()

        supportActionBar!!.title = key1.toString()

        linearLayoutManager = LinearLayoutManager(this)
        recyclerViewBook.setHasFixedSize(true)
        recyclerViewBook.layoutManager = linearLayoutManager

        bookList = ArrayList<Book>()

        db = FirebaseFirestore.getInstance()
        /* val settings = FirebaseFirestoreSettings.Builder()
             .setPersistenceEnabled(true)
             .build()
         db.firestoreSettings = settings */

        db.collection(key2.toString()).orderBy("id")
            .addSnapshotListener(EventListener { documentSnapshots, firebaseFirestoreException ->
                if(firebaseFirestoreException != null){
                    Log.w(TAG, "Error" + firebaseFirestoreException.message)

                }
                for (doc in documentSnapshots!!.getDocumentChanges())
                {
                    if(doc.type == DocumentChange.Type.ADDED){
                        var book:Book = doc.document.toObject(Book::class.java)
                        bookList.add(book)
                        adapter.notifyDataSetChanged()

                       // progressBar.setVisibility(View.GONE);
                    }
                }

            })

       adapter = BookAdapter(bookList,baseContext)
        recyclerViewBook.adapter = adapter

    }
}
