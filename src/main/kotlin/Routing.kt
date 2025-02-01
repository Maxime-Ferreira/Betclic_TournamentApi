package com.tournament

import com.tournament.models.Player
import com.tournament.services.PlayerService
import com.tournament.services.TournamentService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configurePlayersRouting(){
    val playerService = PlayerService()

    routing {
        post("/player") {
            val player = call.receive<Player>()
            playerService.addPlayer(player)
            call.respond(HttpStatusCode.Created, player)
        }
    }
}

fun Application.configureTournamentRouting() {
    val playerService = PlayerService()
    val tournamentService = TournamentService(playerService)

    routing {
        post("/tournaments") {
            val request = call.receive<Map<String, String>>()
            val name = request["name"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Tournament name required")
            tournamentService.createTournament(name)
            call.respond(HttpStatusCode.Created, "Tournament created successfully")
        }

        post("/tournaments/{tournamentId}/players") {
            val tournamentId = call.parameters["tournamentId"]
            val request = call.receive<Map<String, String>>()
            val username = request["username"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Username required")

            val success = tournamentService.addPlayerToTournament(tournamentId ?: "", username)
            if (success) {
                call.respond(HttpStatusCode.Created, "Player added to tournament")
            } else {
                call.respond(HttpStatusCode.NotFound, "Error ! Check tournamentId or player username")
            }
        }

        put("/tournaments/{tournamentId}/players/{playerId}/points") {
            val tournamentId = call.parameters["tournamentId"]
            val playerId = call.parameters["playerId"]
            val request = call.receive<Map<String, Int>>()
            val points = request["points"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Points required")

            val success = tournamentService.updatePlayerPoints(tournamentId ?: "", playerId ?: "", points)
            if (success) {
                call.respond(HttpStatusCode.OK, "Player points updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "Tournament or player not found")
            }
        }

        get("/tournaments/{tournamentId}/players") {
            val tournamentId = call.parameters["tournamentId"]
            val players = tournamentService.getRankedPlayers(tournamentId ?: "")
            if (players != null) {
                call.respond(HttpStatusCode.OK, players)
            } else {
                call.respond(HttpStatusCode.NotFound, "Tournament not found")
            }
        }

        delete("/tournaments/{tournamentId}/players") {
            val tournamentId = call.parameters["tournamentId"]
            val success = tournamentService.deleteAllPlayersFromTournament(tournamentId ?: "")
            if (success) {
                call.respond(HttpStatusCode.OK, "All players removed from tournament")
            } else {
                call.respond(HttpStatusCode.NotFound, "Tournament not found")
            }
        }
    }
}