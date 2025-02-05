This project was created to run on Linux or on WSL if you are on Windows because the bash file uses a mongodb Docker container. It means that your Linux or WSL must have Docker installed.
If docker is not installed, here is a process to install it : https://woshub.com/install-docker-windows-wsl2/
To launch the application on Windows, you would have to modify the script to launch mongodb via a WSL or create a MongoAtlas database.

There are 2 collections :
- players
- tournaments

Here are the endpoints: 
- (POST) http://localhost:8080/player
This endpoint can create a player. Exemple body request : { "username": "xxx" }
- (POST) http://localhost:8080/tournaments
This endpoint can create a tournament. Exemple body request : { "name": "xxx" }
- (POST) http://localhost:8080/tournaments/{tournamentId}/players
This endpoint can add an existing player to a tournament. Exemple body request : { "username": "xxx" }
- (PUT) http://localhost:8080/tournaments/{tournamentId}/players/{playerId}/points
This endpoint can update a player's points in a tournament. Exemple body request : { "points": 123 }
- (GET) http://localhost:8080/tournaments/{tournamentId}/players
This endpoint can show you the players sorted by points and display the rank of each player.
- (DELETE) http://localhost:8080/tournaments/{tournamentId}/players
This endpoint can delete all players from a tournament.
