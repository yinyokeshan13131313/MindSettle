package com.example.mindsettle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MoodAdapter(private val context: Context,
                  private val dataSource: ArrayList<Mood>) : BaseAdapter() {

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
        val rowView = inflater.inflate(R.layout.list_item_summary, parent, false)
        val textViewMood = rowView.findViewById(R.id.textViewMood) as TextView
        val textViewDate = rowView.findViewById(R.id.textViewDate) as TextView
        val textViewComment = rowView.findViewById(R.id.textViewComment) as TextView
        val mood = getItem(position) as Mood
        textViewMood.text = mood.userMood
        textViewDate.text = mood.dateTime
        textViewComment.text = mood.comment

        rowView.tag = position
        return rowView
    }

}