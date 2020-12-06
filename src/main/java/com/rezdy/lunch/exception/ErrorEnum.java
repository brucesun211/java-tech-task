package com.rezdy.lunch.exception;

public enum ErrorEnum {

	DATE_TIME_PARSER_ERROR("Invalid Format date parameter, Please check date format of 'yyyy-mm-dd'"),
	GENERAL_INTERNAL_ERROR("Sorry for inconvinence, can not provide service now, please try the service lately");

	private String errorMsg;

	private ErrorEnum(String _errorMsg) {
		this.errorMsg = _errorMsg;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

}
