package com.example.mindsettle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class GratitudeAdapter(private val context: Context,
                       private val dataSource: ArrayList<Gratitude>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    //1
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.list_item_gratitude, parent, false)
        val textViewGratitudeOne = rowView.findViewById(R.id.textViewGratitudeOne) as TextView
        val textViewGratitudeTwo = rowView.findViewById(R.id.textViewGratitudeTwo) as TextView
        val textViewGratitudeThree = rowView.findViewById(R.id.textViewGratitudeThree) as TextView
        val textViewDate = rowView.findViewById(R.id.textViewDate) as TextView

        val gratitude = getItem(position) as Gratitude
        textViewGratitudeOne.text = gratitude.userGratitudeOne
        textViewGratitudeTwo.text = gratitude.userGratitudeTwo
        textViewGratitudeThree.text = gratitude.userGratitudeThree
        textViewDate.text = gratitude.dateTime

        rowView.tag = position
        return rowView
    }

}