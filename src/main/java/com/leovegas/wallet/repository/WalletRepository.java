package com.leovegas.wallet.repository;


import com.leovegas.wallet.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
