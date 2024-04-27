package com.github.poundr.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.github.poundr.persistence.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM UserEntity WHERE id = :id")
    suspend fun deleteUser(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM UserEntity WHERE id = :id)")
    suspend fun isUserExist(id: Long): Boolean

    @Upsert
    suspend fun upsertUser(user: UserEntity)

    @Query("UPDATE UserEntity SET name = :name, distance = :distance, favorite = :favorite, profilePicMediaHash = :profilePicMediaHash, lastSeen = :lastSeen WHERE id = :id")
    suspend fun updateUserFromPartialProfile(
        id: Long,
        name: String?,
        distance: Float?,
        favorite: Boolean,
        profilePicMediaHash: String?,
        lastSeen: Long?
    )

    @Query("UPDATE UserEntity SET name = :name, distance = :distance, profilePicMediaHash = :profilePicMediaHash, lastSeen = :lastSeen WHERE id = :id")
    suspend fun updateUserFromConversation(id: Long, name: String?, distance: Float?, profilePicMediaHash: String?, lastSeen: Long?)

    @Transaction
    suspend fun upsertUserFromPartialProfile(user: UserEntity) {
        if (!isUserExist(user.id)) {
            insertUser(user)
        } else {
            updateUserFromPartialProfile(user.id, user.name, user.distance, user.favorite, user.profilePicMediaHash, user.lastSeen)
        }
    }

    @Transaction
    suspend fun upsertUserFromConversation(user: UserEntity) {
        if (!isUserExist(user.id)) {
            insertUser(user)
        } else {
            updateUserFromConversation(user.id, user.name, user.distance, user.profilePicMediaHash, user.lastSeen)
        }
    }
}