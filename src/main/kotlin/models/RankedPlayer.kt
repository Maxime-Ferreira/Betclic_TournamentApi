package com.tournament.models

import com.tournament.serializer.ObjectIdSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class RankedPlayer(
    @Serializable(with = ObjectIdSerializer::class) val playerId: ObjectId,
    val username: String,
    val points: Int,
    val ranking: Int
)