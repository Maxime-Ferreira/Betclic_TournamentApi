package com.tournament.models

import com.tournament.serializer.ObjectIdSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class TournamentPlayer(
    @SerialName("playerId") @Serializable(with = ObjectIdSerializer::class) val playerId: ObjectId = ObjectId.get(),
    val username: String,
    var points: Int = 0
)