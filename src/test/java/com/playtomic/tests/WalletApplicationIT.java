package com.playtomic.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtomic.tests.wallet.api.WalletController;
import com.playtomic.tests.wallet.domain.ChargeRequest;
import com.playtomic.tests.wallet.domain.Messages;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.WalletService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(profiles = "test")

public class WalletApplicationIT {

	private static final String ID_WALLET = "idType";
	private static final String ID_CREDIT_CARD = "4242-4242-4242-4242";
	private static final String AMOUNT = "15";

	@Mock
	private Messages messages;


	@Mock
	private StripeService stripeService;

	@Mock
	private WalletService walletService;

	@Mock
	private ObjectMapper objectMapper;


	private WalletController walletController;

	private Wallet wallet;


	@Before
	public void setUp() {

		stripeService = mock(StripeService.class);
		Mockito.reset(stripeService);

		walletService = mock(WalletService.class);
		Mockito.reset(walletService);

		messages = mock(Messages.class);
		Mockito.reset(messages);
		wallet = createWallet();

		walletController = new WalletController(stripeService, walletService, messages);

	}

	@Test
	public void searchOKWallet() {

		when(walletService.findById(anyString())).thenReturn(wallet);

		ResponseEntity<Wallet> responseEntity = walletController.getWalletId(ID_WALLET);

		Mockito.verify(walletService).findById(Mockito.eq(ID_WALLET));
		Assert.assertEquals("Response has to be 200 OK", HttpStatus.OK, responseEntity.getStatusCode());


	}


	@Test
	public void searchWalletKO() {
		when(walletService.findById(anyString())).thenReturn(null);

		ResponseEntity<Wallet> responseEntity = walletController.getWalletId(ID_WALLET);

		Mockito.verify(walletService).findById(Mockito.eq(ID_WALLET));
		Assert.assertEquals("Response has to be 200 OK", HttpStatus.NOT_FOUND, responseEntity.getStatusCode());


	}

	@Test
	public void findAndUpdateWalletByIdOK() {

		Wallet wallet = createWallet();
		ChargeRequest chargeRequest = new ChargeRequest(ID_CREDIT_CARD, AMOUNT);
		when(walletService.findById(anyString())).thenReturn(wallet);
		when(walletService.findAndUpdateWalletById(any(), any())).thenReturn(wallet);

		ResponseEntity<Wallet> responseEntity = walletController.rechargeWallet(ID_WALLET, chargeRequest);

		Assert.assertEquals("Response has to be 200 OK", HttpStatus.OK, responseEntity.getStatusCode());


	}


	@Test
	public void findAndUpdateWalletByIdKONotFoundID() {
		Wallet wallet = createWallet();
		ChargeRequest chargeRequest = new ChargeRequest(ID_CREDIT_CARD, AMOUNT);
		when(walletService.findById(anyString())).thenReturn(null);
		when(walletService.findAndUpdateWalletById(any(), any())).thenReturn(wallet);

		ResponseEntity<Wallet> responseEntity = walletController.rechargeWallet(ID_WALLET, chargeRequest);
		Assert.assertEquals("Response has to be 404 NOT FOUND", HttpStatus.NOT_FOUND, responseEntity.getStatusCode());


	}

	private Wallet createWallet(){
		Wallet wallet = new Wallet();
		wallet.setId(ID_WALLET);
		return wallet;
	}







}
