package com.leovegas.wallet.service;

import com.leovegas.wallet.entities.Player;


public interface PlayerDetailsService {


    /**
     * Method to create the Player entity.
     *
     * @param player
     * @return Player
     */
    public Player createPlayer(Player player) ;

    /**
     * Method to Fetch the Player entity.
     *
     * @param id
     * @return
     */
    public Player getPlayer(final Long id);

}
