package agriculture.com.app.Fragments

import agriculture.com.app.Adapter.AgricultureListGridRecyclerAdapter
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import agriculture.com.app.R
import agriculture.com.app.RecyclerView_Helper.GridItemDecoration
import agriculture.com.app.model.Agrictlutre
import agriculture.com.app.model.User_Fav_Lists
import android.support.v7.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.android.synthetic.main.fragment_favourite.view.*




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FavouriteFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FavouriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FavouriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var db:FirebaseFirestore
    private lateinit var agri_List: ArrayList<Agrictlutre>
    private lateinit var  agriListAdapter: AgricultureListGridRecyclerAdapter
    lateinit var mAuth: FirebaseAuth
    var currentUserId:String? =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

           // mAuth = FirebaseAuth.getInstance()


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val  view = inflater.inflate(R.layout.fragment_favourite, container, false)

        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        db.setFirestoreSettings(settings)

     // var currentUser: FirebaseUser? = mAuth.currentUser
          //  currentUserId = mAuth.currentUser!!.uid
        mAuth = FirebaseAuth.getInstance()
        var mCurrentUser: FirebaseUser? = mAuth.currentUser!!
            currentUserId = mCurrentUser!!.uid

        agri_List = ArrayList<Agrictlutre>()
        agriListAdapter = AgricultureListGridRecyclerAdapter(agri_List)



        db.collection("Users").whereEqualTo("id",currentUserId)
            .addSnapshotListener(EventListener { documentSnapshots, firebaseFirestoreException ->
                if (firebaseFirestoreException != null){

                }
                for (doc in documentSnapshots!!.getDocumentChanges()){

                    var user_Fav_Lists =doc.document.toObject(Agrictlutre::class.java)
                    if(doc.type == DocumentChange.Type.ADDED){

                    agri_List.add(user_Fav_Lists)
                }else if(doc.type == DocumentChange.Type.REMOVED){
                agri_List.remove(user_Fav_Lists)
            }
                for (cls in agri_List) {
                    if (cls.favourite == "0" ) {
                        agri_List.remove(cls)
                    }
                }
                agriListAdapter.notifyDataSetChanged()

            }
            })

      /*  db.collection("agriculture_db").whereEqualTo("favourite","1").orderBy("id")
            .addSnapshotListener(EventListener { documentSnapshots, firebaseFirestoreException ->
                if(firebaseFirestoreException != null){
                    // Log.w(TAG, "Error" + firebaseFirestoreException.message)

                }
                for (doc in documentSnapshots!!.getDocumentChanges())
                {
                    var agrictlutre:Agrictlutre = doc.document.toObject(Agrictlutre::class.java)
                    if(doc.type == DocumentChange.Type.ADDED){
                      //  var agrictlutre:Agrictlutre = doc.document.toObject(Agrictlutre::class.java)
                        agri_List.add(agrictlutre)



                    }else if(doc.type == DocumentChange.Type.REMOVED){
                    agri_List.remove(agrictlutre)
                }
                    for (cls in agri_List) {
                        if (cls.favourite == "0" ) {
                            agri_List.remove(cls)
                        }
                    }
                    agriListAdapter.notifyDataSetChanged()

                }


            })*/

        view.recyclerViewFavourite.layoutManager = GridLayoutManager(activity, 3)

        //This will for default android divider
        view.recyclerViewFavourite.addItemDecoration(GridItemDecoration(10, 2))
        view.recyclerViewFavourite.adapter = agriListAdapter

        agriListAdapter.setAgriList(agri_List)
        agriListAdapter.notifyDataSetChanged();
        //pullToRefresh.setRefreshing(false);


        return view
    }
    private fun initView() {


    }
    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavouriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    /* override fun onStart() {
         super.onStart()
         mAuth = FirebaseAuth.getInstance()
          var mCurrentUser: FirebaseUser? = mAuth.currentUser

         if (mCurrentUser != null) {
           currentUserId = mAuth.currentUser!!.uid
         }
     }
     override fun onResume() {
         super.onResume()
         view?.recyclerViewFavourite?.layoutManager = GridLayoutManager(activity,2)

         //This will for default android divider
         view?.recyclerViewFavourite?.addItemDecoration(GridItemDecoration(10, 2))
         view?.recyclerViewFavourite?.adapter = agriListAdapter

         agriListAdapter.setMovieList(agri_List)

     }*/
}
