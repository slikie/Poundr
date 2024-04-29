package com.github.poundr.di

import android.content.Context
import androidx.room.Room
import com.github.poundr.persistence.CascadeDao
import com.github.poundr.persistence.ConversationDao
import com.github.poundr.persistence.PoundrDatabase
import com.github.poundr.persistence.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PersistenceModule {

    @Provides
    @Singleton
    fun providePoundrDatabase(
        @ApplicationContext context: Context
    ): PoundrDatabase {
        return Room.databaseBuilder(context, PoundrDatabase::class.java, "poundr-db")
            .fallbackToDestructiveMigration() // TODO: Remove this in production
            .build()
    }

    @Provides
    fun provideUserDao(poundrDatabase: PoundrDatabase): UserDao {
        return poundrDatabase.userDao()
    }

    @Provides
    fun provideCascadeDao(poundrDatabase: PoundrDatabase): CascadeDao {
        return poundrDatabase.cascadeDao()
    }

    @Provides
    fun provideConversationDao(poundrDatabase: PoundrDatabase): ConversationDao {
        return poundrDatabase.conversationDao()
    }
}