package com.webapps2022.ejb;

import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class UserService implements UserServiceDAO{

    @PersistenceContext
    EntityManager em;

    public UserService() {
    }

    @Override
    public void registerUser(String username, String userpassword, String name, String surname, String currency, Double balance) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);

            sys_user = new SystemUser(username, paswdToStoreInDB, name, surname, currency, balance);
            sys_user_group = new SystemUserGroup(username, "users");

            em.persist(sys_user);
            em.persist(sys_user_group);
            em.flush();
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void registerUser(String username, String userpassword, String name, String surname, String currency, Double balance, String userGroup) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);
           sys_user = new SystemUser(username, paswdToStoreInDB, name, surname, currency, balance);
            sys_user_group = new SystemUserGroup(username, userGroup);

            em.persist(sys_user);
            em.persist(sys_user_group);
            em.flush();
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void deleteUser(Long userID){
        SystemUser user = em.find(SystemUser.class, userID);
        em.remove(user);
        em.flush();
    }
    
    @Override
    public SystemUser getUser(String username) {
        String sql = "SELECT c FROM SystemUser c WHERE c.username = '"+username+"'";
        SystemUser results = (SystemUser) em.createQuery(sql).getSingleResult();
        SystemUser newResults = new SystemUser(results.getUsername(), "XXXXX", results.getName(), results.getSurname(), results.getCurrency(), results.getBalance());
        return newResults;
    }

    @Override
    public List<SystemUser> getAllUsers(){
        List<SystemUser> users = em.createNamedQuery("findAllUser").getResultList();
        List<SystemUser> newList = new ArrayList<SystemUser>();;
        for (SystemUser u: users){
            newList.add(u);
        }
        return newList;
    }
    
    @Override
    public List<SystemUserGroup> getAllUsersAtGroup(){
        return em.createNamedQuery("findAllUserGroup").getResultList();
    }
    
    @Override
    public List<SystemUser> getAllUsersID(){
        String sql = "SELECT c.id FROM SystemUser c";
        List<SystemUser> users = em.createQuery(sql).getResultList();
        return users;
    }
    
    @Override
    public Long getUsersID(String username){
        String sql = "SELECT c.id FROM SystemUser c WHERE c.username = '"+username+"'";
        Long usr_id = (Long) em.createQuery(sql).getSingleResult();
        return usr_id;
    }
    
    @Override
    public Boolean checkUserExist(String username){
        try {
            String sql = "SELECT c FROM SystemUser c WHERE c.username = '"+username+"'";
            SystemUser systemUser = (SystemUser) em.createQuery(sql).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }
    
    @Override
    public Double getUserBalance(String username){
        String sql = "SELECT c FROM SystemUser c WHERE c.username = '"+username+"'";
        SystemUser results = (SystemUser) em.createQuery(sql).getSingleResult();
        return results.getBalance();
    }
    
    @Override
    public String getUserCurency(String username){
        String sql = "SELECT c FROM SystemUser c WHERE c.username = '"+username+"'";
        SystemUser results = (SystemUser) em.createQuery(sql).getSingleResult();
        return results.getCurrency();
    }
    
    
}
