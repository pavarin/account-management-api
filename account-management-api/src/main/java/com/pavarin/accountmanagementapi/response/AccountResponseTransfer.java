package com.pavarin.accountmanagementapi.response;

import com.pavarin.accountmanagementapi.entity.Account;

public class AccountResponseTransfer {
	
	Account origin;
	Account destination;
	public AccountResponseTransfer(Account origin, Account destination) {
		this.origin = origin;
		this.destination = destination;
	}
	public Account getOrigin() {
		return origin;
	}
	public void setOrigin(Account origin) {
		this.origin = origin;
	}
	public Account getDestination() {
		return destination;
	}
	public void setDestination(Account destination) {
		this.destination = destination;
	}

}
