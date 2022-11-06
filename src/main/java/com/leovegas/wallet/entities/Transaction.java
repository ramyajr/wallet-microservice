package com.leovegas.wallet.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.leovegas.wallet.domain.CurrencyCode;
import com.leovegas.wallet.domain.TransactionType;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trans_seq")
	@SequenceGenerator(name = "trans_seq", sequenceName = "trans_seq", allocationSize = 1)
	private Long id;

	/**
	 * The Transaction Type.
	 */
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	/**
	 * The CurrencyCode.
	 */
	@Enumerated(EnumType.STRING)
	private CurrencyCode currencyCode;

	/**
	 * The Amount.
	 */
	private Long amount;

	/**
	 * The Transaction date.
	 */
	private LocalDateTime transactionDate;

}
