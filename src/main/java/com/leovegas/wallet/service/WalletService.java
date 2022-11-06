package com.leovegas.wallet.service;

import com.leovegas.wallet.entities.Transaction;
import com.leovegas.wallet.entities.Wallet;

import java.util.List;


public interface WalletService {

    /**
     * Method to fetch wallet balance for the player.
     *
     * @param playerId
     * @return
     */
    public Wallet getWalletBalance(final Long playerId);

    /**
     * Method to do credit transaction to the player wallet.
     *
     * @param playerId
     * @return
     */
    public Wallet performCredit(final Long playerId, final Transaction transaction);

    /**
     * Method to do debit transaction from the player account
     *
     * @param playerId
     * @return
     */
    public Wallet performDebit(final Long playerId, final Transaction transaction);

    /**
     * Method to fetch the transaction history of the player.
     *
     * @param playerId
     * @return
     */
    public List<Transaction> getTransactionHistory(final Long playerId);

}
