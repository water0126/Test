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
public class ErrCodeUtil {
	public static String parseMessage(String message, String... args) {
		if (message == null || message.trim().length() <= 0)
			return message;

		if (args == null || args.length <= 0)
			return message;

		String[] splitMsgs = message.split("%");
		if (splitMsgs == null || splitMsgs.length <= 1)
			return message;

		for (int i = 0; i < args.length; i++) {
			String replaceChar = "%" + (i + 1);
			message = message.replaceFirst(replaceChar, args[i]);
		}
		return message;
	}

}
