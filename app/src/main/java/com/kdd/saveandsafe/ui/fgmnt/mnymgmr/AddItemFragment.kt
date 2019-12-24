package com.kdd.saveandsafe.ui.fgmnt.mnymgmr


import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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

    // Views Declarations
    lateinit var mItemName : EditText
    lateinit var mItemPrice : EditText
    lateinit var mAddButton : Button
    lateinit var mSnackBar : Snackbar

    // Date and Time
    lateinit var mCalender : Calendar
    lateinit var mSimpleDateFormat : SimpleDateFormat
    lateinit var mSimpleTimeFormat : SimpleDateFormat
    lateinit var mItemDate : String
    lateinit var mItemTime : String

    // Notifications
    lateinit var mNotificationManager: NotificationManager
    lateinit var mNotificationChannel: NotificationChannel
    lateinit var mBuilder : Notification.Builder
    private val mChannelID = "com.kdd.saveandsafe.ui.fgmnt"
    private val mNotificationDescription = "New Item Added"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mNotificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Views Initialization
        mItemName = view!!.findViewById(R.id.et_add_item_name)
        mItemPrice = view!!.findViewById(R.id.et_add_item_price)
        mAddButton = view!!.findViewById(R.id.bt_add_item)

        // Date and Time
        mCalender = Calendar.getInstance()
        mSimpleTimeFormat = SimpleDateFormat("hh:mm:ss", Locale.ENGLISH)
        mSimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        mItemTime = mSimpleTimeFormat.format(mCalender.time)
        mItemDate = mSimpleDateFormat.format(mCalender.time)

        // Add Button Click Handler
        mAddButton.setOnClickListener { mAddButtonHandler() }

    }

    // Custom function which will be called everytime button pressed
    private fun mAddButtonHandler() {

        // Local variables declaration
        val item_name = mItemName.text.toString()
        val item_price = mItemPrice.text.toString()
        val item_time = mItemTime
        val item_date = mItemDate

        // Coroutine call
        launch {
            context?.let {
                if (item_name.isEmpty() || item_price.isEmpty()) {
                    mSnackBar = Snackbar.make(view!!, "Add Fields are Required", Snackbar.LENGTH_LONG)
                    mSnackBar.show()
                }
                else {
                    val item = ItemEntity(item_name,item_price.toIntOrNull()!!,item_time,item_date)
                    SandSDatabase(it).getItemDao().addItem(item)

                    val fetchPrice = SandSDatabase(it).getPriceDao().listRecent()
                    val getPrices = fetchPrice.get(0)
                    val price = getPrices.p_updated_amount
                    val uprice = price-item_price.toInt()
                    val id = getPrices.p_id
                    var updateItems = getPrices.p_total_items
                    updateItems = updateItems+1
                    SandSDatabase(it).getPriceDao().updateAmount(uprice,updateItems,id)

                    // Navigation
                    val action =
                        AddItemFragmentDirections.backToRecent()
                    Navigation.findNavController(view!!).navigate(action)

                    // Notification Sound
                    val media_player = MediaPlayer.create(view!!.context, Settings.System.DEFAULT_NOTIFICATION_URI)
                    media_player.start()

                    // Display message
                    mSnackBar  = Snackbar.make(view!!, "Item Added Successfully", Snackbar.LENGTH_LONG)
                    mSnackBar.show()

                    showMeTheNotification()

                }
            }
        }
    }

    private fun showMeTheNotification() {
        val nameToDisplay = mItemName.text.toString()
        val priceToDisplay = mItemPrice.text.toString()
        val intent = Intent(view!!.context, LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivities(view!!.context,0, arrayOf(intent),PendingIntent.FLAG_UPDATE_CURRENT)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationChannel = NotificationChannel(mChannelID,mNotificationDescription,NotificationManager.IMPORTANCE_HIGH)
            mNotificationChannel.enableLights(true)
            mNotificationChannel.enableVibration(true)
            mNotificationChannel.lightColor = Color.GREEN
            mNotificationManager.createNotificationChannel(mNotificationChannel)

            mBuilder = Notification.Builder(view!!.context, mChannelID)
                .setContentTitle("Congratulations..!!")
                .setContentText("New Item Added with Name : ${nameToDisplay} and Price : ${priceToDisplay}")
                .setSmallIcon(R.drawable.sands)
                .setLargeIcon(BitmapFactory.decodeResource(view!!.context.resources, R.drawable.sands))
                .setContentIntent(pendingIntent)
        }
        else {
            mBuilder = Notification.Builder(view!!.context)
                .setContentTitle("New Item Added")
                .setContentText("New Item Added with Name : ${nameToDisplay} and Price : ${priceToDisplay}")
                .setSmallIcon(R.drawable.sands)
                .setLargeIcon(BitmapFactory.decodeResource(view!!.context.resources, R.drawable.sands))
                .setContentIntent(pendingIntent)
        }
        mNotificationManager.notify(1245, mBuilder.build())
    }
}
