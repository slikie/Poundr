package com.github.poundr.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.poundr.persistence.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM UserEntity WHERE id = :id")
    suspend fun deleteUser(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM UserEntity WHERE id = :id)")
    suspend fun isUserExist(id: Long): Boolean

    @Query("UPDATE UserEntity SET name = :name, distance = :distance, profilePicMediaHash = :profilePicMediaHash, lastSeen = :lastSeen WHERE id = :id")
    suspend fun updateUser(id: Long, name: String?, distance: Float?, profilePicMediaHash: String?, lastSeen: Long?)

//    @Transaction
    suspend fun upsertUser(user: UserEntity) {
        if (!isUserExist(user.id)) {
            insertUser(user)
        } else {
            updateUser(user.id, user.name, user.distance, user.profilePicMediaHash, user.lastSeen)
        }
    }
}