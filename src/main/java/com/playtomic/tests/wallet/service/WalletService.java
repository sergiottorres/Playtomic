package com.playtomic.tests.wallet.service;


import java.math.BigDecimal;

import com.playtomic.tests.wallet.domain.Wallet;

public interface WalletService {

    /**
     * Search a Wallet by ID
     *
     * @param paymentId ID wallet to search
     */
    Wallet findById(String paymentId) throws StripeServiceException;

    /**
     * Search a Wallet by ID
     */
    Wallet create() throws StripeServiceException;

    /**
     * Update a Wallet
     *
     * @param wallet Wallet to update
     */

    Wallet findAndUpdateWalletById(Wallet wallet, BigDecimal amount);

}



