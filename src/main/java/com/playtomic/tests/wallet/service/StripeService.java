package com.playtomic.tests.wallet.service;

import java.math.BigDecimal;



public interface StripeService {

    /**
     * Charges money in the credit card.
     *
     * Ignore the fact that no CVC or expiration date are provided.
     *
     * @param creditCardNumber The number of the credit card
     * @param amount The amount that will be charged.
     *
     * @throws StripeServiceException
     */
    void charge(String creditCardNumber, BigDecimal amount) throws StripeServiceException;


    /**
     * Refunds the specified payment.
     *
     * @param paymentId ID wallet to do a refund
     */
    void refund(String paymentId) throws StripeServiceException;



}
