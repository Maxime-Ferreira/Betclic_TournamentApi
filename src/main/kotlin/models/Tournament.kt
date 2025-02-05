package models

import com.tournament.models.TournamentPlayer
import com.tournament.serializer.ObjectIdSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Tournament(
    @BsonId @SerialName("_id") @Serializable(with = ObjectIdSerializer::class)
    val id: ObjectId = ObjectId.get(),
    val name: String,
    val players: List<TournamentPlayer> = emptyList()
)