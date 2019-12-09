package com.kdd.saveandsafe.ui.fgmnt


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.kdd.saveandsafe.R
import com.kdd.saveandsafe.dtbse.dbcls.SandSDatabase
import com.kdd.saveandsafe.dtbse.ety.ItemEntity
import com.kdd.saveandsafe.xtrs.BaseFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddItemFragment : BaseFragment() {

    // Variable Declarations
    // Views
    lateinit var mItemName : EditText
    lateinit var mItemPrice : EditText
    lateinit var mAddButton : Button

    // Date and Time
    lateinit var mCalender : Calendar
    lateinit var mSimpleDateFormat : SimpleDateFormat
    lateinit var mSimpleTimeFormat : SimpleDateFormat
    lateinit var mItemDate : String
    lateinit var mItemTime : String

    // Others
    lateinit var mSnackBar : Snackbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Variable Initialization
        // UI Views
        mItemName = view!!.findViewById(R.id.et_add_item_name)
        mItemPrice = view!!.findViewById(R.id.et_add_item_price)
        mAddButton = view!!.findViewById(R.id.bt_add_item)

        // Date and Time
        mCalender = Calendar.getInstance()
        mSimpleTimeFormat = SimpleDateFormat("hh:mm:ss", Locale.ENGLISH)
        mSimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        mItemTime = mSimpleTimeFormat.format(mCalender.time)
        mItemDate = mSimpleDateFormat.format(mCalender.time)

        // Declaring Button Click Action
        mAddButton.setOnClickListener { buttonClickListener() }

    }

    // Custom function which will be called everytime button pressed
    private fun buttonClickListener() {

        // Local variables declaration
        val item_name = mItemName.text.toString()
        val item_price = mItemPrice.text.toString()
        val item_time = mItemTime
        val item_date = mItemDate

        // Coroutine call
        launch {
            context?.let {
                val item = ItemEntity(item_name,item_price.toIntOrNull()!!,item_time,item_date)
                SandSDatabase(it).getItemDao().addItem(item)
            }
        }

        // Navigation
        val action = AddItemFragmentDirections.backToRecent()
        Navigation.findNavController(view!!).navigate(action)

        // Display message
        mSnackBar  = Snackbar.make(view!!, "Item Added Successfully", Snackbar.LENGTH_LONG)
        mSnackBar.show()
    }

}
