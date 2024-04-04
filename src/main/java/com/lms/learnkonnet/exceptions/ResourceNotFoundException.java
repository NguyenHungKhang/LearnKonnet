package com.lms.learnkonnet.exceptions;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
	String FieldName;
	long FieldValue;
	String Value;
	UUID IdValue;
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with this %s : %s ", resourceName,fieldName, fieldValue));
		this.resourceName = resourceName;
		FieldName = fieldName;
		FieldValue = fieldValue;
	}
	public ResourceNotFoundException(String resourceName, String fieldName, UUID idValue) {
		super(String.format("%s not found with this %s : %s ", resourceName,fieldName, idValue));
		this.resourceName = resourceName;
		FieldName = fieldName;
		IdValue = idValue;
	}
	public ResourceNotFoundException(String resourceName, String fieldName, String value) {
		super(String.format("%s not found with this %s : %s ", resourceName,fieldName, value));
		this.resourceName = resourceName;
		FieldName = fieldName;
		Value = value;
	}
}
