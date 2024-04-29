package com.github.poundr.data

import com.github.poundr.network.ConversationService
import com.github.poundr.persistence.ConversationDao
import com.github.poundr.persistence.PoundrDatabase
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ConversationRepository"

@Singleton
class ConversationRepository @Inject constructor(
    private val db: PoundrDatabase,
    private val conversationDao: ConversationDao,
    private val conversationService: ConversationService
) {

}