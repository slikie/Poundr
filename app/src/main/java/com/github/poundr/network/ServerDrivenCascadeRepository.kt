package com.github.poundr.network

import android.util.Log
import com.github.poundr.location.GeoHash
import com.github.poundr.location.PoundrLocationManager
import com.github.poundr.model.ServerDrivenCascadeApiItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerDrivenCascadeRepository @Inject constructor(
    private val serverDrivenCascadeService: ServerDrivenCascadeService,
    private val poundrLocationManager: PoundrLocationManager
) {
    private val _profiles = MutableStateFlow(emptyList<ServerDrivenCascadeApiItem>())
    val profiles = _profiles.asStateFlow()

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

            _profiles.value = items
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is HttpException) {
                Log.d("BrowseViewModel", "fetchData: ${e.response()?.errorBody()?.string()}")
            }
        }
    }
}