package com.pavarin.accountmanagementapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    
    String type;
	int origin;
	int destination;
	double amount;

}
