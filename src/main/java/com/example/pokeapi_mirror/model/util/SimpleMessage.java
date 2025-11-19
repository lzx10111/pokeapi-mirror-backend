package com.example.pokeapi_mirror.model.util;

import java.util.Arrays;
import java.util.List;

public class SimpleMessage {
	private String fieldName;
	private String fieldValue;
	private List<String> fieldMsg;
	
	public SimpleMessage(String fieldName, String fieldValue, List<String> fieldMsg) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.fieldMsg = fieldMsg;
	}
	
	public SimpleMessage(String fieldName, String fieldValue, String fieldMsg) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.fieldMsg = Arrays.asList(fieldMsg);
	}
	
	public SimpleMessage(String fieldName, String fieldMsg) {
		this.fieldName = fieldName;
		this.fieldValue = null;
		this.fieldMsg = Arrays.asList(fieldMsg);
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public List<String> getFieldMsg() {
		return fieldMsg;
	}
	public void setFieldMsg(List<String> fieldMsg) {
		this.fieldMsg = fieldMsg;
	}
}
