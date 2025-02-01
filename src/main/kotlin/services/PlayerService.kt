package com.tournament.services

import models.Player
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.*

class PlayerService {
    private val client = KMongo.createClient("mongodb://localhost:27017")
    private val database = client.getDatabase("betclic_test")
    private val collection = database.getCollection<Player>("players")

    fun getAllPlayers(): List<Player> {
        return collection.find().toList().sortedWith(compareByDescending { it.points })
    }

    fun addPlayer(player: Player) {
        collection.insertOne(player)
    }

    fun getPlayerById(id: String): Player? {
        val objectId = ObjectId(id)
        val query = Document("id", objectId)
        return collection.findOne(query)
    }

    fun updatePlayer(id: String, updatedPlayer: Player): Boolean {
        val result = collection.replaceOneById(id, updatedPlayer)
        return result.matchedCount > 0
    }

    fun deleteAllPlayers(): Boolean {
        val result = collection.deleteMany("{}")
        return result.deletedCount > 0
    }
}