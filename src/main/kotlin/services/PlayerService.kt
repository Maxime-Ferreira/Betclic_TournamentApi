package com.tournament.services

import com.tournament.ConfigLoader
import com.tournament.models.Player
import org.bson.Document
import org.litote.kmongo.KMongo
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class PlayerService {
    private val client = KMongo.createClient(ConfigLoader.mongoUri)
    private val database = client.getDatabase(ConfigLoader.mongoDbName)
    private val playersCollection = database.getCollection<Player>("players")

    fun addPlayer(player: Player) {
        playersCollection.insertOne(player)
    }

    fun getUserByUsername(username: String): Player? {
        val query = Document("username", username)
        return playersCollection.findOne(query)
    }
}