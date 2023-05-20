package com.pavarin.accountmanagementapi.response;

import com.pavarin.accountmanagementapi.entity.Account;

public class AccountResponseDeposit {
	
	Account destination;
	public AccountResponseDeposit(Account destination) {
		this.destination = destination;
	}
	public Account getDestination() {
		return destination;
	}
	public void setDestination(Account destination) {
		this.destination = destination;
	}
	
}
