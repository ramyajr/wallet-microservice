package com.leovegas.wallet.controller;

import com.leovegas.wallet.entities.Transaction;
import com.leovegas.wallet.entities.Wallet;
import com.leovegas.wallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/player/wallet")
public class PlayerWalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping(value = "/{playerId}")
    public ResponseEntity<Wallet> getWalletBalance(@PathVariable(name = "playerId", required = true) final Long playerId) {
        log.info("To fetch wallet balance for the playerId: {}", playerId);
        return new ResponseEntity<>(walletService.getWalletBalance(playerId), HttpStatus.OK);
    }

    @PostMapping(value = "/{playerId}/credit")
    public ResponseEntity<Wallet> performCredit(@PathVariable ("playerId") final Long playerId,
                                                @RequestBody final Transaction transaction) {
        log.info("To perform credit transaction for the playerId: {}", playerId);
        return new ResponseEntity<>(walletService.performCredit(playerId, transaction), HttpStatus.OK);
    }

    @PostMapping(value = "/{playerId}/debit")
    public ResponseEntity<Wallet> performDebit(@PathVariable ("playerId") final Long playerId,
                                               @RequestBody final Transaction transaction) {
        log.info("To perform credit transaction for the playerId: {}", playerId);
        return new ResponseEntity<>(walletService.performDebit(playerId, transaction), HttpStatus.OK);
    }

    @GetMapping(value = "/{playerId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable ("playerId") final Long playerId) {
        log.info("To fetch Transaction history details for the playerId: {}", playerId);
        return new ResponseEntity<>(walletService.getTransactionHistory(playerId), HttpStatus.OK);
    }

}
