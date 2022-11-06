package com.leovegas.wallet.service;

import com.leovegas.wallet.entities.Player;
import com.leovegas.wallet.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class PlayerDetailsServiceImpl implements PlayerDetailsService{

    @Autowired
    private PlayerRepository playerRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Player createPlayer(Player player) {
        log.debug("creating a new player entity.");
        return playerRepository.saveAndFlush(player);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Player getPlayer(Long id) {
        log.debug("Fetching the player details for the id {}", id);
        Optional<Player> players = playerRepository.findById(id);
        if (players.isPresent())
            return players.get();

        return null;
    }

}
