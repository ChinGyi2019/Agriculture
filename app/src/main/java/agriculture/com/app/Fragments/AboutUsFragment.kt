package agriculture.com.app.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import agriculture.com.app.R
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_about_us.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AboutUsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AboutUsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AboutUsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
         val view =inflater.inflate(R.layout.fragment_about_us, container, false)
        //Chun Htet
        view.phone_btn_C.setOnClickListener{
            callPhone("09768218885")
        }
       view.facebook_btn_C.setOnClickListener {
           val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/chunhtet.naing.7"))
           startActivity(i)
       }

        //Phoe Nyan
        view.phone_btn_P.setOnClickListener{
            callPhone("09962536327")
        }
        view.facebook_btn_P.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/phoe.nyan.92"))
            startActivity(i)
        }

        //Zaw Myo
        view.phone_btn_Z.setOnClickListener{
            callPhone("09955540172")
        }
        view.facebook_btn_Z.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100010919162247"))
            startActivity(i)
        }

        //Aung Ko
        view.phone_btn_A.setOnClickListener{
            callPhone("09797761093")
        }
        view.facebook_btn_A.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100008823348192"))
            startActivity(i)
        }
        return view


    }
    fun onClick( view:View){



    }
  fun callPhone(ph:String){

      val i = Intent(Intent.ACTION_CALL)
      i.data = Uri.parse("tel:" + ph + "")

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
              if (checkSelfPermission(context!!,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                  Toast.makeText(context, "call", Toast.LENGTH_LONG).show()
                  requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 10)

                  return
              }

              startActivity(i)
          }


      }


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
         * @return A new instance of fragment AboutUsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutUsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
