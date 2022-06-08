package com.webapps2022.jsf;

import com.webapps2022.ejb.ExchangeRate;
import com.webapps2022.ejb.PaymentServiceDAO;
import com.webapps2022.entity.PaymentTransaction;
import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.SystemUserGroup;
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
@DeclareRoles("admin")
public class AdminBean {
    
    @EJB
    UserServiceDAO usrSrv;
    @EJB
    PaymentServiceDAO ps;
    @EJB
    ExchangeRate er;
    String username;
    String userpassword;
    String name;
    String surname;
    String currency;
    Double balance;
    String userGroup;
    Boolean toConfirm;

    public AdminBean() {
    }
    
    
    
    public List<PaymentTransaction> getAllTransactions() {
        return ps.getAllTransactions();
    }
    
    public String getLoginName() {
        SystemUser sender = usrSrv.getUser(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        return usrSrv.getUser(sender.getUsername()).getName();
    }
    
    public String register() {
        if (!usrSrv.checkUserExist(username)){
            usrSrv.registerUser(username, userpassword, name, surname, currency, balance, userGroup);
            return "success";
        }
        FacesContext.getCurrentInstance().addMessage("loginForm:username", new FacesMessage("Error: username exist!"));
        return null;
    }

    public String deleteUser(){
        Long adm_id  = usrSrv.getUsersID(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
        Long usr_id = Long.parseLong(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("usersForm:userID"));

        if (!(adm_id == usr_id)){
            System.out.println(adm_id);
            System.out.println(usr_id);
            if (toConfirm){
            usrSrv.deleteUser(Long.parseLong(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("usersForm:userID")));
            return "success";
            } else {
                FacesContext.getCurrentInstance().addMessage("usersForm:deleteApprove", new FacesMessage("Error: Please check the above details are correct."));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("usersForm:userID", new FacesMessage("Error: username is same as curent user!"));
            return null;
        }
        
    }
    public List<SystemUser> getUsersList(){
        try {
            return usrSrv.getAllUsers();
        } catch (Exception e){
            return null;
        }
    }
    
    public List<SystemUserGroup> getUsersAtGroupList(){
        try {
            return usrSrv.getAllUsersAtGroup();
        } catch (Exception e){
            return null;
        }
    }
    
    public List<SystemUser> getUsersIDList(){
        try {
            return usrSrv.getAllUsersID();
        } catch (Exception e){
            return null;
        }
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getToConfirm() {
        return toConfirm;
    }

    public void setToConfirm(Boolean toConfirm) {
        this.toConfirm = toConfirm;
    }
    
    
}
