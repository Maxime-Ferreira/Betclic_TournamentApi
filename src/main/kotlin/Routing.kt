package com.tournament

import com.tournament.services.PlayerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.Player

fun Application.configureRouting(playerService: PlayerService) {
    routing {
        get("/players"){
            call.respond(HttpStatusCode.OK, playerService.getAllPlayers())
        }

        get("/players/{id}") {
            val id = call.parameters["id"]
            val player = playerService.getPlayerById(id ?: "")
            if (player != null) {
                call.respond(HttpStatusCode.OK, player)
            } else {
                call.respond(HttpStatusCode.NotFound, "Player not found")
            }
        }

        post("/players") {
            val player = call.receive<Player>()
            playerService.addPlayer(player)
            call.respond(HttpStatusCode.Created, player)
        }

        put("/players/{id}") {
            val id = call.parameters["id"]
            val updatedPlayer = call.receive<Player>()
            val success = playerService.updatePlayer(id ?: "", updatedPlayer)
            if (success) {
                call.respond(HttpStatusCode.OK, "Player updated successfully")
            } else {
                call.respond(HttpStatusCode.NotFound, "Player not found")
            }
        }

        delete("/players") {
            val success = playerService.deleteAllPlayers()
            if (success) {
                call.respond(HttpStatusCode.OK, "All players deleted successfully")
            } else {
                call.respond(HttpStatusCode.NotFound, "No players to delete")
            }
        }
    }
}
