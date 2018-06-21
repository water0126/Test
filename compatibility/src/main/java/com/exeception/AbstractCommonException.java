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
public abstract class AbstractCommonException extends  Exception{

	private static final long serialVersionUID = 1L;
	private String code;
	   private String message;

	   private AbstractCommonException(String message) {
	      super(message);
	      this.message = message;
	   }
	 
	   protected AbstractCommonException(String code, String message) {
	      this(message);
	      this.code = code;
	   }
	 
	   private AbstractCommonException(String message, Throwable err) {
	      super(message, err);
	      this.message = message;
	   }
	 
	   protected AbstractCommonException(String code, String message, Throwable err) {
	      this(message, err);
	      this.code = code;
	   }
	   protected AbstractCommonException(ErrCode errCodable, String...args) {
	      this(errCodable.getErrCode(), errCodable.getMessage(args));
	   }
	 
	   public String getCode() {
	      return code;
	   }

	   public String getMessage() {
	     return message;
	   }
}
