package com.kdd.saveandsafe.xtrs

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(),CoroutineScope {

    // Initializing Job Variable
    private lateinit var mJob : Job

    // Co-routine Definition
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mJob = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}