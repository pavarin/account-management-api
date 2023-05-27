package com.pavarin.accountmanagementapi.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pavarin.accountmanagementapi.entity.Account;
import com.pavarin.accountmanagementapi.exception.AccountNotFoundException;
import com.pavarin.accountmanagementapi.exception.InsufficientBalanceException;
import com.pavarin.accountmanagementapi.repository.AccountRepository;
import com.pavarin.accountmanagementapi.response.AccountResponseTransfer;

@Service
public class AccountService {
	
	@Autowired
	AccountRepository accountRepository;
	
	public void reset() {
		accountRepository.reset();
	}
	
	public Account create(Long id) throws Exception {
		if (Objects.isNull(accountRepository.get(id))) {
			return accountRepository.create(id);
		} else {
			throw new Exception("Já existe uma conta com este id!");
		}
	}
	
	public Account deposit(Long id, double amount) throws Exception {
		Account account = accountRepository.get(id);
		if (Objects.isNull(account)) {
			account = create(id);
		}
		account.setBalance(account.getBalance() + amount);
		return accountRepository.save(account);
	}
	
	public Account withdraw(Long id, double amount) throws Exception {
		Account account = accountRepository.get(id);
		if (Objects.isNull(account)) {
			throw new AccountNotFoundException();
		} else if (account.getBalance() < amount) {
			throw new InsufficientBalanceException("Insufficient balance!");
		} 
		account.setBalance(account.getBalance() - amount);
		return accountRepository.save(account);
	}
	
	public AccountResponseTransfer transfer(Long originId, double amount, Long destinationId) throws Exception {
		Account originAccount = accountRepository.get(originId);
		Account destinationAccount = accountRepository.get(destinationId);
		if (Objects.isNull(originAccount)) {
			throw new AccountNotFoundException("Origin account not found!");
		} else if (Objects.isNull(destinationAccount)) {
			destinationAccount = create(destinationId);
		}
		originAccount = accountRepository.save(withdraw(originId, amount));
		destinationAccount = accountRepository.save(deposit(destinationId, amount));
		AccountResponseTransfer accountResponseTransfer = new AccountResponseTransfer(originAccount, destinationAccount);
		return accountResponseTransfer;
	}
	
	public double balance(Long id) throws Exception {
		Account account = accountRepository.get(id);
		if (Objects.isNull(account)) {
			throw new AccountNotFoundException();
		}
		return account.getBalance();
	}
	
	public Account get(Long id) {
		return accountRepository.get(id);
	}
	
}
