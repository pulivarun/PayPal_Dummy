package com.webapps2022.jsf;

import com.webapps2022.ejb.ExchangeRate;
import com.webapps2022.ejb.PaymentServiceDAO;
import com.webapps2022.entity.SystemUser;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import com.webapps2022.ejb.UserServiceDAO;

@Named
@RequestScoped
@DeclareRoles("users")
public class MakePaymentBean {
    
    @EJB
    UserServiceDAO usrSrv;
    
    @EJB
    PaymentServiceDAO ps;
    
    @EJB
    ExchangeRate exchangeRate;
    
    Long id;
    String sendUsername;
    String sendCurrency;
    Double sendCash;
    String receiveUsername;
    String receiveCurrency;
    Boolean approved;
    HttpServletRequest request;
    
    public MakePaymentBean() {
    }

    public String toPay(){
        SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        Double exchangedRate;
        Double receiverWillGetCash;
        if (approved){
            if ( (usrSrv.checkUserExist(receiveUsername)) && !(sender.getUsername().equals(receiveUsername))){
                if (sender.getCurrency().equals(sendCurrency)){
                    exchangedRate = sendCash;
                } else {
                    exchangedRate = exchangeRate.getExchange(sendCurrency, sendCash, sender.getCurrency());
                }
                if (ps.checkUserBalance(sender.getUsername(), exchangedRate)){
                    String payeeCurrency = usrSrv.getUserCurency(receiveUsername);
                    if (payeeCurrency.equals(sendCurrency)){
                        receiverWillGetCash = sendCash;
                    } else {
                        receiverWillGetCash = exchangeRate.getExchange(sendCurrency, sendCash, payeeCurrency);
                    }
                    try {
                        ps.createPayment(sender.getUsername(), sender.getCurrency(), exchangedRate, exchangeRate.getExchange(sender.getCurrency(), 1d, payeeCurrency), receiveUsername, payeeCurrency, receiverWillGetCash);
                        return "success";
                    } catch (Exception e){
                        System.out.println(e);
                    }
                    
                }else {
                    FacesContext.getCurrentInstance().addMessage("paymentForm:paymentAmount", new FacesMessage("Error: You have insufficient fund!"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("paymentForm:payeeUsername", new FacesMessage("Error: Payee username doesn't exist/ Payee username is same as sender!"));
            }
        }else {
            FacesContext.getCurrentInstance().addMessage("paymentForm:paymentApprove", new FacesMessage("Please approve the payment first!"));
        }
        return null;
    }

    public String toRequest(){
        SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        Double exchangedRate;
        Double receiverWillGetCash;
        if (approved){
            if ( (usrSrv.checkUserExist(receiveUsername)) && (!(sender.getUsername().equals(receiveUsername)))){
                if (sender.getCurrency().equals(sendCurrency)){
                    exchangedRate = sendCash;
                } else {
                    exchangedRate = exchangeRate.getExchange(sendCurrency, sendCash, sender.getCurrency());
                }
                String payeeCurrency = usrSrv.getUserCurency(receiveUsername);
                if (payeeCurrency.equals(sendCurrency)){
                    receiverWillGetCash = sendCash;
                } else {
                    receiverWillGetCash = exchangeRate.getExchange(sendCurrency, sendCash, payeeCurrency);
                }
                try {
                    ps.requestPayment(receiveUsername, payeeCurrency, receiverWillGetCash, exchangeRate.getExchange(sender.getCurrency(), 1d, payeeCurrency), sender.getUsername(), sender.getCurrency(), exchangedRate);
                    return "success";
                } catch (Exception e){
                    System.out.println(e);
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("paymentForm:payeeUsername", new FacesMessage("Error: Payee/Payer username doesn't exist!/ Payee username is same as sender!"));
            }
        }else {
            FacesContext.getCurrentInstance().addMessage("paymentForm:paymentApprove", new FacesMessage("Please approve the payment first!"));
        }
        return null;
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

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
