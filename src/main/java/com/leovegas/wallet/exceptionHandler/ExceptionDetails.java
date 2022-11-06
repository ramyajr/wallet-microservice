package com.leovegas.wallet.exceptionHandler;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * ExceptionDetails POJO class.
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExceptionDetails {

	private Timestamp timeStamp;
	private int status;
	private String message;
	private String details;

}
