package com.example.premier_league_app;

import com.example.premier_league_app.player.Player;
import com.example.premier_league_app.player.PlayerRepository;
import com.example.premier_league_app.player.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void getAllPlayers_Success(){
        Player player1 = new Player();
        player1.setName("testPlayer");
        player1.setNation("brazil");

        Player player2 = new Player();
        player2.setName("testPlayer new");
        player2.setNation("england");

        List<Player> players = List.of(player1, player2);

        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.getAllPlayers();

        assertNotNull(result);
        assertEquals("england", result.get(1).getNation());

    }

    @Test
    void getAllPlayers_NullData(){
        List<Player> players = new ArrayList<>();

        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.getAllPlayers();

        assertEquals(0, result.size());
    }

    @Test
    void getPlayersByTeam_Success(){
        Player player1 = new Player();
        player1.setName("testPlayer");
        player1.setTeam("Man utd");

        List<Player> players = List.of(player1);

        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.getPlayersByTeam("Man utd");

        assertEquals(1, result.size());
    }

    @Test
    void getPlayersByPosition_Success(){
        Player player1 = new Player();
        player1.setName("testPlayer");
        player1.setPos("FW");

        List<Player> players = List.of(player1);

        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.getPlayersByPosition("FW");

        assertNotNull(result);
    }

    @Test
    void getPlayersByPosition_NullPosition(){
        Player player1 = new Player();
        player1.setName("testPlayer");
        player1.setPos(null);

        List<Player> players = List.of(player1);

        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.getPlayersByPosition("FW");

        assertEquals(0, result.size());
    }

    @Test
    void updatePlayerDetails_Success(){
        Player player1 = new Player();
        player1.setName("testPlayer");
        player1.setPos("GK");
        player1.setTeam("man utd");

        Player updatedInfoPlayer1 = new Player();
        updatedInfoPlayer1.setName("testPlayer");
        updatedInfoPlayer1.setPos("GK");
        updatedInfoPlayer1.setTeam("man city");

        when(playerRepository.findByName("testPlayer")).thenReturn(Optional.of(player1));
        when(playerRepository.save(updatedInfoPlayer1)).thenReturn(updatedInfoPlayer1);

        Player result = playerService.updatePlayerDetails(updatedInfoPlayer1);

        assertEquals("GK", result.getPos());
        assertEquals("man city", result.getTeam());

    }

    @Test
    void updatePlayerDetails_NotFound(){
        Player player1 = new Player();
        player1.setName("testPlayer");
        player1.setPos("GK");
        player1.setTeam("man utd");

        Player updatedInfoPlayer1 = new Player();
        updatedInfoPlayer1.setName("testPlayer new");
        updatedInfoPlayer1.setPos("GK");
        updatedInfoPlayer1.setTeam("man city");

        when(playerRepository.findByName("testPlayer new")).thenReturn(Optional.ofNullable(null));

        Player result = playerService.updatePlayerDetails(updatedInfoPlayer1);

        assertNull(result);

    }

}
