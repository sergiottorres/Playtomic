package com.playtomic.tests.wallet.core;

import java.math.BigDecimal;

import com.playtomic.tests.wallet.domain.Wallet;

import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepositoryCustom  {

    Wallet findAndUpdateWalletById(Wallet wallet, BigDecimal amount);

}
