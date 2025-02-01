package models

import com.tournament.serializer.ObjectIdSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Player(
    @SerialName("id") @Serializable(with = ObjectIdSerializer::class) val id: ObjectId = ObjectId.get(),
    val username: String,
    val points: Int,
    val rank: Int,
)