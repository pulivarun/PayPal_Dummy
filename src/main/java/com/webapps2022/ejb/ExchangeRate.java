package com.webapps2022.ejb;

import com.webapps2022.restservice.Exchange;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Stateless
public class ExchangeRate {

    String url = "http://localhost:10000/webapps2022/conversion";
    String fromCurrency;
    String toCurrency;
    Double fromCash;

    public Double getExchange (String fromCurrency, Double fromCash, String toCurrency){
        Client client = ClientBuilder.newClient();
        Double exchangedAmt = null;
        String newURL = (url + "/" + fromCurrency + "/" + toCurrency + "/" + fromCash);
        try {
            Exchange ex = client.target(newURL)
            .request(MediaType.APPLICATION_JSON)
            .get(Exchange.class);
            exchangedAmt = ex.getExchangedAmount();
        }catch (Exception e){
            System.out.println("Access to RESTful failed, fallback to backup");
            System.out.println(newURL);
            System.out.println(e);
        }

        if (exchangedAmt == null){
            return getExchangeBackup(fromCurrency, fromCash, toCurrency);
        }else {
            return exchangedAmt;
        }
    } 
    
    public Double getExchangeBackup (String fromCurrency, Double fromCash, String toCurrency){
        BigDecimal b1 = new BigDecimal(fromCash);
        
        switch (fromCurrency) {
            case "GBP":
                switch (toCurrency) {
                    case "GBP":
                        return fromCash;
                    case "USD":
                        return b1.multiply(new BigDecimal(1.39d)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    case "EUR":
                        return b1.multiply(new BigDecimal(1.15d)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    default:
                        return 1d;
                }
            case "USD":
                switch (toCurrency) {
                    case "GBP":
                        return b1.multiply(new BigDecimal(0.72d)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    case "USD":
                        return fromCash;
                    case "EUR":
                        return b1.multiply(new BigDecimal(0.83d)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    default:
                        return 1d;
                }
            case "EUR":
                switch (toCurrency) {
                    case "GBP":
                        return b1.multiply(new BigDecimal(0.87d)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    case "USD":
                        return b1.multiply(new BigDecimal(1.21d)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    case "EUR":
                        return fromCash;
                    default:
                        return 1d;
                }
            default:
                return 1d;
        }
    } 
        
}
