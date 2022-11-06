package com.leovegas.wallet.service;

import com.leovegas.wallet.entities.Player;
import com.leovegas.wallet.entities.Transaction;
import com.leovegas.wallet.entities.Wallet;
import com.leovegas.wallet.domain.TransactionType;
import com.leovegas.wallet.exception.EntityNotFoundException;
import com.leovegas.wallet.exception.InvalidTransactionException;
import com.leovegas.wallet.repository.PlayerRepository;
import com.leovegas.wallet.repository.TransactionRepository;
import com.leovegas.wallet.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PlayerDetailsService playerService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Wallet getWalletBalance(Long playerId) {
        Player player =playerService.getPlayer(playerId);
        return getWallet(playerId, player);
    }

    @Override
    @Transactional
    public Wallet performCredit(Long playerId, Transaction transaction) {
        Player player = getPlayer(playerId);
        Wallet playerWallet = getWallet(playerId, player);
        validateTransaction(transaction, player, TransactionType.CREDIT);

        playerWallet.setBalance(playerWallet.getBalance() + transaction.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());
        walletRepository.saveAndFlush(playerWallet);

        player.getTransactions().add(transaction);
        playerRepository.saveAndFlush(player);
        log.debug("walletId: {}, is credited for the playerId: {}", playerWallet.getId(), player.getId());
        return playerWallet;
    }

    @Override
    @Transactional
    public Wallet performDebit(Long playerId, Transaction transaction) {
        Player player =playerService.getPlayer(playerId);
        Wallet playerWallet = getWallet(playerId, player);
        validateTransaction(transaction, player, TransactionType.DEBIT);

        playerWallet.setBalance(playerWallet.getBalance() - transaction.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());
        walletRepository.saveAndFlush(playerWallet);

        player.getTransactions().add(transaction);
        playerRepository.saveAndFlush(player);
        log.debug("walletId: {}, is debited for the playerId: {}", playerWallet.getId(), player.getId());
        return playerWallet;
    }

    @Override
    public List<Transaction> getTransactionHistory(Long playerId) {
        Player player = getPlayer(playerId);
        return player.getTransactions();
    }

    /**
     * Get player for the playerId else throw error.
     *
     * @param playerId
     * @return
     */
    private Player getPlayer(Long playerId) {
        Player player =playerService.getPlayer(playerId);
        if(null == player)
            throw new EntityNotFoundException("Given player id is not abvailable : "+ playerId);

        return player;
    }

    /**
     * Get player wallet for the playerId
     *
     * @param playerId
     * @param player
     * @return
     */
    private Wallet getWallet(Long playerId, Player player) {
        Wallet playerWallet = player.getWallet();
        if(null == playerWallet)
            throw new EntityNotFoundException("Player wallet is not available for the given player id "+ playerId);

        return playerWallet;
    }

    /**
     * Method to validate the Transaction
     *
     * @param transaction
     * @param player
     * @param transactionType
     */
    private void validateTransaction(Transaction transaction, Player player, TransactionType transactionType) {
        if(null != transaction.getId() &&  transactionRepository.findById(transaction.getId()).isPresent())
            throw new InvalidTransactionException("Invalid transaction id to perform " + transactionType +
                    " for the playerId: "+ player.getId());

        if(transaction.getCurrencyCode() != player.getWallet().getCurrencyCode())
            throw new InvalidTransactionException("Invalid Currency Code to perform " + transactionType +
                    " for the playerId: "+ player.getId());

        if(transaction.getTransactionType() != transactionType)
            throw new InvalidTransactionException("Invalid Transaction type to perform " + transactionType +
                    " transaction for the playerId: "+ player.getId());

        if(transaction.getAmount() <= 0d)
            throw new InvalidTransactionException("Invalid transaction amount to perform " + transactionType +
                    " transaction for the playerId: "+ player.getId());

        if((transaction.getTransactionType() == TransactionType.DEBIT) && (player.getWallet().getBalance() < transaction.getAmount()))
            throw new InvalidTransactionException("Invalid transaction due to insufficient balance in the wallet " +
                    "for the playerId: "+ player.getId());
    }
}
