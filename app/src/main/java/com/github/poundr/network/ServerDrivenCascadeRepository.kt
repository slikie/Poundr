package com.github.poundr.network

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.github.poundr.data.CascadeRemoteMediator
import com.github.poundr.data.model.CascadeRequestArgs
import com.github.poundr.location.GeoHash
import com.github.poundr.location.PoundrLocationManager
import com.github.poundr.network.model.ServerDrivenCascadeApiItem
import com.github.poundr.persistence.PoundrDatabase
import com.github.poundr.ui.model.CascadeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerDrivenCascadeRepository @Inject constructor(
    private val poundrLocationManager: PoundrLocationManager,
    private val poundrDatabase: PoundrDatabase,
    private val serverDrivenCascadeService: ServerDrivenCascadeService
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getMessages(
        isCascade: Boolean,
    ): Pager<Int, CascadeItem> {
        val location = runBlocking { poundrLocationManager.getLastLocation() ?: poundrLocationManager.getCurrentLocation()!! }
        val geohash = GeoHash.encode(location.latitude, location.longitude, GeoHash.MAX_PRECISION)
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CascadeRemoteMediator(
                isCascade,
                CascadeRequestArgs(
                    nearbyGeoHash = geohash,
                ),
                poundrDatabase,
                serverDrivenCascadeService
            ),
            pagingSourceFactory = { poundrDatabase.cascadeDao().getCascadeItemsPagingSource() }
        )
    }

    suspend fun fetchData() = withContext(Dispatchers.IO) {
        try {
            val location = poundrLocationManager.getLastLocation() ?: poundrLocationManager.getCurrentLocation() ?: return@withContext
            val geohash = GeoHash.encode(location.latitude, location.longitude, GeoHash.MAX_PRECISION)
            val page = serverDrivenCascadeService.getCascadePage(
                nearbyGeoHash = geohash,
                exploreGeoHash = null,
                onlineOnly = false,
                photoOnly = false,
                faceOnly = false,
                notRecentlyChatted = false,
                hasAlbum = null,
                ageMin = null,
                ageMax = null,
                heightCmMin = null,
                heightCmMax = null,
                weightGramsMin = null,
                weightGramsMax = null,
                tribes = null,
                lookingFor = null,
                relationshipStatuses = null,
                bodyTypes = null,
                sexualPositions = null,
                meetAt = null,
                nsfwPics = null,
                tags = null,
                fresh = false,
                pageNumber = 1,
                genders = null,
                rightNow = false,
                favorites = false
            )

            val items = mutableListOf<ServerDrivenCascadeApiItem>()

            page.items.forEach {
                when (it) {
                    is ServerDrivenCascadeApiItem.PartialProfile,
                    is ServerDrivenCascadeApiItem.Profile -> {
                        items.add(it)
                    }
                }
            }

//            _profiles.value = items
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is HttpException) {
                Log.d("BrowseViewModel", "fetchData: ${e.response()?.errorBody()?.string()}")
            }
        }
    }
}