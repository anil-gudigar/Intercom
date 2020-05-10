package com.intercom.app.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.intercom.app.ui.data.Customer
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.lang.Math.*


class HomeViewModel : ViewModel() {
    var PI_RAD = Math.PI / 180.0
    val centerLatitude = 53.339428
    val centerLongitude = -6.257664

    fun readInputFile(context: Context, input: String): BufferedReader? {
        return try {
            context.assets.open(input).bufferedReader()
        } catch (ex: FileNotFoundException) {
            null
        }
    }

    fun loadCustomers(context: Context, reader: BufferedReader?): ArrayList<Customer>?{
        var customerList: ArrayList<Customer>? = null
        reader?.let {
            val iterator = it.lineSequence().iterator()
            customerList = ArrayList()
            while (iterator.hasNext()) {
                val customer = iterator.next()
                customerList?.add(Gson().fromJson(customer, Customer::class.java))
            }
            it.close()
        }
        return customerList
    }

    fun buildCustomerInviteList(context: Context,allCustomers:ArrayList<Customer>?): MutableList<Customer> {
        var invitedCustomerList: MutableList<Customer> = mutableListOf<Customer>()
        allCustomers?.let {
            for (customer in it) {
                if (isValidLatLang(customer.latitude.toDouble(), customer.longitude.toDouble())) {
                    val distanceInKM = greatCircleInKilometers(
                        customer.latitude.toDouble(),
                        customer.longitude.toDouble(),
                        centerLatitude,
                        centerLongitude
                    )
                    if (distanceInKM <= 100) {
                        invitedCustomerList.add(customer)
                    }
                } else {
                    Log.i("Anil", "invlide Lat Long :" + customer.toString())
                }
            }
            return invitedCustomerList.sortedWith(compareBy({ it.user_id })) as MutableList<Customer>
        }
        return invitedCustomerList
    }

    fun writeOutputFile(mcoContext: Context, sFileName: String?, customerList: MutableList<Customer>?): String{
        var outfilePath =""
        val file = File(mcoContext.filesDir, "intercom")
        if (!file.exists()) {
            file.mkdir()
        }
        try {
            val outfile = File(file, sFileName)
            val writer = FileWriter(outfile)
            customerList?.let {
                for (customer in it){
                    writer.append(customer.toString())
                    writer.append('\n')
                }
            }
            writer.flush()
            writer.close()
            outfilePath = outfile.getAbsolutePath()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return outfilePath
    }

    /**
     * Use Great Circle distance formula to calculate distance between 2 coordinates in kilometers.
     */
    private fun greatCircleInKilometers(
        lat1: Double,
        long1: Double,
        lat2: Double,
        long2: Double
    ): Double {
        val phi1 = lat1 * PI_RAD
        val phi2 = lat2 * PI_RAD
        val lam1 = long1 * PI_RAD
        val lam2 = long2 * PI_RAD
        return 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1))
    }

    /**
     * validate latitude / longitude
     */

    fun isValidLatLang(latitude: Double?, longitude: Double?): Boolean {
        return latitude?.toInt() in -90 until 90 && longitude?.toInt() in -180 until 180
    }

}
