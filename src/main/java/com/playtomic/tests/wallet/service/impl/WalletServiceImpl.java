package com.playtomic.tests.wallet.service.impl;


import java.math.BigDecimal;

import com.playtomic.tests.wallet.core.WalletRepository;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.service.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {


    private final WalletRepository walletRepository;


    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;

    }

    @Override
    public Wallet findById(String paymentId){
        return walletRepository.findById(paymentId).orElse(null);
    }

    @Override
    public Wallet create(){
        return walletRepository.save(new Wallet());
    }

    @Override
    public Wallet findAndUpdateWalletById(Wallet wallet, BigDecimal amount){
       return walletRepository.findAndUpdateWalletById(wallet, amount);
    }

}
