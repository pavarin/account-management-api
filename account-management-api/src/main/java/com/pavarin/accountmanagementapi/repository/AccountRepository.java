package com.pavarin.accountmanagementapi.repository;

import org.springframework.stereotype.Repository;

import com.pavarin.accountmanagementapi.entity.Account;

@Repository
public interface AccountRepository {
    
    void reset();
	Account create(Long id);
	Account save(Account account);
	Account get(Long id);
    
}
