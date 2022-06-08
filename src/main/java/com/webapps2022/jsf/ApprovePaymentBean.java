package com.webapps2022.jsf;

import com.webapps2022.ejb.PaymentServiceDAO;
import com.webapps2022.entity.PaymentTransaction;
import com.webapps2022.entity.SystemUser;
import java.util.List;
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
public class ApprovePaymentBean {
    @EJB
    UserServiceDAO usrSrv;
    
    @EJB
    PaymentServiceDAO ps;
    
    Long paymentID;
    Boolean approved;
    String senderUsername;
    
    public String toConfirmPay(){
        if (approved){
            if (ps.approveTransaction(Long.parseLong(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("paymentForm:paymentID")))){
                return "success";
            } else {
                FacesContext.getCurrentInstance().addMessage("paymentForm:paymentID", new FacesMessage("Error: You have insufficient fund!"));
                return null;
            }
        }
        return null;
    }
    
    public String toRejectPay(){
        if (approved){
            ps.rejectTransaction(Long.parseLong(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("paymentForm:paymentID")));
            return "success";
        }
        return null;
    }
    
    public List<PaymentTransaction> getUserPendingTransactionsID(String username) {
        return ps.getUserPendingTransactionsID(username);
    }
    
    public List<PaymentTransaction> getUserPendingTransactionsListID() {
        try {
            SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
            return getUserPendingTransactionsID(sender.getUsername());  
        } catch (Exception e){
            return null;
        }
    }
    
    public List<PaymentTransaction> getUserPendingTransactionsList() {
        try {
            SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
            return ps.getUserPendingTransactions(sender.getUsername());
        } catch (Exception e){
            return null;
        }
    }

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
    
}
