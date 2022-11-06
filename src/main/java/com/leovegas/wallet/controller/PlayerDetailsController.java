package com.leovegas.wallet.controller;


import com.leovegas.wallet.entities.Player;
import com.leovegas.wallet.service.PlayerDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/player")
public class PlayerDetailsController {

    @Autowired
    private PlayerDetailsService playerDetailsService;

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        log.info("Creating a new player");
        return new ResponseEntity<>(playerDetailsService.createPlayer(player), HttpStatus.CREATED);
    }

}
