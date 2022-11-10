package com.leovegas.wallet;

import com.leovegas.wallet.entities.Player;
import com.leovegas.wallet.entities.Transaction;
import com.leovegas.wallet.entities.Wallet;
import com.leovegas.wallet.domain.CurrencyCode;
import com.leovegas.wallet.domain.TransactionType;
import com.leovegas.wallet.exception.InvalidTransactionException;
import com.leovegas.wallet.repository.PlayerRepository;
import com.leovegas.wallet.repository.WalletRepository;
import com.leovegas.wallet.service.PlayerDetailsServiceImpl;
import com.leovegas.wallet.service.WalletServiceImpl;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class WalletServiceImplTest {

	/** Set up test values. */
	private static final Long TEST_PLAYER_ID = Long.valueOf(101);
	private static final Long TEST_WALLET_ID = Long.valueOf(501);
	private static final Long TEST_AMOUNT = Long.valueOf(500);

	@InjectMocks
	private WalletServiceImpl walletService;

	@Mock
	private PlayerDetailsServiceImpl playerService;

	@Mock
	private WalletRepository walletRepository;

	@Mock
	private PlayerRepository playerRepository;

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	/**
	 * Shows successful credit transaction into a player's wallet
	 */
	@Test
	public void shouldCreditPlayerWallet() {
		Player player = getPlayerWithWallet();
		given(playerService.getPlayer(isA(Long.class))).willReturn(player);
		given(walletRepository.saveAndFlush(isA(Wallet.class))).willReturn(getWallet());
		given(playerRepository.saveAndFlush(isA(Player.class))).willReturn(player);

		Wallet wallet = this.walletService.performCredit(TEST_PLAYER_ID, getCreditTransaction());

		assertNotNull("Wallet should not be null", wallet);
		assertEquals("Wallet should be credited with amount", TEST_AMOUNT, wallet.getBalance(), 0d);

		verify(playerRepository, times(1)).saveAndFlush(isA(Player.class));
		verify(walletRepository, times(1)).saveAndFlush(isA(Wallet.class));
		verifyNoMoreInteractions(playerRepository);
		verifyNoMoreInteractions(walletRepository);
	}

	/**
	 * Throws Exception for passing Invalid CurrencyCode during transaction.
	 */
	@Test(expected = InvalidTransactionException.class)
	public void throwsException_ForInvalidCurrCode() {
		Player player = getPlayerWithWallet();
		given(playerService.getPlayer(isA(Long.class))).willReturn(player);
		Transaction transaction = getCreditTransaction();
		transaction.setCurrencyCode(CurrencyCode.EUR);
		Wallet wallet = this.walletService.performCredit(TEST_PLAYER_ID, transaction);

		verify(playerRepository, times(0)).saveAndFlush(isA(Player.class));
		verify(walletRepository, times(0)).saveAndFlush(isA(Wallet.class));
		verifyNoMoreInteractions(playerRepository);
		verifyNoMoreInteractions(walletRepository);
	}

	/**
	 * Throws Exception for passing Invalid Transaction type during transaction.
	 */
	@Test
	public void throwsException_ForInvalidTransType() {
		Player player = getPlayerWithWallet();
		given(playerService.getPlayer(isA(Long.class))).willReturn(player);
		Transaction transaction = getCreditTransaction();
		transaction.setTransactionType(TransactionType.DEBIT);
		try {
		Wallet wallet = this.walletService.performCredit(TEST_PLAYER_ID, transaction);
		} catch(Exception e) {
			assertEquals(e.getMessage(),"Invalid Transaction type to perform " + TransactionType.CREDIT +
                    " transaction for the playerId: "+ player.getId());
		}
		verify(playerRepository, times(0)).saveAndFlush(isA(Player.class));
		verify(walletRepository, times(0)).saveAndFlush(isA(Wallet.class));
		verifyNoMoreInteractions(playerRepository);
		verifyNoMoreInteractions(walletRepository);
	}

	/**
	 * Shows successful debit transaction from a player's wallet
	 */
	@Test
	public void shouldDebitPlayerWallet() {
		Player player = getPlayerWithWallet();
		player.getWallet().setBalance(1000d);
		given(playerService.getPlayer(isA(Long.class))).willReturn(player);
		given(walletRepository.saveAndFlush(isA(Wallet.class))).willReturn(getWallet());
		given(playerRepository.saveAndFlush(isA(Player.class))).willReturn(player);

		Wallet wallet = this.walletService.performDebit(TEST_PLAYER_ID, getDebitTransaction());

		assertNotNull("Wallet should not be null", wallet);
		assertEquals("Wallet should be debited with amount", TEST_AMOUNT, wallet.getBalance(), 0d);

		verify(playerRepository, times(1)).saveAndFlush(isA(Player.class));
		verify(walletRepository, times(1)).saveAndFlush(isA(Wallet.class));
		verifyNoMoreInteractions(playerRepository);
		verifyNoMoreInteractions(walletRepository);
	}

	/**
	 * Throws Exception for passing more amount than available Balance during debit
	 * transaction.
	 */
	@Test
	public void throwsException_ForInsufficientBalance() {
		Player player = getPlayerWithWallet();
		given(playerService.getPlayer(isA(Long.class))).willReturn(player);
		Transaction transaction = getDebitTransaction();
		transaction.setAmount(TEST_AMOUNT);
		try {
			Wallet wallet = this.walletService.performDebit(TEST_PLAYER_ID, transaction);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Invalid transaction due to insufficient balance in the wallet " +
                    "for the playerId: "+ player.getId());
		}
		verify(playerRepository, times(0)).saveAndFlush(isA(Player.class));
		verify(walletRepository, times(0)).saveAndFlush(isA(Wallet.class));
		verifyNoMoreInteractions(playerRepository);
		verifyNoMoreInteractions(walletRepository);
	}

	/**
	 * Method to get a Player with Wallet instance
	 *
	 * @return
	 */
	private Player getPlayerWithWallet() {
		return Player.builder().id(TEST_PLAYER_ID).wallet(getWallet()).transactions(new ArrayList<>()).build();
	}

	/**
	 * Method to get a Wallet instance
	 *
	 * @return
	 */
	private Wallet getWallet() {
		return Wallet.builder().id(TEST_WALLET_ID).balance(0d).currencyCode(CurrencyCode.USD).build();
	}

	/**
	 * Method to get a debit Transaction instance
	 *
	 * @return
	 */
	private Transaction getDebitTransaction() {
		return Transaction.builder().amount(TEST_AMOUNT).currencyCode(CurrencyCode.USD)
				.transactionType(TransactionType.DEBIT).build();
	}

	/**
	 * Method to get a credit Transaction instance
	 *
	 * @return
	 */
	private Transaction getCreditTransaction() {
		return Transaction.builder().amount(TEST_AMOUNT).currencyCode(CurrencyCode.USD)
				.transactionType(TransactionType.CREDIT).build();
	}

}
