package com.webapps2022.ejb;

import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Startup
@Singleton
public class InitSingleton {
    
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void adminInit() {
        
        if (!checkUserExist("paymentadmin")){
           
            register("admin1", "admin1", "admin", "admin", "admin");
        }else {
            System.out.println("Admin account detected");
        }
       
    }
    
    private void register(String username, String userpassword, String name, String surname, String perm) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);
            sys_user = new SystemUser(username, paswdToStoreInDB, name, surname, "GBP", 1000d);
            sys_user_group = new SystemUserGroup(username, perm);

            em.persist(sys_user);
            em.persist(sys_user_group);
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            System.out.println("Error upon creation of account");
        }
    }
    
    private Boolean checkUserExist(String username){
        try {
            String sql = "SELECT c FROM SystemUser c WHERE c.username = '"+username+"'";
            SystemUser systemUser = (SystemUser) em.createQuery(sql).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }
}
