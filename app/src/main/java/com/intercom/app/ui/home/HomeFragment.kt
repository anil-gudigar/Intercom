package com.intercom.app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intercom.app.R
import com.intercom.app.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private var mContext: Context? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mContext = activity?.applicationContext
        val rootView = inflater.inflate(R.layout.home_fragment, container, false)
        recyclerView = rootView.findViewById(R.id.customerList)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        mContext?.let {
            val bufferReader = viewModel.readInputFile(it,"customers.txt")

            val allCustomers  = viewModel.loadCustomers(it,bufferReader)

            val invitee_list = viewModel.buildCustomerInviteList(it, allCustomers)

            val adapter = CustomerAdapter(mContext,invitee_list)
            recyclerView.adapter = adapter

            val outfile = viewModel.writeOutputFile(it,"output.txt",invitee_list)

            output_file_path.text = outfile
        }
    }

}
