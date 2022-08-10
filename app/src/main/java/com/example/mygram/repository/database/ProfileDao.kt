package com.example.mygram.repository.database

import androidx.room.*
import com.example.mygram.domain.User
import kotlinx.coroutines.flow.Flow


@Dao
interface ProfileDao {

    @Query("SELECT * FROM User")
    fun getUser(): Flow<User>

    @Update
    fun updateUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

}