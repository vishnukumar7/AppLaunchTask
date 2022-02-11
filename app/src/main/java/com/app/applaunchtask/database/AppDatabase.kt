package com.app.applaunchtask.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.applaunchtask.model.User
import com.app.applaunchtask.model.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDoa(): UserDao

}