package com.tournament.services

import com.mongodb.client.model.Filters
import com.tournament.ConfigLoader
import com.tournament.models.RankedPlayer
import com.tournament.models.TournamentPlayer
import models.Tournament
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.*

class TournamentService(
    private val playerService: PlayerService
) {
    private val client = KMongo.createClient(ConfigLoader.mongoUri)
    private val database = client.getDatabase(ConfigLoader.mongoDbName)
    private val tournamentCollection = database.getCollection<Tournament>("tournaments")

    fun createTournament(name: String) {
        val tournament = Tournament(name = name)
        tournamentCollection.insertOne(tournament)
    }

    private fun getTournamentById(id: String): Tournament? {
        val objectId = ObjectId(id)
        val query = Document("_id", objectId)
        return tournamentCollection.findOne(query)
    }

    fun addPlayerToTournament(tournamentId: String, username: String): Boolean {
        val tournament = getTournamentById(tournamentId) ?: return false
        val user = playerService.getUserByUsername(username) ?: return false

        val player = TournamentPlayer(playerId = user.id, username = user.username, points = 0)

        val updatedTournament = tournament.copy(players = tournament.players + player)
        val result = tournamentCollection.replaceOne(Filters.eq("_id", ObjectId(tournamentId)), updatedTournament)

        return result.modifiedCount > 0
    }

    fun updatePlayerPoints(tournamentId: String, playerId: String, points: Int): Boolean {
        val tournament = getTournamentById(tournamentId) ?: return false
        val updatedPlayers = tournament.players.map {
            if (it.playerId.toHexString() == playerId) it.copy(points = points) else it
        }
        val updatedTournament = tournament.copy(players = updatedPlayers)
        val result = tournamentCollection.replaceOne(Filters.eq("_id", ObjectId(tournamentId)), updatedTournament)
        return result.matchedCount > 0
    }

    fun getRankedPlayers(tournamentId: String): List<RankedPlayer>? {
        val players = getTournamentById(tournamentId)?.players
            ?.sortedByDescending { it.points }
            ?: return null

        return players.mapIndexed { index, player ->
            RankedPlayer(
                playerId = player.playerId,
                username = player.username,
                points = player.points,
                ranking = index + 1
            )
        }
    }

    fun deleteAllPlayersFromTournament(tournamentId: String): Boolean {
        val tournament = getTournamentById(tournamentId) ?: return false
        val updatedTournament = tournament.copy(players = emptyList())
        val result = tournamentCollection.replaceOne(Filters.eq("_id", ObjectId(tournamentId)), updatedTournament)
        return result.matchedCount > 0
    }
}