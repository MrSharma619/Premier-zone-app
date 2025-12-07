package com.example.premier_league_app;

import com.example.premier_league_app.player.Player;
import com.example.premier_league_app.player.PlayerController;
import com.example.premier_league_app.player.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerService playerService;

    @Test
    void getPlayers_All_Success() throws Exception {
        Player player1 = new Player();
        player1.setName("testPlayer");
        player1.setNation("brazil");

        Player player2 = new Player();
        player2.setName("testPlayer new");
        player2.setNation("england");

        List<Player> players = List.of(player1, player2);

        when(playerService.getAllPlayers()).thenReturn(players);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/player"))
                .andExpect(status().is2xxSuccessful());

        verify(playerService, times(1)).getAllPlayers();
    }

    @Test
    void getPlayers_TeamAndPosition_Success() throws Exception {
        Player player1 = new Player();
        player1.setName("testPlayer");
        player1.setTeam("team1");
        player1.setPos("FW");

        List<Player> players = List.of(player1);

        when(playerService.getPlayersByTeamAndPosition("team1", "FW")).thenReturn(players);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/player")
                .param("team", "team1")
                .param("position", "FW"))
                .andExpect(status().is2xxSuccessful());

        verify(playerService, times(1)).getPlayersByTeamAndPosition("team1", "FW");
    }

    @Test
    void getPlayers_Nation_Success() throws Exception {
        Player player1 = new Player();
        player1.setName("testPlayer");
        player1.setNation("nation1");

        List<Player> players = List.of(player1);

        when(playerService.getPlayersByNation("nation1")).thenReturn(players);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/player")
                        .param("nation", "nation1"))
                .andExpect(status().is2xxSuccessful());

        verify(playerService, times(1)).getPlayersByNation("nation1");
    }

    @Test
    void addPlayer_Success() throws Exception {
        Player player1 = new Player();
        player1.setName("testPlayer");
        player1.setNation("nation1");

        when(playerService.addPlayer(player1)).thenReturn(player1);

        mockMvc.perform(post("/api/v1/player")
                        .contentType(MediaType.APPLICATION_JSON)                //important
                        .content(new ObjectMapper().writeValueAsString(player1)))
                .andExpect(status().is2xxSuccessful());

        verify(playerService, times(1)).addPlayer(player1);
    }

    @Test
    void deletePlayer_Success() throws Exception {
        doNothing().when(playerService).deletePlayer("player1");           //important

        mockMvc.perform(delete("/api/v1/player/{name}", "player1"))
                .andExpect(status().isNoContent());

        verify(playerService, times(1)).deletePlayer("player1");
    }

}
