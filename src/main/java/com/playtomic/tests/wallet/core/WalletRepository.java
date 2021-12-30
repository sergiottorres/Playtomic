package com.playtomic.tests.wallet.core;


import com.playtomic.tests.wallet.domain.Wallet;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String>, WalletRepositoryCustom {


}
