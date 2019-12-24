package com.kdd.saveandsafe.ui.fgmnt.mnymgmr


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdd.saveandsafe.R
import com.kdd.saveandsafe.dtbse.dbcls.SandSDatabase
import com.kdd.saveandsafe.ui.adptr.RecentAdapter
import com.kdd.saveandsafe.xtrs.BaseFragment
import kotlinx.coroutines.launch

class RecentFragment : BaseFragment() {

    // Views Declarations
    lateinit var mMoveToAddItemButton : TextView
    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recent, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Views Initialization
        mMoveToAddItemButton = view!!.findViewById(R.id.tv_clrone_add_item_page)
        mRecyclerView = view!!.findViewById(R.id.rv_clrtwo)

        // RecyclerView Calls
        mRecyclerView.layoutManager = LinearLayoutManager(view!!.context)

        // Move to add item page
        mMoveToAddItemButton.setOnClickListener { handleMoveToAddItemButtonClick() }
        loadItemList()

    }

    private fun loadItemList() {
        launch {
            context?.let {
                val items = SandSDatabase(it).getItemDao().listItem()
                mRecyclerView.adapter = RecentAdapter(items)
            }
        }
    }

    private fun handleMoveToAddItemButtonClick() {
        Navigation.findNavController(view!!).navigate(RecentFragmentDirections.recentToAddItem())
    }

}
