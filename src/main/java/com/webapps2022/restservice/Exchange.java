package com.webapps2022.restservice;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "conversion")
public class Exchange {
    
    String fromCurrency;
    Double exchangeRate;
    String toCurrency;
    Double exchangedAmount;

    public Exchange() {
    }
    @XmlAttribute
    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    @XmlAttribute
    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setFromCash(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @XmlAttribute
    public String getsetToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    @XmlAttribute
    public Double getExchangedAmount() {
        return exchangedAmount;
    }

    public void setExchangedAmount(Double exchangedAmount) {
        this.exchangedAmount = exchangedAmount;
    }

    
}
