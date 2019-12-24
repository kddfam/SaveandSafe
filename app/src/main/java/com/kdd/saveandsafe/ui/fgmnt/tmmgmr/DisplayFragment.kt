package com.kdd.saveandsafe.ui.fgmnt.tmmgmr


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdd.saveandsafe.R
import com.kdd.saveandsafe.dtbse.dbcls.SandSDatabase
import com.kdd.saveandsafe.ui.adptr.TaskAdapter
import com.kdd.saveandsafe.xtrs.BaseFragment
import kotlinx.coroutines.launch

class DisplayFragment : BaseFragment() {

    // Views Declaration
    lateinit var mAddTaskButton : Button
    lateinit var mRecyclerView : RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_display, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Views Initialization
        mAddTaskButton = view!!.findViewById(R.id.bt_cldone_add_task_page)
        mRecyclerView = view!!.findViewById(R.id.rv_cldtwo)

        // Button Click Handler
        mAddTaskButton.setOnClickListener { handlerAddTaskButtonClick() }

        // RecyclerView Manipulations
        mRecyclerView.layoutManager = LinearLayoutManager(view!!.context)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        launch {
            context?.let {
                val task = SandSDatabase(it).getTaskDao().listTask()
                mRecyclerView.adapter = TaskAdapter(task)
            }
        }
    }

    private fun handlerAddTaskButtonClick() {
        val action = DisplayFragmentDirections.displayToAddTask()
        Navigation.findNavController(view!!).navigate(action)
    }


}
