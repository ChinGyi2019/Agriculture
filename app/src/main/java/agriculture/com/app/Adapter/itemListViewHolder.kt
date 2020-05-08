package agriculture.com.app.Adapter

import agriculture.com.app.Activity.RecyclerViewGridLayoutActivity
import agriculture.com.app.R
import agriculture.com.app.model.Agrictlutre
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.show_agriculture_detail_layout.view.*

class itemListViewHolders (itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
    private var view: View = itemView
    lateinit var mContext: Context
    init {
        view.setOnClickListener(this)
    }

    fun bindView(agrictlutre: Agrictlutre ) {
        itemView.textMovieTitle.text = agrictlutre.name
      //  itemView.textMovieViews.text = agrictlutre.id.toString()
       // itemView.textReleaseDate.text = agrictlutre.desp_one

        Glide.with(itemView.context).load(agrictlutre.agri_Picture!!).placeholder(R.drawable.farmer).into(itemView.imageMovie)
    }

    override fun onClick(v: View?) {
        Toast.makeText(mContext,"Clicked ",Toast.LENGTH_LONG).show()
    }

}