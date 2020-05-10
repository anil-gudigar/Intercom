package com.intercom.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.intercom.app.ui.viewmodel.HomeViewModel
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Created by anilkumar on 10/05/20.
 */
class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var homeViewModel: HomeViewModel? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        homeViewModel = HomeViewModel()
    }

    @Test
    fun check_read_Input_File() {
        // when asset file declared
        val inputFile = "customers.txt"

        // Then the read file form asset
        val inputFileReader =  homeViewModel?.readInputFile(ApplicationProvider.getApplicationContext(),inputFile)

        MatcherAssert.assertThat(
            inputFileReader, CoreMatchers.not(Matchers.nullValue())
        )
    }

    @Test
    fun check_read_all_customers() {
        // Given a input file
        val inputFile = "customers.txt"
        val inputFileReader =  homeViewModel?.readInputFile(ApplicationProvider.getApplicationContext(),inputFile)

        MatcherAssert.assertThat(
            inputFileReader, CoreMatchers.not(Matchers.nullValue())
        )
        // when asset file declared
        val allCustomer = homeViewModel?.loadCustomers(ApplicationProvider.getApplicationContext(),inputFileReader)

        //then read all customers
        MatcherAssert.assertThat(
            allCustomer, CoreMatchers.not(Matchers.nullValue())
        )
    }


    @Test
    fun check_build_all_invited_customers() {
        // Given a input file
        val inputFile = "customers.txt"
        val inputFileReader =  homeViewModel?.readInputFile(ApplicationProvider.getApplicationContext(),inputFile)

        MatcherAssert.assertThat(
            inputFileReader, CoreMatchers.not(Matchers.nullValue())
        )

        // when asset file declared and all customers loaded
        val allCustomer = homeViewModel?.loadCustomers(ApplicationProvider.getApplicationContext(),inputFileReader)

        MatcherAssert.assertThat(
            allCustomer, CoreMatchers.not(Matchers.nullValue())
        )

        //then build all invited customers
        val allInvitedCustomer = homeViewModel?.buildCustomerInviteList(ApplicationProvider.getApplicationContext(),allCustomer)

        MatcherAssert.assertThat(
            allInvitedCustomer, CoreMatchers.not(Matchers.nullValue())
        )
    }

    @Test
    fun check_write_to_output_File_invited_customers() {
        // Given all customers value
        val inputFile = "customers.txt"
        val inputFileReader =  homeViewModel?.readInputFile(ApplicationProvider.getApplicationContext(),inputFile)

        MatcherAssert.assertThat(
            inputFileReader, CoreMatchers.not(Matchers.nullValue())
        )

        val allCustomer = homeViewModel?.loadCustomers(ApplicationProvider.getApplicationContext(),inputFileReader)

        MatcherAssert.assertThat(
            allCustomer, CoreMatchers.not(Matchers.nullValue())
        )

        // when asset file declared and all customers loaded
        val allInvitedCustomer = homeViewModel?.buildCustomerInviteList(ApplicationProvider.getApplicationContext(),allCustomer)

        MatcherAssert.assertThat(
            allInvitedCustomer, CoreMatchers.not(Matchers.nullValue())
        )

        //then build all invited customers

        val outfileFile = homeViewModel?.writeOutputFile(ApplicationProvider.getApplicationContext(),"output.txt",allInvitedCustomer)

        MatcherAssert.assertThat(
            outfileFile, CoreMatchers.not(Matchers.nullValue())
        )
    }

    @Test
    fun check_valid_latitude_longitude_from_inputfile() {
        // Given all customers value
        val inputFile = "customers.txt"
        val inputFileReader =  homeViewModel?.readInputFile(ApplicationProvider.getApplicationContext(),inputFile)

        MatcherAssert.assertThat(
            inputFileReader, CoreMatchers.not(Matchers.nullValue())
        )

        // when asset file declared and all customers loaded
        val allCustomer = homeViewModel?.loadCustomers(ApplicationProvider.getApplicationContext(),inputFileReader)

        MatcherAssert.assertThat(
            allCustomer, CoreMatchers.not(Matchers.nullValue())
        )

        //then check valid lat long for random customer

        val customer = allCustomer?.get(Random().nextInt(allCustomer.size-1))

        val validlatlong = homeViewModel?.isValidLatLang(customer?.latitude?.toDouble(),customer?.longitude?.toDouble())?:false

        assertEquals(true,validlatlong)
    }

    @Test
    fun check_valid_latitude_longitude_from_value() {
        // Given
        // when asset file declared and all customers loaded
        val latitude = 300.0
        val longitude  = 19.0

        //then check valid lat long
        val validlatlong = homeViewModel?.isValidLatLang(latitude,longitude)?:false

        assertEquals(true,validlatlong)
    }
}