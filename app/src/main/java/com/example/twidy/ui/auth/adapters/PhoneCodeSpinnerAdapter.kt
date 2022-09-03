package com.example.twidy.ui.auth.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.twidy.R
import com.example.twidy.data.response.Country
import com.example.twidy.data.response.Location

class PhoneCodeSpinnerAdapter(ctx: Context,var res: Int,var list: ArrayList<Country> ) : ArrayAdapter<Country>(ctx,res,list) {
    private val inflater = LayoutInflater.from(ctx)
    override fun getItem(position: Int): Country? {
        return list[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(res,parent,false)
        val countryCodeTextView = view.findViewById<TextView>(R.id.country_code_textview)
        countryCodeTextView.text = "+"+list[position].phonecode.toString()
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        /*val view = inflater.inflate(R.layout.code_spinner_dropdownitem,parent,false)
        val countryNameTextView = view.findViewById<TextView>(R.id.country_name_textview)
        val countryCodeTextView = view.findViewById<TextView>(R.id.country_code_textview)
        countryNameTextView.text = list[position].name
        countryCodeTextView.text = list[position].phonecode.toString()
        return view*/
        val view = inflater.inflate(R.layout.code_spinner_item_dropdown,parent,false)
        val countryCodeTextView = view.findViewById<TextView>(R.id.country_code_textview)
        countryCodeTextView.text = "+"+list[position].phonecode.toString()
        return view
    }
    fun getItemPosition(location: Location): Int{
        for(i in list.indices){
            val item = getItem(i)
            if(item?.name==location.name&&item.phonecode==location.phonecode)
                return i
        }
        return 0
    }

}