package com.webapps2022.jsf;

import com.webapps2022.ejb.ExchangeRate;
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
public class CheckExchangeRateBean {
    
    @EJB
    UserServiceDAO usrSrv;
    @EJB
    ExchangeRate er;
    Double GBP;
    Double USD;
    Double EUR;

    public CheckExchangeRateBean() {
    }
    
    public String getCurrency() {
        return usrSrv.getUserCurency(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
    }
    public Double getGBP() {
        String currency = usrSrv.getUserCurency(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        return er.getExchange(currency, 1d, "GBP");
    }
    
    public Double getUSD() {
        String currency = usrSrv.getUserCurency(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        return er.getExchange(currency, 1d, "USD");
    }
    
    public Double getEUR() {
        String currency = usrSrv.getUserCurency(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        return er.getExchange(currency, 1d, "EUR");
    }
    
}
