package com.yilan.project.repository;

import com.yilan.project.model.Account;

public interface AccountDao {
    Account authenticate(String inputUsername, String inputPassword);
    boolean insertAccount(String username, String password, String phoneNumber);
}
