package com.example.pokeapi_mirror.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.pokeapi_mirror.model.util.SimpleMessage;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;

@ControllerAdvice
public class MyCustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler
	  public ResponseEntity<Object> handle(ConstraintViolationException ex) {
			List<SimpleMessage> errors = new ArrayList<>();
			List<String> listFields = new ArrayList<>();
			List<String> listInvalidField = new ArrayList<>();
			List<List<String>> listMsgField = new ArrayList<List<String>>();
			String field = null;

	        for (ConstraintViolation<?> e :  ex.getConstraintViolations()) {     	
	        	for (Path.Node node : e.getPropertyPath()) {
	        	    if (!listFields.contains(node.getName())) {
	        	    	listFields.add(node.getName());
	        	    	if (e.getInvalidValue() != null) {
	        	    		listInvalidField.add(e.getInvalidValue().toString());
	        	    	}
	        	    	else {
	        	    		listInvalidField.add(null);
	        	    	}
	        	    }
	        	}
	        }
	        
	        for (int i=0; i < listFields.size(); i++) {
				listMsgField.add(new ArrayList<>());
	        }
	        
	        for (ConstraintViolation<?> e :  ex.getConstraintViolations()) {     	
	        	for (Path.Node node : e.getPropertyPath()) {
	        		field = node.getName();
	        	}
	        	
	        	listMsgField.get(listFields.indexOf(field)).add(e.getMessage());
	        }
	        
	        for (int i=0; i < listFields.size(); i++) {
	        	if (!listMsgField.get(i).isEmpty()) {
	        		errors.add(new SimpleMessage(listFields.get(i), listInvalidField.get(i), listMsgField.get(i)));
	        	}
	        }
	           
	        return new ResponseEntity<>(errors, null, HttpStatus.BAD_REQUEST);
	  }
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<SimpleMessage> errors = new ArrayList<>();
		List<String> listFields = new ArrayList<>();
		List<String> listInvalidField = new ArrayList<>();
		List<List<String>> listMsgField = new ArrayList<>();
		String field = null;
		
		for (FieldError e: ex.getBindingResult().getFieldErrors()) {
			if (!listFields.contains(e.getField())) {
    	    	listFields.add(e.getField());
    	    	if (e.getRejectedValue() != null) {
    	    		listInvalidField.add(e.getRejectedValue().toString());
    	    	}
    	    	else {
    	    		listInvalidField.add(null);
    	    	}
    	    }
		}
		
		for (int i=0; i < listFields.size(); i++) {
			listMsgField.add(new ArrayList<>());
        }
		
		for (FieldError e: ex.getBindingResult().getFieldErrors()) {
        	field = e.getField();
        	listMsgField.get(listFields.indexOf(field)).add(e.getDefaultMessage());
		}
		
		for (int i=0; i < listFields.size(); i++) {
			errors.add(new SimpleMessage(listFields.get(i), listInvalidField.get(i), listMsgField.get(i)));
        }

		return new ResponseEntity<>(errors, null, HttpStatus.BAD_REQUEST);
	}
}
