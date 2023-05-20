package com.pavarin.accountmanagementapi.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pavarin.accountmanagementapi.entity.Account;

public class AccountRepositoryInMemory implements AccountRepository {

    private List<Account> accounts = null;
	
	public AccountRepositoryInMemory() {
		accounts = new ArrayList<Account>();
	}

	@Override
	public void reset() {
		accounts.clear();
	}
	
	@Override
	public Account create(Long id) {
		Account account = new Account(id, 0.0);
		accounts.add(account);
		return account;
	}

	@Override
	public Account save(Account account) {
		accounts.stream()
			.filter(acc -> !acc.getId().equals(account.getId()))
			.collect(Collectors.toList())
			.add(account);
		return account;
	}

	@Override
	public Account get(Long id) {
		return accounts.stream()
					.filter(account -> account.getId() == id)
					.findFirst()
					.orElse(null);
	}

}
