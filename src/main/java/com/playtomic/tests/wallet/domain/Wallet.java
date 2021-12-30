package com.playtomic.tests.wallet.domain;


import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Object Wallet
 *
 * @author Sergio Torres 2021
 */

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document("wallet")
public class Wallet {

    @JsonProperty
    @Id
    private String id;

    @JsonProperty
    @Field
    @NotNull
    private Double currentBalance;

    /**
     * Constructor.
     *
     * @return Wallet created with initial balance 0
     */
    public Wallet() {
        this.currentBalance = 0.0;
    }



}
