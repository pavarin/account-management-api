package com.pavarin.accountmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pavarin.accountmanagementapi.entity.Account;
import com.pavarin.accountmanagementapi.entity.Event;
import com.pavarin.accountmanagementapi.exception.AccountNotFoundException;
import com.pavarin.accountmanagementapi.exception.InsufficientBalanceException;
import com.pavarin.accountmanagementapi.response.AccountResponseDeposit;
import com.pavarin.accountmanagementapi.response.AccountResponseTransfer;
import com.pavarin.accountmanagementapi.response.AccountResponseWithDraw;
import com.pavarin.accountmanagementapi.service.AccountService;
import com.pavarin.accountmanagementapi.utils.EventTypeEnum;

@Controller
public class AccountController {
	
	@Autowired
	private AccountService service;
	
	@PostMapping("/reset")
	@ResponseBody
	public ResponseEntity<String> reset() {
		try {
			service.reset();
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("OK");
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}
	
	@GetMapping("/balance")
	@ResponseBody
	public ResponseEntity<String> balance(@RequestParam(name="account_id") String id) {
		try {
			double balance = service.balance(id);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(String.valueOf(balance));
		} catch (AccountNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(String.valueOf(0));
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}
	
	@PostMapping(value = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> event(@RequestBody Event event) {
		try {
			if (event.getType().equals(EventTypeEnum.DEPOSIT.getType())) {
				Account accountDeposit = service.deposit(event.getDestination(), event.getAmount());
				return ResponseEntity
						.status(HttpStatus.CREATED)
						.body(new AccountResponseDeposit(accountDeposit));
			} else if (event.getType().equals(EventTypeEnum.WITHDRAW.getType())) {
				Account accountWithdraw = service.withdraw(event.getOrigin(), event.getAmount());
				return ResponseEntity
						.status(HttpStatus.CREATED)
						.body(new AccountResponseWithDraw(accountWithdraw));
			} else if (event.getType().equals(EventTypeEnum.TRANSFER.getType())) {
				AccountResponseTransfer accountResponseTransfer = service.transfer(event.getOrigin(), event.getAmount(), event.getDestination());
				return ResponseEntity
						.status(HttpStatus.CREATED)
						.body(accountResponseTransfer);
			} 
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Invalid event type!"); 
		} catch (InsufficientBalanceException e) {
			return ResponseEntity
					.status(HttpStatus.PAYMENT_REQUIRED)
					.body(e.getMessage());
		} catch (AccountNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(0);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}

}
