package com.intercom.app.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intercom.app.R
import com.intercom.app.ui.data.Customer


/**
 * Created by anilkumar on 10/05/20.
 */
class CustomerAdapter internal constructor(context: Context?, data: List<Customer>) : RecyclerView.Adapter<CustomerAdapter.ViewHolder?>() {
    private val mData: List<Customer> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null


    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = mData[position]
        holder.name.text = customer.name
        holder.id.text = customer.user_id.toString()
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name: TextView
        var id: TextView
        init {
            name = itemView.findViewById(R.id.customerName)
            id = itemView.findViewById(R.id.userID)
        }
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.customer_row, parent, false)
        return ViewHolder(view)
    }
}