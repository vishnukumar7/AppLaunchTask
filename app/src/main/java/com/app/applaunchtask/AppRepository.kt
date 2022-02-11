package com.app.applaunchtask

import androidx.annotation.WorkerThread
import com.app.applaunchtask.model.User
import com.app.applaunchtask.model.UserDao
import kotlinx.coroutines.flow.Flow

class AppRepository(private val userDao: UserDao) {

    val userList: Flow<MutableList<User>> =userDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User){
        userDao.insert(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(user: User){
        userDao.delete(user)
    }
}