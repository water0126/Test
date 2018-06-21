package compatibility;

import com.constant.GlobalValue;

/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 12.
 * @author        : min
 * @history       :
 */
public class TestString {
	public static void main(String[] args) {
		
		String line = " [client2@minsvr:/home/client2]$ :";
//		if (line.contains("$ ")){
//			System.out.println(line);
//		}
//		
//		String dbsucessmsg = "* Tibero Database tibero is created successfully on 2018. 06. 20. (��) 02:33:07 KST.:";
//		if(dbsucessmsg.matches(".*Database.*is created successfully.*")){
//			System.out.println("test");
//		}
//		
//		
		
		line = "Linux stopbugs 2.6.9-89.ELsmp #1 SMP Mon Jun 22 12:31:33 EDT 2009 x86_64 x86_64 x86_64 GNU/Linux version (little-endian):";
		if(line.isEmpty() ){
			System.out.println("isEmpty test");
		}
		
		if(line.matches(".*Linux stopbugs.*")){
			System.out.println("Linux");
		}
	}

}
