package com.bill.management.util;

import lombok.Getter;

@Getter
public class Response {

	String response;
	int statusCode;
	
	Response(String response, int statusCode) {
		this.response = response;
		this.statusCode = statusCode;
	}
	
}
