package com.webapps2022.ejb;

import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.SystemUserGroup;
import java.util.List;
import javax.ejb.Local;
@Local
public interface UserServiceDAO {
    public void registerUser(String username, String userpassword, String name, String surname, String currency, Double balance);
    public void registerUser(String username, String userpassword, String name, String surname, String currency, Double balance, String userGroup);
    public void deleteUser(Long userID);
    public SystemUser getUser(String username);
    public List<SystemUser> getAllUsers();
    public List<SystemUserGroup> getAllUsersAtGroup();
    public List<SystemUser> getAllUsersID();
    public Long getUsersID(String username);
    public Boolean checkUserExist(String username);
    public Double getUserBalance(String username);
    public String getUserCurency(String username);
}
