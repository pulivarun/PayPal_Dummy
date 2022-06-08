package com.webapps2022.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-05-06T13:53:55")
@StaticMetamodel(PaymentTransaction.class)
public class PaymentTransaction_ { 

    public static volatile SingularAttribute<PaymentTransaction, String> sendCurrency;
    public static volatile SingularAttribute<PaymentTransaction, String> receiveCurrency;
    public static volatile SingularAttribute<PaymentTransaction, Boolean> approved;
    public static volatile SingularAttribute<PaymentTransaction, Double> exchangeRate;
    public static volatile SingularAttribute<PaymentTransaction, Boolean> pending;
    public static volatile SingularAttribute<PaymentTransaction, Double> sendCash;
    public static volatile SingularAttribute<PaymentTransaction, Long> id;
    public static volatile SingularAttribute<PaymentTransaction, String> sendUsername;
    public static volatile SingularAttribute<PaymentTransaction, String> receiveUsername;
    public static volatile SingularAttribute<PaymentTransaction, Double> receiveCash;

}