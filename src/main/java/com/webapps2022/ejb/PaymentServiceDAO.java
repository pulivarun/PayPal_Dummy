package com.webapps2022.ejb;
import com.webapps2022.entity.PaymentTransaction;
import java.util.List;
import javax.ejb.Local;
@Local
public interface PaymentServiceDAO {
    public List<PaymentTransaction> getAllTransactions();
    public List<PaymentTransaction> getUserTransactions(String username);
    public List<PaymentTransaction> getPendingTransactions(String username);
    public List<PaymentTransaction> getUserPendingTransactions(String username);
    public List<PaymentTransaction> getUserPendingTransactionsID(String username);
    public PaymentTransaction getTransaction(Long id);
    public Boolean approveTransaction(Long id);
    public void rejectTransaction(Long id);
    public void createPayment (String sendUsername, String sendCurrency, Double sendCash, Double exchangeRate, String receiveUsername, String receiveCurrency, Double receiveCash);
    public void requestPayment (String sendUsername, String sendCurrency, Double sendCash, Double exchangeRate, String receiveUsername, String receiveCurrency, Double receiveCash);
    public Boolean checkUserBalance(String username, Double payment);
}
