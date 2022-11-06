package com.leovegas.wallet.repository;


import com.leovegas.wallet.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlayerRepository extends JpaRepository<Player, Long> {

}
