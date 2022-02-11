package com.app.applaunchtask

import android.app.Application
import com.app.applaunchtask.database.DatabaseClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    private val applicationScope= CoroutineScope(SupervisorJob())

    private val database by lazy { DatabaseClient.getInstance(this) }

    val appRepository by lazy { AppRepository(database.userDoa(),RetrofitHelper.getInstances()) }
}