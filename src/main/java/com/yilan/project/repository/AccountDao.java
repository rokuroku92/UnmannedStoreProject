package com.yilan.project.repository;

public interface AccountDao {
    int authenticate(String inputAccount, String inputPassword);
}
