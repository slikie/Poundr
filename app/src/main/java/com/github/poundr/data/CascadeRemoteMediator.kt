package com.github.poundr.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.poundr.data.model.CascadeRequestArgs
import com.github.poundr.network.ServerDrivenCascadeService
import com.github.poundr.network.model.ServerDrivenCascadeApiItem
import com.github.poundr.persistence.PoundrDatabase
import com.github.poundr.persistence.model.CascadeItemEntity
import com.github.poundr.persistence.model.UserEntity
import com.github.poundr.ui.model.CascadeItem

private const val TAG = "CascadeRM"
private const val FIRST_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class CascadeRemoteMediator(
    private val isCascade: Boolean,
    private val cascadeRequestArgs: CascadeRequestArgs,
    private val poundrDatabase: PoundrDatabase,
    private val cascadeService: ServerDrivenCascadeService
) : RemoteMediator<Int, CascadeItem>() {
    private val userDao = poundrDatabase.userDao()
    private val cascadeDao = poundrDatabase.cascadeDao()
    private val conversationDao = poundrDatabase.conversationDao()

    private var nextPage: Int? = FIRST_PAGE

    override suspend fun initialize(): InitializeAction {
        return if (isCascade) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CascadeItem>
    ): MediatorResult {
        return try {
            val pageToLoad = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> nextPage
            }

            val response = cascadeService.getCascadePage(
                nearbyGeoHash = cascadeRequestArgs.nearbyGeoHash,
                exploreGeoHash = cascadeRequestArgs.exploreGeoHash,
                onlineOnly = cascadeRequestArgs.onlineOnly,
                photoOnly = cascadeRequestArgs.photoOnly,
                faceOnly = cascadeRequestArgs.faceOnly,
                notRecentlyChatted = cascadeRequestArgs.notRecentlyChatted,
                hasAlbum = cascadeRequestArgs.hasAlbum,
                ageMin = cascadeRequestArgs.ageMin,
                ageMax = cascadeRequestArgs.ageMax,
                heightCmMin = cascadeRequestArgs.heightCmMin,
                heightCmMax = cascadeRequestArgs.heightCmMax,
                weightGramsMin = cascadeRequestArgs.weightGramsMin,
                weightGramsMax = cascadeRequestArgs.weightGramsMax,
                tribes = cascadeRequestArgs.tribes,
                lookingFor = cascadeRequestArgs.lookingFor,
                relationshipStatuses = cascadeRequestArgs.relationshipStatuses,
                bodyTypes = cascadeRequestArgs.bodyTypes,
                sexualPositions = cascadeRequestArgs.sexualPositions,
                meetAt = cascadeRequestArgs.meetAt,
                nsfwPics = cascadeRequestArgs.nsfwPics,
                tags = cascadeRequestArgs.tags,
                fresh = cascadeRequestArgs.fresh,
                pageNumber = pageToLoad,
                genders = cascadeRequestArgs.genders,
                rightNow = cascadeRequestArgs.rightNow,
                favorites = cascadeRequestArgs.favorites
            )

            Log.d(TAG, "Loaded page $pageToLoad with ${response.items.size} items")

            poundrDatabase.withTransaction {
                cascadeDao.deleteAllCascadeItems()

                response.items.forEach { item ->
                    when (item) {
                        is ServerDrivenCascadeApiItem.PartialProfile -> {
                            //   @PrimaryKey val id: Long,
                            //    val aboutMe: String? = null,
                            //    val age: Int? = null,
                            //    val name: String? = null,
                            //    val distance: Float? = null,
                            //    val favorite: Boolean = false,
                            //    val profilePicMediaHash: String? = null,
                            //    val lastSeen: Long? = null,

                            //    @Json(name = "profileId") val profileId: Long,
                            //    @Json(name = "lastOnline") val lastOnline: Long?,
                            //    @Json(name = "approximateDistance") val approximateDistance: Boolean?,
                            //    @Json(name = "distanceMeters") val distanceMeters: Double?,
                            //    @Json(name = "isFavorite") val isFavorite: Boolean?,
                            //    @Json(name = "isTeleporting") val isTeleporting: Boolean?,
                            //    @Json(name = "isBoosting") val isBoosting: Boolean?,
                            //    @Json(name = "rightNow") val rightNow: RightNowStatus?,
                            //    @Json(name = "displayName") val displayName: String?,
                            //    @Json(name = "photoMediaHashes") val photoMediaHashes: List<String>?,
                            //    @Json(name = "lastViewed") val lastViewed: Long?,
                            //    @Json(name = "hasChattedInLast24Hrs") val hasChattedInLast24Hrs: Boolean?,
                            //    @Json(name = "lastMessageTimestamp") val lastMessageTimestamp: Long?,
                            //    @Json(name = "upsellItemType") val upsellItemType: String?,
                            //    @Json(name = "unreadCount") val unreadCount: Int?,
                            //    @Json(name = "arrivalDays") val arrivalDays: Int?
                            val user = UserEntity(
                                id = item.data.profileId,
                                name = item.data.displayName,
                                distance = item.data.distanceMeters,
                                favorite = item.data.isFavorite ?: false,
                                profilePicMediaHash = item.data.photoMediaHashes.firstOrNull(),
                                lastSeen = item.data.lastOnline,
                            )
                            userDao.upsertUserFromPartialProfile(user)

                            val cascadeItem = CascadeItemEntity(
                                profileId = item.data.profileId
                            )
                            cascadeDao.insertCascadeItem(cascadeItem)

                            // todo: update conversation
                        }
                        is ServerDrivenCascadeApiItem.Profile -> {
                            //    @Json(name = "profileId") val profileId: Long,
                            //    @Json(name = "lastOnline") val lastOnline: Long?,
                            //    @Json(name = "isFavorite") val isFavorite: Boolean?,
                            //    @Json(name = "isBoosting") val isBoosting: Boolean?,
                            //    @Json(name = "isTeleporting") val isTeleporting: Boolean?,
                            //    @Json(name = "lastViewed") val lastViewed: Long?,
                            //    @Json(name = "hasChattedInLast24Hrs") val hasChattedInLast24Hrs: Boolean?,
                            //    @Json(name = "lastMessageTimestamp") val lastMessageTimestamp: Long?,
                            //    @Json(name = "photoMediaHashes") val photoMediaHashes: List<String>,
                            //    @Json(name = "displayName") val displayName: String?,
                            //    @Json(name = "age") val age: Int?,
                            //    @Json(name = "approximateDistance") val approximateDistance: Boolean?,
                            //    @Json(name = "distanceMeters") val distanceMeters: Float?,
                            //    @Json(name = "aboutMe") val aboutMe: String?,
                            //    @Json(name = "ethnicity") val ethnicity: Int?,
                            //    @Json(name = "lookingFor") val lookingFor: List<Int>?,
                            //    @Json(name = "relationshipStatus") val relationshipStatus: Int?,
                            //    @Json(name = "tribes") val tribes: List<Int>?,
                            //    @Json(name = "bodyType") val bodyType: Int?,
                            //    @Json(name = "sexualPosition") val sexualPosition: Int?,
                            //    @Json(name = "hivStatus") val hivStatus: Int?,
                            //    @Json(name = "lastTestedDate") val lastTestedDate: Long?,
                            //    @Json(name = "heightCm") val heightCm: Double?,
                            //    @Json(name = "weightGrams") val weightGrams: Double?,
                            //    @Json(name = "socialNetworks") val socialNetworks: List<SocialNetwork2>?,
                            //    @Json(name = "acceptsNsfwPics") val acceptsNSFWPics: Int?,
                            //    @Json(name = "meetAt") val meetAt: List<Int>?,
                            //    @Json(name = "tags") val tags: List<String>?,
                            //    @Json(name = "genders") val genders: List<Int>?,
                            //    @Json(name = "pronouns") val pronouns: List<Int>?,
                            //    @Json(name = "vaccines") val vaccines: List<Int>?,
                            //    @Json(name = "rightNow") val rightNow: RightNowStatus?,
                            //    @Json(name = "rightNowText") val rightNowText: String?,
                            //    @Json(name = "rightNowPosted") val rightNowPosted: Long?,
                            //    @Json(name = "unreadCount") val unreadCount: Int?,
                            //    @Json(name = "arrivalDays") val arrivalDays: Int?
                            val user = UserEntity(
                                id = item.data.profileId,
                                aboutMe = item.data.aboutMe,
                                age = item.data.age,
                                name = item.data.displayName,
                                distance = item.data.distanceMeters,
                                favorite = item.data.isFavorite ?: false,
                                profilePicMediaHash = item.data.photoMediaHashes.firstOrNull(),
                                lastSeen = item.data.lastOnline,
                            )
                            userDao.upsertUser(user)

                            val cascadeItem = CascadeItemEntity(
                                profileId = item.data.profileId
                            )
                            cascadeDao.insertCascadeItem(cascadeItem)

                            // todo: update conversation
                        }
                    }
                }
            }

            Log.d(TAG, "Inserted ${response.items.size} items")

            nextPage = response.nextPage

            MediatorResult.Success(endOfPaginationReached = response.items.isEmpty())
        } catch (e: Exception) {
            Log.e(TAG, "load: Error loading items", e)
            MediatorResult.Error(e)
        }
    }
}