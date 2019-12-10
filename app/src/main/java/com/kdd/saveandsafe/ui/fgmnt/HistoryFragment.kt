package com.kdd.saveandsafe.ui.fgmnt


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kdd.saveandsafe.R
import com.kdd.saveandsafe.dtbse.dbcls.SandSDatabase
import com.kdd.saveandsafe.dtbse.ety.PriceEntity
import com.kdd.saveandsafe.ui.adptr.HistoryAdapter
import com.kdd.saveandsafe.ui.adptr.RecentAdapter
import com.kdd.saveandsafe.xtrs.BaseFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : BaseFragment() {

    // Variable Declarations
    lateinit var mMoveToRecentButton : TextView
    lateinit var mAddAmountTitle : TextView
    lateinit var mAmount : EditText
    lateinit var mDone : TextView
    lateinit var mRecentItemRecyclerView : RecyclerView
    lateinit var mAmountHistoryRecyclerView : RecyclerView
    lateinit var mSwipeRefreshLayoutAmount : SwipeRefreshLayout

    // Date and Time
    lateinit var mCalendar: Calendar
    lateinit var mSimpleTimeFormat : SimpleDateFormat
    lateinit var mSimpleDateFormat: SimpleDateFormat
    lateinit var mDate : String
    lateinit var mTime : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Variable Initialization
        mMoveToRecentButton = view!!.findViewById(R.id.tv_clhone_recent_page)
        mMoveToRecentButton.setOnClickListener { handleMoveToRecentButtonClick() }

        // Add Amount Title TextView Initialization
        mAddAmountTitle = view!!.findViewById(R.id.tv_clhone_add_amount)

        // EditText initialization
        mAmount = view!!.findViewById(R.id.et_clhone_amount)

        // Done Button Initialization and Click Listener
        mDone = view!!.findViewById(R.id.tv_clhone_done_button)
        mDone.setOnClickListener { handleDoneButtonClick() }

        // Date and Time Manipulations
        mCalendar = Calendar.getInstance()
        mSimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        mSimpleTimeFormat = SimpleDateFormat("hh:mm:ss", Locale.ENGLISH)
        mDate = mSimpleDateFormat.format(mCalendar.time)
        mTime = mSimpleTimeFormat.format(mCalendar.time)

        // Recent Items RecyclerView initialization
        mRecentItemRecyclerView = view!!.findViewById(R.id.rv_clhtwo)
        mRecentItemRecyclerView.layoutManager = LinearLayoutManager(view!!.context)

        // Amount History RecyclerView Initialization
        mSwipeRefreshLayoutAmount = view!!.findViewById(R.id.srl_clhthree)
        mAmountHistoryRecyclerView = view!!.findViewById(R.id.rv_clhthree)
        mAmountHistoryRecyclerView.layoutManager = LinearLayoutManager(view!!.context)

        // Listing Amount Added by swiping refresh
        mSwipeRefreshLayoutAmount.setOnRefreshListener { onRefreshListener() }

        // Listing Recent Item and amount (Recent 3)
        launch {
            context?.let {
                val recent_items = SandSDatabase(it).getItemDao().listRecent()
                val price_list = SandSDatabase(it).getPriceDao().listLastThree()
                mRecentItemRecyclerView.adapter = RecentAdapter(recent_items)
                mAmountHistoryRecyclerView.adapter = HistoryAdapter(price_list)

                // Fetching date and matching for view visibility
                try {
                    val dateToCheck = price_list.get(0)
                    val convertStringToDateFormat = dateToCheck.p_date
                    val dateConverter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(convertStringToDateFormat)
                    val dateChecker = checkDateRange(dateConverter!!)
                    if (dateChecker == true) {
                        mAddAmountTitle.visibility = View.GONE
                        mAmount.visibility = View.GONE
                        mDone.visibility = View.GONE
                    }
                    else {
                        Log.e("Hello", "World")
                    }
                }
                catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    // Custom function which will be called everytime when swipe refresh work specifically only for amount
    private fun onRefreshListener() {
        mSwipeRefreshLayoutAmount.isRefreshing = true
        launch {
            context?.let {
                val price_list = SandSDatabase(it).getPriceDao().listLastThree()
                mAmountHistoryRecyclerView.adapter = HistoryAdapter(price_list)
                mSwipeRefreshLayoutAmount.isRefreshing = false
            }
        }
    }

    // Custom function which will work whenever the done button is clicked
    private fun handleDoneButtonClick() {
        val amount = mAmount.text.toString()
        val updated_price = mAmount.text.toString()
        val savings = mAmount.text.toString()
        val date = mDate
        val time = mTime
        val total_items = 0
        launch {
            val prices = PriceEntity(amount.toIntOrNull()!!,updated_price.toIntOrNull()!!,savings.toIntOrNull()!!,total_items,time,date)
            context?.let {
                SandSDatabase(it).getPriceDao().addPrice(prices)
                mAddAmountTitle.visibility = View.GONE
                mAmount.visibility = View.GONE
                mDone.visibility = View.GONE
            }
        }
    }

    // Custom function which will be called everytime button pressed
    private fun handleMoveToRecentButtonClick() {

        Navigation.findNavController(view!!).navigate(HistoryFragmentDirections.historyToRecent())

    }

    // Custom function for checking the date range
    private fun checkDateRange(date : Date) : Boolean {
        return !(date.after(mCalendar.time))
    }
}
