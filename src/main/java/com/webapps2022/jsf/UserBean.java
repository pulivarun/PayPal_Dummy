package com.webapps2022.jsf;

import com.webapps2022.ejb.PaymentServiceDAO;
import com.webapps2022.ejb.TimeServiceEJB;
import com.webapps2022.entity.PaymentTransaction;
import com.webapps2022.entity.SystemUser;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import com.webapps2022.ejb.UserServiceDAO;

@Named
@RequestScoped
@DeclareRoles("users")
public class UserBean {

    @EJB
    UserServiceDAO usrSrv;
    @EJB
    PaymentServiceDAO ps;
    @EJB
    TimeServiceEJB ts;
    

    public UserBean() {
    }

    public Long getId() {
        SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        return usrSrv.getUser(sender.getUsername()).getId();
    }

    public String getUsername() {
        SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        return sender.getUsername();
    }

    public String getName() {
        SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        return usrSrv.getUser(sender.getUsername()).getName();
    }

    public String getSurname() {
        SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        return usrSrv.getUser(sender.getUsername()).getSurname();
    }

    public String getCurrency() {
        SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        return usrSrv.getUser(sender.getUsername()).getCurrency();
    }

    public Double getBalance() {
        SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        return usrSrv.getUser(sender.getUsername()).getBalance();
    }
    
    public long getTime() {
        return 0;
    }

    public List<PaymentTransaction> getUserTransactions(String username) {
        return ps.getUserTransactions(username);
    }
    
    public List<PaymentTransaction> getTransactionsList() {
        try {
            SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
            return getUserTransactions(sender.getUsername());  
        } catch (Exception e){
            return null;
        }
    }

    public List<PaymentTransaction> getPendingTransactionsList() {
        try {
            SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
            return ps.getPendingTransactions(sender.getUsername());
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
    
    public List<PaymentTransaction> getUserPendingTransactionsListID() {
        try {
            SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
            return ps.getUserPendingTransactionsID(sender.getUsername());  
        } catch (Exception e){
            return null;
        }
    }
}
