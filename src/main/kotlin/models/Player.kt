package com.tournament.models

import com.tournament.serializer.ObjectIdSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Player(
    @BsonId @SerialName("_id") @Serializable(with = ObjectIdSerializer::class)
    val id: ObjectId = ObjectId.get(),
    val username: String
)