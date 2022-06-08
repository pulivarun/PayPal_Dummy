package com.webapps2022.restservice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Singleton
@Path("/conversion")
public class RSExchange {

    private final ArrayList<String> arr;

    public RSExchange() {
        arr =  new ArrayList<String>();
        arr.add("GBP");
        arr.add("USD");
        arr.add("EUR");
    }

    @GET
    @Path("/{fromCurrency}/{toCurrency}/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getExchangeRate(@PathParam("fromCurrency") String fromCurrency,@PathParam("toCurrency") String toCurrency) {
        if ((arr.contains(fromCurrency))&&(arr.contains(toCurrency))) {
            Exchange ex = new Exchange();
            ex.setFromCurrency(fromCurrency);
            ex.setToCurrency(toCurrency);
            ex.setFromCash(1d);
            ex.setExchangedAmount(getExchange(fromCurrency,1d,toCurrency));
            return Response.ok(ex).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("/{fromCurrency}/{toCurrency}/{fromCash}/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getExchange(@PathParam("fromCurrency") String fromCurrency,@PathParam("toCurrency") String toCurrency,@PathParam("fromCash") Double fromCash) {
        if ((arr.contains(fromCurrency))&&(arr.contains(toCurrency))) {
            Exchange ex = new Exchange();
            ex.setFromCurrency(fromCurrency);
            ex.setToCurrency(toCurrency);
            ex.setFromCash(fromCash);
            ex.setExchangedAmount(getExchange(fromCurrency,fromCash,toCurrency));
            return Response.ok(ex).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    
    private static Double getExchange (String fromCurrency, Double fromCash, String toCurrency){
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
    
    @PostConstruct
    public void init() {
        
    }

    @PreDestroy
    public void clean() {
    }
}
