package com.exeception; 
 /**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2017. 9. 3.
 * @author        : min
 * @history       :
 */
public class CommException extends AbstractCommonException{
	
	public CommException(String code, String message) {
		super(code, message);
	}

	public CommException(String code, String message, Throwable err) {
		super(code, message, err);
	}

	public CommException(ErrCode errCodable, String... args) {
		super(errCodable, args);
	}

}
