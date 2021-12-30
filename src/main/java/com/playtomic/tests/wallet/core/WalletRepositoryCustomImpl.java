package com.playtomic.tests.wallet.core;

import java.math.BigDecimal;

import com.playtomic.tests.wallet.domain.Wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class WalletRepositoryCustomImpl implements WalletRepositoryCustom {

    private final MongoOperations operations;

    @Autowired
    public WalletRepositoryCustomImpl(MongoOperations operations) {

        this.operations = operations;
    }

    @Override
    public Wallet findAndUpdateWalletById(Wallet wallet, BigDecimal amount){
        Query query = new Query(Criteria.where("_id").is(wallet.getId()));
        Update update = new Update().inc("currentBalance", amount.doubleValue());
        return operations.findAndModify(query, update, Wallet.class);

    }
}
