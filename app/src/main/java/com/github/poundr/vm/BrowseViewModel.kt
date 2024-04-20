package com.github.poundr.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.poundr.UserManager
import com.github.poundr.network.ServerDrivenCascadeService
import com.squareup.moshi.JsonReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val userManager: UserManager,
    private val serverDrivenCascadeService: ServerDrivenCascadeService,
) : ViewModel() {
    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _profiles = MutableStateFlow(emptyList<String>())
    val profiles = _profiles.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _refreshing.value = true
            fetchData()
            _refreshing.value = false
        }
    }

    suspend fun fetchData() = withContext(Dispatchers.IO) {
        try {
            val responseBody = serverDrivenCascadeService.getCascadePage(
                nearbyGeoHash = "",
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

            val items = mutableListOf<String>()
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
        }
    }

    private fun parseItems(reader: JsonReader, items: MutableList<String>) {
        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "data" -> {
                        reader.beginObject()
                        while (reader.hasNext()) {
                            when (reader.nextName()) {
                                "displayName" -> { items.add(reader.nextString()) }
                                else -> { reader.skipValue() }
                            }
                        }
                        reader.endObject()
                    }
                    else -> { reader.skipValue() }
                }
            }
            reader.endObject()
        }
        reader.endArray()
    }
}