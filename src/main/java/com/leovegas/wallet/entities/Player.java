package com.leovegas.wallet.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq")
	@SequenceGenerator(name = "player_seq", sequenceName = "player_seq", allocationSize = 1)
	private Long id;

	/**
	 * Player first Name
	 */
	private String firstName;

	/**
	 * Player last Name
	 */
	private String lastName;

	/**
	 * Player wallet
	 */
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Wallet wallet;

	/**
	 * Player Transactions
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "player_id")
	private List<Transaction> transactions;

}
