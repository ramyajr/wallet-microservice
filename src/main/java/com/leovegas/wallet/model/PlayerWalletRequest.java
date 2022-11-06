package com.leovegas.wallet.model;

import com.leovegas.wallet.domain.CurrencyCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(description = "Create wallet for a Player")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerWalletRequest {

    /** The Player id */
    @ApiModelProperty(notes = "Player id", required = true, position = 1)
    private Long playerId;


    /** The currency Code */
    @ApiModelProperty(notes = "Currency Code", required = true, position = 2)
    private CurrencyCode currencyCode;

}
