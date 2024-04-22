package com.github.poundr.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.poundr.PoundrLocationManager
import com.github.poundr.UserManager
import com.github.poundr.network.ServerDrivenCascadeService
import com.github.poundr.ui.GridProfileModel
import com.github.poundr.utils.GeoHash
import com.squareup.moshi.JsonReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val userManager: UserManager,
    private val serverDrivenCascadeService: ServerDrivenCascadeService,
    private val poundrLocationManager: PoundrLocationManager
) : ViewModel() {
    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _profiles = MutableStateFlow(emptyList<GridProfileModel>())
    val profiles = _profiles.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            _refreshing.value = true
            fetchData()
            _refreshing.value = false
        }
    }

    suspend fun fetchData() = withContext(Dispatchers.IO) {
        try {
            val location = poundrLocationManager.getLastLocation() ?: poundrLocationManager.getCurrentLocation() ?: return@withContext
            val geohash = GeoHash.encode(location.latitude, location.longitude, GeoHash.MAX_PRECISION)
            val responseBody = serverDrivenCascadeService.getCascadePage(
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

            val items = mutableListOf<GridProfileModel>()
            var nextPage = 0

            val reader = JsonReader.of(responseBody.source())
            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "items" -> { parseItems(reader, items) }
                    "nextPage" -> { nextPage = reader.nextInt() }
                    else -> { reader.skipValue() }
                }
            }

            Log.d("BrowseViewModel", "items: ${items.size}")
            Log.d("BrowseViewModel", "nextPage: $nextPage")

            _profiles.value = items
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is HttpException) {
                Log.d("BrowseViewModel", "fetchData: ${e.response()?.errorBody()?.string()}")
            }
        }
    }

    private fun parseItems(reader: JsonReader, items: MutableList<GridProfileModel>) {
        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "data" -> {
                        reader.beginObject()
                        var imageId: String? = null
                        var displayName = ""
                        while (reader.hasNext()) {
                            when (reader.nextName()) {
                                "displayName" -> { displayName = reader.nextString() }
                                "photoMediaHashes" -> {
                                    reader.beginArray()
                                    while (reader.hasNext()) {
                                        if (imageId == null) {
                                            imageId = reader.nextString()
                                        } else {
                                            reader.skipValue()
                                        }
                                    }
                                    reader.endArray()
                                }
                                else -> { reader.skipValue() }
                            }
                        }
                        reader.endObject()
                        items.add(GridProfileModel(imageId, displayName))
                    }
                    else -> { reader.skipValue() }
                }
            }
            reader.endObject()
        }
        reader.endArray()
    }
}