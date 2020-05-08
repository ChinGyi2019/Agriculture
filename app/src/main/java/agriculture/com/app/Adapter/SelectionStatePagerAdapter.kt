package agriculture.com.app.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

var mFragmentList:ArrayList<Fragment> = ArrayList<Fragment>()
var mTitleList:ArrayList<String> = ArrayList<String>()
class SelectionStatePagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)

    }

    override fun getCount(): Int {
        return  mFragmentList.size
    }

    fun addFragment( fragment :Fragment){
        mFragmentList.add(fragment)

    }
}