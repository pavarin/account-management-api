package com.pavarin.accountmanagementapi.response;

import com.pavarin.accountmanagementapi.entity.Account;

public class AccountResponseWithDraw {
	
	Account origin;
	public AccountResponseWithDraw(Account origin) {
		this.origin = origin;
	}
	public Account getOrigin() {
		return origin;
	}
	public void setOrigin(Account origin) {
		this.origin = origin;
	}
	
}
