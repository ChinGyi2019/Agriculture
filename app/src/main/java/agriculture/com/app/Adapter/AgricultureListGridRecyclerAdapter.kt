package agriculture.com.app.Adapter

import agriculture.com.app.Activity.DescriptionDeatilsActivity
import agriculture.com.app.Activity.ShowAgricultureDetailsActivity
import agriculture.com.app.R
import agriculture.com.app.model.Agrictlutre
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.show_agriculture_detail_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

open class AgricultureListGridRecyclerAdapter(private val agriList:ArrayList<Agrictlutre>) : RecyclerView.Adapter<itemListViewHolder>(){
    public var listOfAgrictlure = listOf<Agrictlutre>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemListViewHolder {
        return itemListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.show_agriculture_detail_layout, parent, false))

    }

    override fun getItemCount(): Int= agriList.size

    override fun onBindViewHolder(viewHolder: itemListViewHolder, position: Int) {
        var agriculture = agriList[position]
        viewHolder.bindView(agriculture)
    }

    fun setAgriList(listOfAgrculture: List<Agrictlutre>) {
        this.listOfAgrictlure = listOfAgrculture
            notifyDataSetChanged()

    }


}
class itemListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
    //private var view: View = itemView
    private var agrictlutre:Agrictlutre ? = null
    lateinit var mContext: Context
    init {
        itemView.setOnClickListener(this)

    }

    fun bindView(agrictlutre: Agrictlutre ) {
        this.agrictlutre = agrictlutre
        itemView.textMovieTitle.text = agrictlutre.name
      //  itemView.textMovieViews.text = agrictlutre.id.toString()
     //   itemView.textReleaseDate.text = agrictlutre.desp_one

        Glide.with(itemView.context).load(agrictlutre.agri_Picture).placeholder(R.drawable.farmer).into(itemView.imageMovie)
    }

    override fun onClick(v: View?) {
        mContext = itemView.context
        Toast.makeText(mContext,"Clicked "+agrictlutre!!.name,Toast.LENGTH_LONG).show()
        val bundle: Bundle = Bundle()

        val intent :Intent = Intent(mContext,ShowAgricultureDetailsActivity::class.java)
        bundle.putString("name",agrictlutre!!.name)
        bundle.putString("id",agrictlutre!!.id)
        bundle.putString("desp_one",agrictlutre!!.desp_one)
        bundle.putString("desp_two",agrictlutre!!.desp_two)
        bundle.putString("desp_three",agrictlutre!!.desp_three)
        bundle.putString("desp_four",agrictlutre!!.desp_four)
        bundle.putString("favourite",agrictlutre!!.favourite)
        bundle.putString("agri_Picture",agrictlutre!!.agri_Picture)
        intent.putExtras(bundle)
        startActivity(mContext, intent, bundle)


    }


}