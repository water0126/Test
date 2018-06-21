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
public interface ErrCode {
	String getErrCode();
	String getMessage(String... args);
}
