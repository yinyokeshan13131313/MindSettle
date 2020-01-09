package com.example.mindsettle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPagerAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position){
            0->{
                FragmentActive()
            }
            else -> return FragmentCompleteGoals()
        }

        //is to set tab position
    }

    override fun getCount(): Int {
        return 2
        //this method will return back_two tabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0->"Active"
            else -> return "Complete"
        }

        //this set our tab title
    }
}