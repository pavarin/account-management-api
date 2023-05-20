package com.pavarin.accountmanagementapi.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pavarin.accountmanagementapi.entity.Account;
import com.pavarin.accountmanagementapi.exception.AccountNotFoundException;
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
		account.setAmount(account.getAmount() + amount);
		return accountRepository.save(account);
	}
	
	public Account withdraw(Long id, double amount) throws Exception {
		Account account = accountRepository.get(id);
		if (Objects.isNull(account)) {
			throw new AccountNotFoundException();
		} else if ( account.getAmount() < amount) {
			throw new Exception("Saldo insuficiente!");
		} 
		account.setAmount(account.getAmount() - amount);
		return accountRepository.save(account);
	}
	
	public AccountResponseTransfer transfer(Long originId, double amount, Long destinationId) throws Exception {
		Account originAccount = accountRepository.get(originId);
		Account destinationAccount = accountRepository.get(destinationId);
		if (Objects.isNull(originAccount)) {
			throw new Exception("Conta origem inexistente!");
		} else if (Objects.isNull(destinationAccount)) {
			throw new Exception("Conta destinto inexistente!");
		} else if (originAccount.getAmount() < amount) {
			throw new Exception("Saldo insuficiente para efetivar transferência!");
		} 
		originAccount.setAmount(originAccount.getAmount() - amount);
		destinationAccount.setAmount(destinationAccount.getAmount() + amount);
		AccountResponseTransfer accountResponseTransfer = 
				new AccountResponseTransfer(accountRepository.save(originAccount), 
						accountRepository.save(destinationAccount));
		return accountResponseTransfer;
	}
	
	public double balance(Long id) throws Exception {
		Account account = accountRepository.get(id);
		if (Objects.isNull(account)) {
			throw new AccountNotFoundException();
		}
		return account.getAmount();
	}
	
	public Account get(Long id) {
		return accountRepository.get(id);
	}
	
}
