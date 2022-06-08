package com.webapps2022.ejb;

import com.webapps2022.entity.PaymentTransaction;
import com.webapps2022.entity.SystemUser;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionAttribute
public class PaymentService implements PaymentServiceDAO{
    
    @PersistenceContext
    EntityManager em;
    
    @EJB   
    ExchangeRate exchangeRate;

    public PaymentService() {
    }

    @Override
    public List<PaymentTransaction> getAllTransactions(){
        List<PaymentTransaction> transactions = em.createNamedQuery("findAllTransaction").getResultList();
        return transactions;
    }
    
    @Override
    public List<PaymentTransaction> getUserTransactions(String username){
        String sql = "SELECT c FROM PaymentTransaction c WHERE c.sendUsername = '"+username+"' OR c.receiveUsername = '"+username+"'";
        List<PaymentTransaction> transactions = em.createQuery(sql).getResultList();
        return transactions;
    }
    
    @Override
    public List<PaymentTransaction> getPendingTransactions(String username){
        String sql = "SELECT c FROM PaymentTransaction c WHERE c.pending = 'true' AND ( c.sendUsername = '"+username+"' OR c.receiveUsername = '"+username+"')";
        List<PaymentTransaction> transactions = em.createQuery(sql).getResultList();
        return transactions;
    }
    
    @Override
    public List<PaymentTransaction> getUserPendingTransactions(String username){
        String sql = "SELECT c FROM PaymentTransaction c WHERE c.pending = 'true' AND c.sendUsername = '"+username+"'";
        List<PaymentTransaction> transactions = em.createQuery(sql).getResultList();
        return transactions;
    }
    
    @Override
    public List<PaymentTransaction> getUserPendingTransactionsID(String username){
        String sql = "SELECT c.id FROM PaymentTransaction c WHERE c.pending = 'true' AND c.sendUsername = '"+username+"'";
        List<PaymentTransaction> transactions = em.createQuery(sql).getResultList();
        return transactions;
    }
    
    @Override
    public PaymentTransaction getTransaction(Long id){
        PaymentTransaction results = em.find(PaymentTransaction.class, id);
        return results;
    }
    
    @Override
    public Boolean approveTransaction(Long id){
        PaymentTransaction results = em.find(PaymentTransaction.class, id);
        if (checkUserBalance(results.getSendUsername(), results.getSendCash())){
            results.setPending(false);
            results.setApproved(true);
            deductBalance(results.getSendUsername(), results.getSendCash());
            addBalance(results.getReceiveUsername(), results.getReceiveCash());
            em.persist(results);
            em.flush();
            return true;
        }else {
            return false;
        }
    }
    
    @Override
    public void rejectTransaction(Long id){
        PaymentTransaction results = em.find(PaymentTransaction.class, id);
        results.setPending(false);
        results.setApproved(false);
        em.persist(results);
        em.flush();
    }
    
    private void createTransaction (String sendUsername, String sendCurrency, Double sendCash, Double exchangeRate, String receiveUsername, String receiveCurrency, Double receiveCash, Boolean pending, Boolean approved){
        PaymentTransaction newTransaction = new PaymentTransaction(sendUsername, sendCurrency, sendCash, exchangeRate, receiveUsername, receiveCurrency, receiveCash, pending, approved);
        em.persist(newTransaction);
        em.flush();
    }
    
    @Override
    public void createPayment (String sendUsername, String sendCurrency, Double sendCash, Double exchangeRate, String receiveUsername, String receiveCurrency, Double receiveCash){
        createTransaction(sendUsername, sendCurrency, sendCash, exchangeRate, receiveUsername, receiveCurrency, receiveCash, false, true);
        deductBalance(sendUsername, sendCash);
        addBalance(receiveUsername, receiveCash);
    }
    
    @Override
    public void requestPayment (String sendUsername, String sendCurrency, Double sendCash, Double exchangeRate, String receiveUsername, String receiveCurrency, Double receiveCash){
        createTransaction(sendUsername, sendCurrency, sendCash, exchangeRate, receiveUsername, receiveCurrency, receiveCash, true, false);
    }

    private void addBalance(String username, Double cash){
        String sql = "SELECT c FROM SystemUser c WHERE c.username = '"+username+"'";
        SystemUser su = (SystemUser) em.createQuery(sql).getSingleResult();
        BigDecimal bd1 = new BigDecimal(su.getBalance());
        Double newBalance = bd1.add(new BigDecimal(cash)).doubleValue();
        su.setbalance(newBalance);
        em.persist(su);
        em.flush();
    }

    private void deductBalance(String username, Double cash){
        String sql = "SELECT c FROM SystemUser c WHERE c.username = '"+username+"'";
        SystemUser su = (SystemUser) em.createQuery(sql).getSingleResult();
        BigDecimal bd1 = new BigDecimal(su.getBalance());
        Double newBalance = bd1.subtract(new BigDecimal(cash)).doubleValue();
        su.setbalance(newBalance);
        em.persist(su);
        em.flush();
    }
    
    @Override
    public Boolean checkUserBalance(String username, Double payment){
        String sql = "SELECT c FROM SystemUser c WHERE c.username = '"+username+"'";
        SystemUser results = (SystemUser) em.createQuery(sql).getSingleResult();
        BigDecimal bd1 = new BigDecimal(results.getBalance());
        int res = bd1.compareTo(new BigDecimal(payment));
        switch (res) {
            case 0:
                return true;
            case 1:
                return true;
            case -1:
                return false;
            default:
                return false;
        }
    }
}
