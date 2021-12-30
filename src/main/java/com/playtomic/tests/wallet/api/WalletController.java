package com.playtomic.tests.wallet.api;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.playtomic.tests.wallet.domain.ChargeRequest;
import com.playtomic.tests.wallet.domain.Messages;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.StripeServiceException;
import com.playtomic.tests.wallet.service.WalletService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API Wallet Controller, test backend position
 *
 * @author Sergio Torres 2021.
 */

@RestController
public class WalletController {


    private static final Logger LOG = LoggerFactory.getLogger(WalletController.class);
    private static final String NOT_FOUND_ID = "messages.wallet.notfound.id";

    private final StripeService stripeService;
    private final WalletService walletService;
    private final Messages messages;


    @Autowired
    public WalletController(StripeService stripeService, WalletService walletService, Messages messages) {
        this.stripeService = stripeService;
        this.walletService = walletService;
        this.messages = messages;
    }

    @RequestMapping("/")
    public void log() {
        LOG.info("Logging from /");
    }

    /** Get a wallet using a identifier
     *
     * @param id Identifier from the wallet
     * @return wallet
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Wallet> getWalletId(@PathVariable @NotNull String id) {
        final Wallet wallet = walletService.findById(id);
        if (wallet == null) {
            if (LOG.isInfoEnabled()) {
                LOG.info(messages.get(NOT_FOUND_ID));
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    /** Create a wallet (use personal)
     *
     * @return wallet
     */
    @PostMapping(value = "/create")
    public ResponseEntity<Wallet> createWallet() {
        final Wallet wallet = walletService.create();
        if (wallet == null) {

            LOG.info(messages.get(NOT_FOUND_ID));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    /** Top-up money in a wallet using a credit card number
     *
     * @param id Identifier from the wallet
     * @return wallet
     */
    @PostMapping(value = "/recharge/{id}")
    public ResponseEntity<Wallet> rechargeWallet(@PathVariable @NotNull String id,
                                                 @RequestBody ChargeRequest chargeRequest) {
        final Wallet wallet = walletService.findById(id);
        if (wallet == null) {
            LOG.info(messages.get(NOT_FOUND_ID));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }else{
            BigDecimal amount = new BigDecimal(chargeRequest.getAmount());
            stripeService.charge(chargeRequest.getCreditCardNumber(), amount);
            walletService.findAndUpdateWalletById(wallet, amount);
            return new ResponseEntity<>(wallet, HttpStatus.OK);


        }
    }


}
