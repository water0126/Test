package com.utility;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @Class Name : StringUtil.java
 * @Description : 문자관련 유틸리티 메소드 정의 
 * @Modification Information
 *
 *    수정일                               수정자             수정내용
 *    -------        -------     -------------------
 *    2015.12.09.     전혜나		최초작성
 *
 */

public class StringUtil {
	
	public static final String SEPARATOR_ARRAY = ",";
	
	/**
	 * 오브젝트가 Null 또는 Empty이면 true 를 return 한다.
	 * @param obj
	 * @return true : null / false : not null
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {
		if ( obj == null ) {
			return true;
		} else if ( obj instanceof String && ( obj==null || "".equals(obj.toString().trim()) ) ) {
			return true;
		}
        else if( obj instanceof List && ( obj == null || ((List)obj).isEmpty() || ((List)obj).size() == 0) ) {
        	return true;
        }
        else if( obj instanceof Map && ( obj==null || ((Map)obj).isEmpty() ) ) {
        	return true;
        }
        else if( obj instanceof Object[] && ( obj==null || Array.getLength(obj)==0 ) ) {
        	return true;
        }
        else {
        	return false;
        }
	}
	
	/**
	 * 오브젝트가 Null 또는 Empty가 아니면 true 를 return 한다.
	 * @param obj
	 * @return true : not null / false : null
	 */	
	public static boolean isNotNullOrEmpty(Object obj) {
		return !isNullOrEmpty(obj);
	}
	
	// 한글데이타 인코딩 정보 
    /**
     * IOS8859_1 문자열 인코딩 된 문자셋을  euc-kr로 인코딩 하여 전달한다.
     * - mysql DB의 한글 데이타 글짜 깨짐 방지를 위함. 
     * @param str
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String ios8859ToEucKr(String str) throws UnsupportedEncodingException {
    	String ENCODE_CHAR1 = "8859_1";
        String ENCODE_CHAR2 = "EUC-KR";
        
        if (isNullOrEmpty(str)) return "";
        else return new String(str.getBytes(ENCODE_CHAR1), ENCODE_CHAR2);
    }
    
	/**
	 * 입력받은 string의 이니셜을 대문자로 변환하여 전달한다.
	 * ex) welcome to tmax -> Welcome To Tmax
	 * @param str
	 * @return
	 */
	public static String initcap(String str) {
		String cap = "";
		// 띄어쓰기로 split 하여 각 문자열을 대문자로 변환하여 다시 저장한다.
		for (String temp : str.split(" ")) { 
			cap += StringUtils.capitalize(temp) + " ";			
		}
		return cap.trim();
	}	    
    
	/**
	 * 입력받은 string을 구분자(콤마)로 나누어 string array로 치환
	 * @param str
	 * @return
	 */
	public static String[] stringToArray(String str) {
		return stringToArray(str, SEPARATOR_ARRAY);
	}
	
	/**
	 * 입력받은 string을 구분자로 나누어 string array로 치환
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String[] stringToArray(String str, String separator) {
		if(isNullOrEmpty(str)) str = "";
		return str.trim().split(separator);
	}	
	
	/**
	 * 입력받은 string을 구분자(콤마)로 나누어 string list로 치환
	 * @param str
	 * @return
	 */
	public static List<String> stringToList(String str) {
		if(isNullOrEmpty(str)) 
			return new ArrayList<String>();
		
		String[] arr = stringToArray(str);
		return new ArrayList<String>(Arrays.asList(arr));
	}	
	
	/**
	 * 입력받은 string을 구분자로 나누어 string list로 치환
	 * @param str
	 * @param separator
	 * @return
	 */
	public static List<String> stringToList(String str, String separator) {
		if(isNullOrEmpty(str)) 
			return new ArrayList<String>();
		
		String[] arr = stringToArray(str, separator);
		return new ArrayList<String>(Arrays.asList(arr));
	}
		
	/**
	 * 입력받은 string array를 구분자(콤마)로 구분하여 문자열로 변환
	 * @param arr
	 * @return
	 */
	public static String StringArrayToString(String[] arr) {
		return StringArrayToString(arr, SEPARATOR_ARRAY);
	}
	
	/**
	 * 입력받은 string array를 전달받은 구분자로 구분하여 문자열로 변환
	 * @param arr
	 * @param separator
	 * @return
	 */
	public static String StringArrayToString(String[] arr, String separator) {
		if(isNullOrEmpty(arr)) return "";
		return StringUtils.join(arr, separator);
	}
	
	/**
	 * 입력받은 string list를 구분자(콤마)로 구분하여 문자열로 변환
	 * @param list
	 * @return
	 */
	public static String StringListToString(List<String> list) {
		return StringListToString(list, SEPARATOR_ARRAY);
	}	

	/**
	 * 입력받은 string list를 전달받은 구분자로 구분하여 문자열로 변환
	 */
	public static String StringListToString(List<String> list, String separator) {
		if(isNullOrEmpty(list)) return "";
		return StringUtils.join(list.toArray(), separator);
	}	

	/**
	 * 입력받은 string을 int로  치환
	 * @param str
	 * @return
	 */
	public static int stringToInteger(String str) {
		int number = 0;
		try {
			number = Integer.parseInt(str);
		} catch (NumberFormatException localNumberFormatException) {
		} catch (NullPointerException localNullPointerException) {
		}
		return number;
	}
	
	/**
	 * 입력받은 string이 number인지 체크한다.
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException localNumberFormatException) {
			return false;
		}
	}	
	
	/**
	 * string list 내에 중복되는 값이 있는 지 체크한다.
	 * @param list
	 * @return true(중복  있음) / false(중복 없음) 
	 */
	public static boolean isDuplcate(List<String> list) {
		if (isNotNullOrEmpty(list)) {
			for (int i=0; i < list.size(); i++) {
				List<String> temp = new ArrayList<String>(); // 중복값을 체크하기 위한 값을 제거한 리스트를 담기 위해 생성하는 객체
				temp.addAll(list);
				temp.remove(i); // 체크할 값을 리스트에서 제거한다. 
				if(temp.contains(list.get(i))) {  // 해당 값을 포함하고 있는 지 확인한다.
					// 포함하고 잇으면  false를 리턴한다. 
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * list 객체에서  중복되는 값을 제거한 뒤 전달한다.
	 * @param list
	 * @return
	 */
	public static List<Object> getUniqueValue(List<Object> list) {
		List<Object> uniqueList = new ArrayList<Object>(new HashSet<Object>(list));
		return uniqueList;
	}	
	
	/**
	 * string list 객체에서 빈 문자열을 제거한 뒤 전달한다.
	 * @param list
	 * @return
	 */
	public static List<String> getListRemoveEmpty(List<String> list) {
		List<String> temp = new ArrayList<String>();
		if (isNotNullOrEmpty(list)) {		
			for (String str : list) {
				// 빈문자열이 아닌 경우 리스트에 저장한다.
				if (isNotNullOrEmpty(str)) temp.add(str);
			}
		}
		return temp;
	}	
}
