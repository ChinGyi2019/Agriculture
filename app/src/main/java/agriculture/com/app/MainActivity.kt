package agriculture.com.app

import agriculture.com.app.Activity.StartingActivity
import agriculture.com.app.Adapter.SelectionStatePagerAdapter
import agriculture.com.app.Fragments.AboutUsFragment
import agriculture.com.app.Fragments.FavouriteFragment
import agriculture.com.app.Fragments.HomeFragment
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {
    lateinit var selectionStatePagerAdapter: SelectionStatePagerAdapter
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }

        setSupportActionBar(main_tool_bar)
        supportActionBar!!.setTitle("Site Pyo")
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        main_tool_bar.setTitleTextColor(Color.parseColor("#FFFFFF"))


        selectionStatePagerAdapter = SelectionStatePagerAdapter(supportFragmentManager)

        setUpViewPager(container)


        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.action_home -> {
                    setViewPager(0)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_favourite -> {
                    setViewPager(1)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.action_about_us -> {
                    setViewPager(2)
                    return@OnNavigationItemSelectedListener true
                }


            }
            false

        }

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun setUpViewPager(viewPager: ViewPager) {

        selectionStatePagerAdapter = SelectionStatePagerAdapter(supportFragmentManager)
        selectionStatePagerAdapter.addFragment(HomeFragment())
        selectionStatePagerAdapter.addFragment(FavouriteFragment())
        selectionStatePagerAdapter.addFragment(AboutUsFragment())

        viewPager.adapter = selectionStatePagerAdapter

    }

    fun setViewPager(fragmentNumber: Int) {
        container.setCurrentItem(fragmentNumber)

    }

    fun sentToStart() {
        val intent = Intent(this@MainActivity, StartingActivity::class.java)
        startActivity(intent)
        finish()
    }

   /* override fun onStart() {
        super.onStart()

        var mCurrentUser: FirebaseUser? = mAuth.currentUser

        if (mCurrentUser == null) {
            sentToStart()
        }
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.logout_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.mainLgoutbtn) {
            FirebaseAuth.getInstance().signOut()
            sentToStart()
        }

        return true
    }
}



