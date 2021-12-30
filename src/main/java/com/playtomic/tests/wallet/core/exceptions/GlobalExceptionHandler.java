package com.playtomic.tests.wallet.core.exceptions;

import com.playtomic.tests.wallet.service.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.StripeServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * Class for handler error/exception controllers
 *
 * @author Sergio Torres 2021.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * Handler for bad request (example: amount < 10)
     *
     * @param e exception
     * @return response HTTP 400
     */
    @SuppressWarnings("unused")
    @ExceptionHandler({StripeAmountTooSmallException.class})
    public ResponseEntity<Void> exceptionHandlerBadStrip(final Throwable e) {
        log.warn("Exception in REST service, amount is too low", e);
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Fail from third-party system generic
     *
     * @param e excepción
     * @return response HTTP 424
     */
    @SuppressWarnings("unused")
    @ExceptionHandler({StripeServiceException.class})
    public ResponseEntity<Void> exceptionHandlerErrorStrip(final Throwable e) {
        log.warn("Exception in REST service", e);
        return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
    }


}
