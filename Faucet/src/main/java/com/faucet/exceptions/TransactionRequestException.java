package com.faucet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid transaction request. Try later.")
public class TransactionRequestException extends Exception {
}
