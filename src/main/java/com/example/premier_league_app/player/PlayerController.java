package com.example.premier_league_app.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService _playerService){
        this.playerService = _playerService;
    }

    @GetMapping
    public List<Player> getPlayers(
            @RequestParam(required = false) String team,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String nation
    ){
        if(team != null && position != null)
            return playerService.getPlayersByTeamAndPosition(team, position);

        if(team != null)
            return playerService.getPlayersByTeam(team);

        if(position != null)
            return playerService.getPlayersByPosition(position);

        if(name != null)
            return playerService.getPlayersByName(name);

        if(nation != null)
            return playerService.getPlayersByNation(nation);

        return playerService.getAllPlayers();

    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(
            @RequestBody Player player
    ){
        Player newPlayer = playerService.addPlayer(player);

        return new ResponseEntity<>(newPlayer, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Player> updatePlayer(
            @RequestBody Player player
    ){
        Player updatedPlayer = playerService.updatePlayerDetails(player);

        if(updatedPlayer != null)
            return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{playerName}")
    public ResponseEntity<Void> deletePlayer(
            @PathVariable String playerName
    ){
        playerService.deletePlayer(playerName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
