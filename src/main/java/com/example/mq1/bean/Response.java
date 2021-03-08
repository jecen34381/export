package com.example.mq1.bean;


import org.omg.CORBA.portable.OutputStream;


public class Response<T> extends Bean implements ResponseHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code = OK;
	private String message = SUCCESS;
	private T data;
	
	public Response(){
	}
	public Response(String code, String message){
		this.code = code;
		this.message = message;
	}
	public Response(T data){
		this.data = data;
	}
	public Response(String code, String message, T data){
		this.data = data;
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
