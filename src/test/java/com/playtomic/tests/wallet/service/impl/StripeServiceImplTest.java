package com.playtomic.tests.wallet.service.impl;


import java.math.BigDecimal;
import java.net.URI;

import com.playtomic.tests.wallet.service.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.StripeRestTemplateResponseErrorHandler;
import com.playtomic.tests.wallet.service.StripeServiceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

/**
 * This test is failing with the current implementation.
 *
 * How would you test this?
 */
public class StripeServiceImplTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private MockRestServiceServer mockServer;


    URI testUri = URI.create("http://how-would-you-test-me.localhost");
    StripeServiceImpl s = new StripeServiceImpl(testUri, testUri, new RestTemplateBuilder());;

    @BeforeEach
    public void setUp(){
        restTemplateBuilder = mock(RestTemplateBuilder.class);
        Mockito.reset(restTemplateBuilder);


        StripeRestTemplateResponseErrorHandler responseErrorHandler = new StripeRestTemplateResponseErrorHandler();
        RestTemplateBuilder restTemplateBuilder2 = new RestTemplateBuilder().errorHandler(responseErrorHandler);
        RestTemplate restTemplate = restTemplateBuilder2.build();

        when(restTemplateBuilder.errorHandler(any())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        s = new StripeServiceImpl(testUri, testUri, restTemplateBuilder);
        mockServer = MockRestServiceServer.createServer(restTemplate);


    }

    @Test
    public void test_exception() {

        mockServer.expect(requestTo(testUri)).andExpect(method(HttpMethod.POST)).andRespond(MockRestResponseCreators.withStatus(HttpStatus.UNPROCESSABLE_ENTITY));
        Assertions.assertThrows(StripeAmountTooSmallException.class, () -> {
            s.charge("4242 4242 4242 4242", new BigDecimal(5));
        });
    }

    @Test
    public void test_ok() throws StripeServiceException {

        mockServer.expect(requestTo(testUri)).andExpect(method(HttpMethod.POST)).andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK));
        s.charge("4242 4242 4242 4242", new BigDecimal(15));
    }

}
