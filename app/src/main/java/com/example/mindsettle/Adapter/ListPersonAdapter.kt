package com.example.mindsettle.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import com.example.mindsettle.Model.Person
import com.example.mindsettle.R
import kotlinx.android.synthetic.main.row_layout.view.*

class ListPersonAdapter(internal var activity: Activity,
                        internal var lstPerson:List<Person>,
                        internal var editTextNickname: EditText,
                        internal var editTextEmail: EditText,
                        internal var editTextPass: EditText,
                        internal var editTextBirthYear: EditText,
                        internal var editTextCountry: EditText):BaseAdapter() {

    internal var inflater:LayoutInflater

    init{
        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView:View
         rowView = inflater.inflate(R.layout.row_layout,null)

        rowView.txt_name.text = lstPerson[position].userName.toString()
        rowView.txt_email.text = lstPerson[position].email.toString()
        rowView.txt_pass.text = lstPerson[position].password.toString()
        rowView.txt_birth.text = lstPerson[position].birthYear.toString()
        rowView.txt_country.text = lstPerson[position].country.toString()

        //Evemt
        rowView.setOnClickListener{
            editTextNickname.setText(rowView.txt_name.text.toString())
            editTextEmail.setText(rowView.txt_email.text.toString())
            editTextPass.setText(rowView.txt_pass.text.toString())
            editTextBirthYear.setText(rowView.txt_birth.text.toString())
            editTextCountry.setText(rowView.txt_country.text.toString())
        }
        return rowView
    }

    override fun getItem(position: Int): Any {
        return lstPerson[position]
    }

    override fun getItemId(position: Int): Long {
        return lstPerson[position].userName?.toLong()!!
    }

    override fun getCount(): Int {
        return lstPerson.size
    }

}