package com.example.premier_league_app.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository _playerRepository){
        this.playerRepository = _playerRepository;
    }

    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    public List<Player> getPlayersByTeam(String teamName){
        return playerRepository.findAll().stream()
                .filter(player -> player.getTeam().equals(teamName))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByName(String searchText){
        return playerRepository.findAll().stream()
                .filter(player -> player.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByPosition(String searchText){
        return playerRepository.findAll().stream()
                .filter(player -> player.getPos().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByNation(String searchText){
        return playerRepository.findAll().stream()
                .filter(player -> player.getNation().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPosition(String team, String position){
        return playerRepository.findAll().stream()
                .filter(player -> player.getTeam().equals(team) && player.getPos().equals(position))
                .collect(Collectors.toList());
    }

    public Player addPlayer(Player player){
        return playerRepository.save(player);
    }

    public Player updatePlayerDetails(Player updatedPlayerInfo){
        Optional<Player> existingPlayerInfo = playerRepository.findByName(updatedPlayerInfo.getName());

        if(existingPlayerInfo.isPresent()){
            Player playerToUpdate = existingPlayerInfo.get();
            playerToUpdate.setTeam(updatedPlayerInfo.getTeam());
            playerToUpdate.setPos(updatedPlayerInfo.getPos());
            playerToUpdate.setNation(updatedPlayerInfo.getNation());

            return playerRepository.save(playerToUpdate);
        }

        return null;
    }

    @Transactional
    public void deletePlayer(String name){
        playerRepository.deleteByName(name);
    }

}
