package com.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 7.
 * @author        : min
 * @history       :
 */
public class FileUtility {
	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);
	public static void compressTarGz(String dirPath , String tarGzPath) throws IOException{
		TarArchiveOutputStream tOut = null;		
		try {

			logger.debug("path:{}", new File(".").getAbsolutePath());
			tOut = new TarArchiveOutputStream(new GzipCompressorOutputStream(
					new BufferedOutputStream(new FileOutputStream(new File(tarGzPath)))));
			addFileToTarGz(tOut,dirPath,"");
		} finally {
			tOut.finish();
			tOut.close();
			tOut = null;
		}
	}

	public static void addFileToTarGz(TarArchiveOutputStream tOut, String path, String base) throws IOException{
		File f = new File(path);
		logger.debug("file:{}",f.exists());
		String entryName = base + f.getName();
		TarArchiveEntry tarEntry =  new TarArchiveEntry(f,entryName);
		tOut.putArchiveEntry(tarEntry);
		
		if(f.isFile()){
			IOUtils.copy(new FileInputStream(f), tOut);
			tOut.closeArchiveEntry();
		}else{
			tOut.closeArchiveEntry();
			File[] children = f.listFiles();
			if(children !=null){
				for (File child : children){
					logger.debug("children file : {}",child.getName());
					addFileToTarGz(tOut, child.getAbsolutePath(), entryName+"/");
				}
			}
		}
		
	}
	
	public static void uncompressTarGZ(File tarFile, File dest) throws IOException {
		dest.mkdir();
		TarArchiveInputStream tarIn = null;
		tarIn = new TarArchiveInputStream(
				new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(tarFile))));

		TarArchiveEntry tarEntry = tarIn.getNextTarEntry();

		while (tarEntry != null) {
			File destPath = new File(dest, tarEntry.getName());
			if (tarEntry.isDirectory()) {
				destPath.mkdir();
			} else {
				destPath.createNewFile();
				byte[] btoRead = new byte[1024];
				BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(destPath));
				int len = 0;

				while ((len = tarIn.read(btoRead)) != -1) {
					bout.write(btoRead, 0, len);
				}
				bout.close();
				btoRead = null;
			}

			tarEntry = tarIn.getNextTarEntry();
		}

	}
	
	//Jet 로 파일 생성 하면 new 라인이 생겨서 리눅스에서 에러 발생해서 newline 제거 
	public static void removeNewlinefromDiretoryfile(String dir) throws IOException {
		ArrayList<String> files = new ArrayList<String>(); 
		subDirList(dir,files);
		
		for(String filename : files){
			logger.info("file:{}",filename);
	        File f = new File(filename);  
	        if (f.exists() == false)  
	        {  
	        	continue;
	        }  
	        String Text = "";
	        // 파일 읽기  
	        String fileText = ReadFileText(f);  
	        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));  
	        Text = fileText.replace(System.getProperty( "line.separator" ), "\n");
	        // 파일 쓰기  
	        buffWrite.write(Text, 0, Text.length());  
	        // 파일 닫기  
	        buffWrite.flush();  
	        buffWrite.close(); 

		}
	}
	
	// 파일 테스트 읽기  
	private static String ReadFileText(File file)  
	{  
	    String strText = "";  
	    int nBuffer;  
	    try   
	    {  
	        BufferedReader buffRead = new BufferedReader(new FileReader(file));  
	        while ((nBuffer = buffRead.read()) != -1)  
	        {  
	            strText += (char)nBuffer;  
	        }  
	        buffRead.close();  
	    }  
	    catch (Exception ex)  
	    {  
	        System.out.println(ex.getMessage());  
	    }  
	      
	    return strText;  
	}  
	
	public static void subDirList(String source,ArrayList<String> list) {

		File dir = new File(source);
		File[] fileList = dir.listFiles();

		try {

			for (int i = 0; i < fileList.length; i++) {

				File file = fileList[i];

				if (file.isFile()) {

					// 파일이 있다면 파일 이름 출력
					logger.info("\t File name = {}" ,file.getName());
					list.add(file.getParent()+File.separator+file.getName());

				} else if (file.isDirectory()) {

					logger.info("Directory name = {}" , file.getName());
					// 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
//					list.add(file.getParent()+File.separator+file.getName());
					subDirList(file.getCanonicalPath().toString(),list);

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	

}
