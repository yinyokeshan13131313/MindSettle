package com.example.mindsettle


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_goals.*

/**
 * A simple [Fragment] subclass.
 */
class GoalsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goals, container, false)

        val activity = activity as Main2BottomActivity

        val toolbar: androidx.appcompat.widget.Toolbar = view.findViewById(R.id.toolbar)
        //these lines set the toolbar title
        activity.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        //activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.add_icon)
        //activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //activity.setSupportActionBar()
        //these lines st our adapter

        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
        val fragmentAdapter = MyPagerAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdapter

        //this line view our tabLayout with
        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        return view
    }

    //override fun onCreate(savedInstanceState: Bundle?) {
      //  super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)
    //}

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        //val activity = activity as Main2BottomActivity
        //activity.menuInflater.inflate(R.menu.menu_add, menu)
        inflater?.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return(when(item?.itemId){
            R.id.addGoal->{
            val activity = activity as Main2BottomActivity
            var token = activity.getSharedPreferences("username", Context.MODE_PRIVATE)
            var username = token.getString("loginusername", "")


            val intent = Intent(activity, NewGoalActivity::class.java)
            intent.putExtra("username", username)
            activity.startActivity(intent)

            //activity.intent(this, NewGoalActivity::class.java)
            //activity.intent.putExtra("username", username)
            //val intent = activity.intent
            //startActivity(intent)
            true
            }
            else ->
            super.onOptionsItemSelected(item)
            })
        }

        //val activity = activity as Main2BottomActivity
       //var token = activity.getSharedPreferences("username", Context.MODE_PRIVATE)
        //var username = token.getString("loginusername", "")
        //val intent = activity.intent(this@GoalsFragment, NewGoalActivity::class.java)
        //val intent = activity.intent(this, Main2BottomActivity::class.java)

        //intent.putExtra("username", username)
        //startActivity(intent)
        //return super.onOptionsItemSelected(item)
    }

