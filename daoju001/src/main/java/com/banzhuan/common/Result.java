package com.banzhuan.common;

import java.util.HashMap;
import java.util.Map;

public class Result {
	private boolean success=true;
	private Map ret = new HashMap();
	private Map errors = new HashMap();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public void addError(String key, String value)
	{
		errors.put(key, value);
	}
	
	public Map getErrors()
	{
		return this.errors;
	}
	
	public void add(String key , Object obj)
	{
		ret.put(key, obj);
	}
	
	public Object get(String key)
	{
		return ret.get(key);
	}
	
	
}
