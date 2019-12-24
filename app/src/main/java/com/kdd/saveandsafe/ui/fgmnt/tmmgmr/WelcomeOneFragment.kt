package com.kdd.saveandsafe.ui.fgmnt.tmmgmr


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.kdd.saveandsafe.R
import java.util.*

class WelcomeOneFragment : Fragment() {

    // Views Declarations
    lateinit var mTimer: Timer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_welcome_one, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Timer Functionality
        mTimer = Timer()
        mTimer.schedule(object : TimerTask() {
            override fun run() {
                Navigation.findNavController(view!!).navigate(WelcomeOneFragmentDirections.welcomeToDisplay())
            }
        },1000)
    }

}
