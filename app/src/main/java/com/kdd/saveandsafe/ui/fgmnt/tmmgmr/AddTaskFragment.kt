package com.kdd.saveandsafe.ui.fgmnt.tmmgmr


import android.media.MediaPlayer
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.kdd.saveandsafe.R
import com.kdd.saveandsafe.dtbse.dbcls.SandSDatabase
import com.kdd.saveandsafe.dtbse.ety.TaskEntity
import com.kdd.saveandsafe.xtrs.BaseFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddTaskFragment : BaseFragment() {

    // Views Declaration
    lateinit var mTaskName : EditText
    lateinit var mRadioGroup : RadioGroup
    lateinit var mRadioButton : RadioButton
    lateinit var mAddTaskButton : Button
    lateinit var mSnackBar : Snackbar

    // Date and Time
    lateinit var mCalendar: Calendar
    lateinit var mCalenderOne : Calendar
    lateinit var mCalculateExpiryTime : Date
    lateinit var mSimpleDateFormat : SimpleDateFormat
    lateinit var mSimpleTimeFormat : SimpleDateFormat
    lateinit var mTaskDateInString : String
    lateinit var mTaskTimeInString : String
    lateinit var mTaskExpiryTimeInString : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Views Initialization
        mTaskName = view!!.findViewById(R.id.et_task_name)
        mRadioGroup = view!!.findViewById(R.id.rg_select_time)
        mAddTaskButton = view!!.findViewById(R.id.bt_add_task)

        // Date and Time
        mCalendar = Calendar.getInstance()
        mSimpleTimeFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        mSimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        mTaskTimeInString = mSimpleTimeFormat.format(mCalendar.time)
        mTaskDateInString = mSimpleDateFormat.format(mCalendar.time)

        // Add Task Handler
        mAddTaskButton.setOnClickListener { mAddTaskButtonHandler() }


    }

    private fun mAddTaskButtonHandler() {

        // Expiry Time
        mCalenderOne = Calendar.getInstance()
        mCalculateExpiryTime = Date()
        mCalenderOne.time = mCalculateExpiryTime

        // Local Variable declarations
        val task = mTaskName.text.toString()
        val time = mTaskTimeInString
        val date = mTaskDateInString

        val selectedTime = mRadioGroup.checkedRadioButtonId
        mRadioButton = view!!.findViewById(selectedTime)
        if (mRadioButton.text.equals("30 Mins")) {
            mCalenderOne.add(Calendar.MINUTE, 30)
            mCalculateExpiryTime = mCalenderOne.time
            mTaskExpiryTimeInString = mSimpleTimeFormat.format(mCalenderOne.time)
        }
        else if (mRadioButton.text.equals("45 Mins")) {
            mCalenderOne.add(Calendar.MINUTE, 45)
            mCalculateExpiryTime = mCalenderOne.time
            mTaskExpiryTimeInString = mSimpleTimeFormat.format(mCalenderOne.time)
        }
        else if (mRadioButton.text.equals("60 Mins")) {
            mCalenderOne.add(Calendar.MINUTE, 60)
            mCalculateExpiryTime = mCalenderOne.time
            mTaskExpiryTimeInString = mSimpleTimeFormat.format(mCalenderOne.time)
        }
        else {
            mCalenderOne.add(Calendar.MINUTE, 120)
            mCalculateExpiryTime = mCalenderOne.time
            mTaskExpiryTimeInString = mSimpleTimeFormat.format(mCalenderOne.time)
        }

        val etime = mTaskExpiryTimeInString

        launch {
            context?.let {
                val mTask = TaskEntity(task,time,date,etime)
                val add = SandSDatabase(it).getTaskDao().addTask(mTask)

                // Navigation
                val action = AddTaskFragmentDirections.backToDisplay()
                Navigation.findNavController(view!!).navigate(action)

                // Notification Sound
                val media_player = MediaPlayer.create(view!!.context, Settings.System.DEFAULT_NOTIFICATION_URI)
                media_player.start()

                // Display message
                mSnackBar  = Snackbar.make(view!!, "Task Added Successfully", Snackbar.LENGTH_LONG)
                mSnackBar.show()
            }
        }
    }

}
