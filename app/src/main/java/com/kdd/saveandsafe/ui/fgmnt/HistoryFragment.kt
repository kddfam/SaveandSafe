package com.kdd.saveandsafe.ui.fgmnt


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.kdd.saveandsafe.R

class HistoryFragment : Fragment() {

    // Variable Declarations
    lateinit var mMoveToRecentButton : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Variable Initialization
        mMoveToRecentButton = view!!.findViewById(R.id.tv_clhone_recent_page)
        mMoveToRecentButton.setOnClickListener { handleMoveToRecentButtonClick() }
    }

    // Custom function which will be called everytime button pressed
    private fun handleMoveToRecentButtonClick() {

        Navigation.findNavController(view!!).navigate(HistoryFragmentDirections.historyToRecent())

    }

}
