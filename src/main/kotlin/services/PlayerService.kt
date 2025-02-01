package com.tournament.services

import com.tournament.models.Player
import org.bson.Document
import org.litote.kmongo.KMongo
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class PlayerService {
    private val client = KMongo.createClient("mongodb://localhost:27017")
    private val database = client.getDatabase("betclic_test")
    private val playersCollection = database.getCollection<Player>("players")

    fun addPlayer(player: Player) {
        playersCollection.insertOne(player)
    }

    fun getUserByUsername(username: String): Player? {
        val query = Document("username", username)
        return playersCollection.findOne(query)
    }
}