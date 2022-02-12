package com.app.applaunchtask.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Query("select * from user")
    fun getAll(): Flow<MutableList<User>>

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("delete from user")
    fun deleteAll()
}