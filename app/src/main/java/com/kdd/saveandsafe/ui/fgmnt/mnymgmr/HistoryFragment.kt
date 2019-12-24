package com.kdd.saveandsafe.ui.fgmnt.mnymgmr


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
import com.google.android.material.snackbar.Snackbar
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

    // Views Declarations
    lateinit var mMoveToRecentButton : TextView
    lateinit var mAddAmountTitle : TextView
    lateinit var mAmount : EditText
    lateinit var mDone : TextView
    lateinit var mRecentItemRecyclerView : RecyclerView
    lateinit var mAmountHistoryRecyclerView : RecyclerView
    lateinit var mSwipeRefreshLayoutAmount : SwipeRefreshLayout
    lateinit var mSnackbar : Snackbar

    // Date and Time
    lateinit var mCalendar : Calendar
    lateinit var mCalenderForExpiry : Calendar
    lateinit var mSimpleTimeFormat : SimpleDateFormat
    lateinit var mSimpleDateFormat: SimpleDateFormat
    lateinit var mDateInString : String
    lateinit var mExpiryDateInString : String
    lateinit var mTimeInString : String
    lateinit var mCalculateExpiryDate : Date
    lateinit var mDateStringToDateConversion : Date
    lateinit var mExpiryDateStringToExpiryDateConversion : Date

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Views Initialization
        mMoveToRecentButton = view!!.findViewById(R.id.tv_clhone_recent_page)
        mAddAmountTitle = view!!.findViewById(R.id.tv_clhone_add_amount)
        mAmount = view!!.findViewById(R.id.et_clhone_amount)
        mDone = view!!.findViewById(R.id.tv_clhone_done_button)
        mRecentItemRecyclerView = view!!.findViewById(R.id.rv_clhtwo)
        mSwipeRefreshLayoutAmount = view!!.findViewById(R.id.srl_clhthree)
        mAmountHistoryRecyclerView = view!!.findViewById(R.id.rv_clhthree)

        // Button Click Handlers
        mMoveToRecentButton.setOnClickListener { mMoveToRecentButtonHandler() }
        mDone.setOnClickListener { mDoneHandler() }

        // Recent Item Added RecyclerView
        mRecentItemRecyclerView.layoutManager = LinearLayoutManager(view!!.context)
        loadRecentAddedItem()

        // Amount History RecyclerView
        mAmountHistoryRecyclerView.layoutManager = LinearLayoutManager(view!!.context)
        mSwipeRefreshLayoutAmount.setOnRefreshListener { loadAmountHistory() }
        loadAmountHistory()

        // Date and Time
        mCalendar = Calendar.getInstance()
        mSimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        mSimpleTimeFormat = SimpleDateFormat("hh:mm:ss", Locale.ENGLISH)

        // Calculating Expiry Date
        mCalculateExpiryDate = Date()
        mCalenderForExpiry = Calendar.getInstance()
        mCalenderForExpiry.time = mCalculateExpiryDate
        mCalenderForExpiry.add(Calendar.DATE, 30)
        mCalculateExpiryDate = mCalenderForExpiry.time

        // Formatting Dates
        mDateInString = mSimpleDateFormat.format(mCalendar.time)
        mTimeInString = mSimpleTimeFormat.format(mCalendar.time)
        mExpiryDateInString = mSimpleDateFormat.format(mCalculateExpiryDate)

    }

    private fun mMoveToRecentButtonHandler() {
        val action  = HistoryFragmentDirections.historyToRecent()
        Navigation.findNavController(view!!).navigate(action)
    }

    private fun mDoneHandler() {
        val amount = mAmount.text.toString()
        val uamount = mAmount.text.toString()
        val savings = 0
        val total = 0
        val time = mTimeInString
        val date = mDateInString
        val edate = mExpiryDateInString

        launch {
            context?.let {
                if(amount.isEmpty() || uamount.isEmpty()) {
                    mSnackbar = Snackbar.make(view!!, "Please Enter Amount", Snackbar.LENGTH_LONG)
                    mSnackbar.show()
                }
                else {
                    val price = PriceEntity(amount.toIntOrNull()!!, uamount.toIntOrNull()!!, savings, total, time, date, edate)
                    SandSDatabase(it).getPriceDao().addPrice(price)
                    mSnackbar = Snackbar.make(view!!, "Added Successfully", Snackbar.LENGTH_LONG)
                    mSnackbar.show()
                    mAddAmountTitle.visibility = View.GONE
                    mAmount.visibility = View.GONE
                    mDone.visibility = View.GONE
                }
            }
        }
    }

    private fun loadAmountHistory() {
        mSwipeRefreshLayoutAmount.isRefreshing = true
        launch {
            context?.let {
                val priceLoad = SandSDatabase(it).getPriceDao().listPrice()
                mAmountHistoryRecyclerView.adapter = HistoryAdapter(priceLoad)
                mSwipeRefreshLayoutAmount.isRefreshing = false
                whenToShow()
            }
        }
    }

    private fun loadRecentAddedItem() {
        launch {
            context?.let {
                val itemLoad = SandSDatabase(it).getItemDao().listRecent()
                mRecentItemRecyclerView.adapter = RecentAdapter(itemLoad)
                whenToShow()
            }
        }
    }

    private fun whenToShow() {
        launch {
            context?.let {
                try {
                    val priceLoad = SandSDatabase(it).getPriceDao().listPrice()
                    val fetchingDateToCheck = priceLoad.get(0)
                    val fetchingExpiryDateToCheck = priceLoad.get(0)
                    val dateToCheck = fetchingDateToCheck.p_date
                    val expiryDateToCheck = fetchingExpiryDateToCheck.p_e_date
                    mDateStringToDateConversion = mSimpleDateFormat.parse(dateToCheck)!! // 11-12-2019
                    mExpiryDateStringToExpiryDateConversion = mSimpleDateFormat.parse(expiryDateToCheck)!! // 10-01-2020
                    if(checkDateValidity(mDateStringToDateConversion,mExpiryDateStringToExpiryDateConversion)) {

                        mAddAmountTitle.visibility = View.GONE
                        mAmount.visibility = View.GONE
                        mDone.visibility = View.GONE
                    }
                    else {
                        Log.e("Not in between","Not in between")
                    }
                }
                catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun checkDateValidity(date : Date, date1 : Date) : Boolean {
        return mCalendar.time.after(date) && mCalendar.time.before(date1) // 13-12-2019
    }


}
