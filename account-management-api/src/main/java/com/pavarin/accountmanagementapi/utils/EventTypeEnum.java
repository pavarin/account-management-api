package com.pavarin.accountmanagementapi.utils;

public enum EventTypeEnum {
	
	DEPOSIT("deposit"), 
	WITHDRAW("withdraw"),
	TRANSFER("transfer");
	
	private String type;
	
	EventTypeEnum(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}

}
