package com.example.sqlitecrud

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter(val context: Activity,val dataId: Array<String>,val dataName: Array<String>,val dataEmail: Array<String>,val  dataAge: Array<String>) :
    ArrayAdapter<String>(context,R.layout.custom_list,dataName) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val idText = rowView.findViewById(R.id.textViewId) as TextView
        val nameText = rowView.findViewById(R.id.textViewName) as TextView
        val emailText = rowView.findViewById(R.id.textViewEmail) as TextView
        val textViewAge = rowView.findViewById(R.id.textViewAge) as TextView

        idText.text = "Id: ${dataId[position]}"
        nameText.text = "Name: ${dataName[position]}"
        emailText.text = "Email: ${dataEmail[position]}"
        textViewAge.text = "Email: ${dataAge[position]}"
        return rowView
    }
}
