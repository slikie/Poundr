package com.github.poundr.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
//    foreignKeys = [
//        ForeignKey(
//            entity = ConversationPreviewEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["previewId"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
)
data class UserEntity(
    @PrimaryKey val id: Long,
    val aboutMe: String? = null,
    val age: Int? = null,
    val name: String? = null,
    val distance: Float? = null,
    val favorite: Boolean = false,
    val profilePicMediaHash: String? = null,
    val lastSeen: Long? = null,
)

//            "aboutMe": null,
//            "age": 20,
//            "approximateDistance": false,
//            "arrivalDays": null,
//            "bodyType": null,
//            "displayName": " M Mistar ",
//            "distance": 59685.7,
//            "ethnicity": null,
//            "foundVia": null,
//            "genders": [],
//            "grindrTribes": [],
//            "hashtags": [],
//            "height": null,
//            "hivStatus": null,
//            "identity": null,
//            "isFavorite": false,
//            "isNew": true,
//            "isRoaming": false,
//            "isTeleporting": false,
//            "lastChatTimestamp": 1714092383280,
//            "lastTestedDate": null,
//            "lastUpdatedTime": 1713906435387,
//            "lastViewed": null,
//            "lookingFor": [],
//            "medias": [
//                {
//                    "mediaHash": "e3a09c78f4921493862a2682909b163913e73760",
//                    "reason": null,
//                    "state": 1,
//                    "type": 0
//                }
//            ],
//            "meetAt": [],
//            "nsfw": null,
//            "profileId": "624435880",
//            "profileImageMediaHash": "e3a09c78f4921493862a2682909b163913e73760",
//            "profileTags": [],
//            "pronouns": [],
//            "relationshipStatus": null,
//            "rightNow": "NOT_ACTIVE",
//            "rightNowPosted": null,
//            "rightNowText": null,
//            "seen": 1714092357687,
//            "sexualPosition": null,
//            "showAge": true,
//            "showDistance": true,
//            "socialNetworks": {},
//            "tapType": null,
//            "tapped": false,
//            "unreadCount": 0,
//            "vaccines": [],
//            "weight": null