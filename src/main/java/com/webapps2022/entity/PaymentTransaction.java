package com.webapps2022.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
@NamedQuery(name="findAllTransaction", query="SELECT c FROM PaymentTransaction c ")
@Entity
public class PaymentTransaction implements Serializable {
    
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @NotNull
    String sendUsername;
    
    @NotNull
    String sendCurrency;
    
    @NotNull
    Double sendCash;
    
    @NotNull
    Double exchangeRate;
    
    @NotNull
    String receiveUsername;
    
    @NotNull
    String receiveCurrency;
    
    @NotNull
    Double receiveCash;
    
    @NotNull
    Boolean pending;
    
    @NotNull
    Boolean approved;

    public PaymentTransaction() {
    }

    public PaymentTransaction(String sendUsername, String sendCurrency, Double sendCash, Double exchangeRate, String receiveUsername, String receiveCurrency, Double receiveCash, Boolean pending, Boolean approved) {
        this.sendUsername = sendUsername;
        this.sendCurrency = sendCurrency;
        this.sendCash = sendCash;
        this.exchangeRate = exchangeRate;
        this.receiveUsername = receiveUsername;
        this.receiveCurrency = receiveCurrency;
        this.receiveCash = receiveCash;
        this.pending = pending;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSendUsername() {
        return sendUsername;
    }

    public void setSendUsername(String sendUsername) {
        this.sendUsername = sendUsername;
    }

    public String getSendCurrency() {
        return sendCurrency;
    }

    public void setSendCurrency(String sendCurrency) {
        this.sendCurrency = sendCurrency;
    }

    public Double getSendCash() {
        return sendCash;
    }

    public void setSendCash(Double sendCash) {
        this.sendCash = sendCash;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getReceiveUsername() {
        return receiveUsername;
    }

    public void setReceiveUsername(String receiveUsername) {
        this.receiveUsername = receiveUsername;
    }

    public String getReceiveCurrency() {
        return receiveCurrency;
    }

    public void setReceiveCurrency(String receiveCurrency) {
        this.receiveCurrency = receiveCurrency;
    }

    public Double getReceiveCash() {
        return receiveCash;
    }

    public void setReceiveCash(Double receiveCash) {
        this.receiveCash = receiveCash;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
    
}
