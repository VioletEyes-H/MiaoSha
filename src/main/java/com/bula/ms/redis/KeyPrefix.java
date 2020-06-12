package com.bula.ms.redis;

public interface KeyPrefix {
		
	int expireSeconds();
	
	String getPrefix();
	
}
