package agriculture.com.app.Adapter

import agriculture.com.app.Permission.ManagePermissions
import agriculture.com.app.R
import agriculture.com.app.model.Book
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.book_detail_layout.view.*
import kotlinx.android.synthetic.main.show_agriculture_detail_layout.view.*
import java.security.Permission
import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.app.Service
import android.net.Uri
import android.os.Environment
import android.support.v4.app.ActivityCompat

private val PermissionsRequestCode = 123
private lateinit var managePermissions: ManagePermissions

lateinit var book:ArrayList<Book>
lateinit var context: Context


class BookAdapter(private val bookList:ArrayList<Book>,var context: Context)
    : RecyclerView.Adapter<BookHolder>() {
    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val book = bookList.get(position)
        holder.bindView(book)

    }

    init{
        this.context = context
    }

    //override fun onBindViewHolder(holder: ViewHolder, position: Int) {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val inflatedView = parent.inflate(R.layout.book_detail_layout, false)
        return BookHolder(inflatedView)

    }

    override fun getItemCount() = bookList.size
}

class BookHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener {
    private var view: View = v
    public var book: Book? = null
    val STORAGE_PERMISSION_CODE:Int = 1000;

    init {
        v.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d("RecyclerView", "CLICK!")

        val context:Context = itemView.context
        val bundle: Bundle = Bundle()


        val list = listOf<String>(
           // Manifest.permission.CAMERA,
           // Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE
          //  Manifest.permission.SEND_SMS,
           // Manifest.permission.READ_CALENDAR
        )

        // Initialize a new instance of ManagePermissions class
        managePermissions = ManagePermissions(context as Activity,list,PermissionsRequestCode)

        //*val intent : Intent = Intent(context,ShowSubjectDetailActivity::class.java)
        /* bundle.putString("sub_name",subject!!.sub_name)
        bundle.putString("sub_code",subject!!.sub_code)
        bundle.putString("description",subject!!.description)
        //bundle.putString("sub_name",subject!!.sub_name)
        intent.putExtras(bundle)
        ContextCompat.startActivity(context, intent, bundle)
*/
      //  Toast.makeText(context, "hello book", Toast.LENGTH_SHORT).show()

        val builder = AlertDialog.Builder(context)
        // Set the alert dialog title
        builder.setTitle("${book?.name}")
        // Display a message on alert dialog
        builder.setMessage("Are you sure want to download ${book?.name} ?")
        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("Download"){dialog, which ->
            // Do something when user press the positive button
            Toast.makeText(context,"Ok, you are downloading ${book?.name}.",Toast.LENGTH_SHORT).show()
            // Change the app background color
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(context.applicationContext as Activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)

            }else{
                //permission already granted, perform download
                    startDownloading()
                }
            }
            else{


            }

        }

            //startDownloading()
          //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
              //  managePermissions.checkPermissions()

            //}else{
  //              startDownloading()
//            }
     //   }
        // Display a negative button on alert dialog
        builder.setNegativeButton("cancel"){dialog,which ->
            Toast.makeText(context,"You are not agree.",Toast.LENGTH_SHORT).show()
        }
        // Display a neutral button on alert dialog
      /*  builder.setNeutralButton("Cancel"){_,_ ->
            Toast.makeText(context,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
        }*/
        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()
        // Display the alert dialog on app interface
        dialog.show()

    }
      fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                                      grantResults: IntArray) {
        when (requestCode) {
            PermissionsRequestCode ->{
                val isPermissionsGranted = managePermissions
                    .processPermissionsResult(requestCode,permissions,grantResults)

                if(isPermissionsGranted){
                    // Do the task now
                   // toast("Permissions granted.")
                    startDownloading()
                    Toast.makeText(context,"Permissions granted.", Toast.LENGTH_SHORT).show()
                }else{
                   // toast("Permissions denied.")
                        Toast.makeText(context,"Permissions denied.", Toast.LENGTH_SHORT).show()


                }
                return
            }
        }

}

    private fun startDownloading() {
        Toast.makeText(context,"download.", Toast.LENGTH_SHORT).show()

        var url = book?.download_Link.toString()
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Download")
        request.setDescription("The file is downloading...")

        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"${book?.name}")
        //get download service
        val manager = Service.DOWNLOAD_SERVICE as DownloadManager
        manager.enqueue(request)
    }

    fun Context.toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


    companion object {
        //5
        private val Subject_Key = "Subject"
    }

    fun bindView(book: Book) {
        this.book = book
        Glide.with(view.context).load(book.picture)
            .placeholder(R.drawable.farmer)
            .into(view.bookImage)

        view.book_name.text = book.name
        view.book_Description.text = book.description

    }
}

