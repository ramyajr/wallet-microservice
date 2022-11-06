package com.leovegas.wallet.entities;

import lombok.*;

import javax.persistence.*;

import com.leovegas.wallet.domain.CurrencyCode;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    /**
     * The id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq")
    @SequenceGenerator(name = "wallet_seq", sequenceName = "wallet_seq", allocationSize = 1)
    private Long id;
    
    /**
     * Player wallet balance
     */
    private Double balance;
    
    /**
     * Player wallet currency Code
     */
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

}
