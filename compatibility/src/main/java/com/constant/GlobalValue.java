package com.constant; 
 /**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 14.
 * @author        : min
 * @history       :
 */
public class GlobalValue {
	
	public static final String genFileDir ="G:\\eclipse\\eclipse-jee-neon-3-win32-x86_64\\workspace\\FileGen\\template";
	public static final String configArchiveFilename ="config.tar.gz";
	public static final String configArchiveDir ="archConfig";
	public static final String configDir ="config";
	
	public static final String errorRegex= "(Booting Failed"
						           + "|command not found"
						           + "|Data source was not found"
						           + "|Can't open the license file"
						           + "|Invalid license file"
						           + "|database created already"
						           + "|A shared memory segment with the same key already exists"
						           + ")";
	
	public static final String installEndRegex =".*Database.*is created successfully.*";
	public static final String SamplerTestEndRegex =".*New Sampler Compatibility Test End.*";
	public static String remoteAllBinDir ="Tibero" ;

}
