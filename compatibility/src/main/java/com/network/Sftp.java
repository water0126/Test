package com.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exeception.CommException;
import com.exeception.CommonErrCode;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * @Class Name  : sftpClient.java
 * @Description : sftp/scp 접속 관련 클라이언트
 * @Modification Information
 *
 */
public class Sftp extends Connector{
	private static final Logger logger = LoggerFactory.getLogger(Sftp.class);
	
	private Session session = null;
	private Channel channel = null;	
	private ChannelSftp sftpClient = null;  
	public static final int SFTP_PROT=22;
	

	public boolean login() 	 throws CommException {	
		JSch jsch = new JSch();

		// 세션 객체를 생성한다.
		try {

			session = jsch.getSession(super.getServer().getUser(), super.getServer().getHost(), SFTP_PROT);

	
			// 패스워드를 설정한다.
			session.setPassword(super.getServer().getPassword());
			
			// config 설정  
			session.setConfig("StrictHostKeyChecking", "no"); // 호스트 정보를 검사하지 않는다.
			
			// 접속한다.
			session.connect();
			
			// sftp 객체 열기
			channel = session.openChannel("sftp");
			channel.connect();
			
			sftpClient = (ChannelSftp) channel;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logout();
			throw new CommException(CommonErrCode.SFTP_ERR_0001,super.getServer().getHost());
			
		}
		return true;
	}
	
	public boolean logout() { 
		// sftp 연결 끊기.
        if (sftpClient != null || sftpClient.isClosed() != true) sftpClient.quit(); 
        logger.debug("sftpClient is closed? : " + sftpClient.isClosed());
        
        // 채널 정리 하기 
		if (channel != null || channel.isConnected() == true) channel.disconnect();
		logger.debug("channel is connected ? : " + channel.isConnected());
		
		 // 세션 정리 하기 (안끊으면 프로세스가 살아있음.)
		if (session != null || session.isConnected() == true) session.disconnect();
		logger.debug("session is connected ? : " + session.isConnected());
		return true;
	}
	
	
	/**
	 * 파일 다운로드
	 * @param srcpath - 리포트 파일의 경로 
	 * @param destfile - 다운받을 파일명 및 경로 
	 * @return 
	 * @throws SftpException
	 * @throws IOException
	 * @throws JSchException
	 */
	
	public File get(String downloadFile) throws IOException, Exception  {
		FileOutputStream out = null;
		
		logger.info(" File Download : [" + downloadFile + "]" );
		try {			
        	out = new FileOutputStream(new File(downloadFile));
			sftpClient.get(downloadFile, out);
		} catch (SftpException e) {
			logout();
			throw new CommException(CommonErrCode.SFTP_ERR_0005,downloadFile);
		} finally {
			out.close();
		}
		return null;
	}
	

	/**
	 * 파일 다운로드
	 * @param srcpath - 리포트 파일의 경로 
	 * @param destfile - 다운받을 파일명 및 경로 
	 * @return 
	 * @throws SftpException
	 * @throws IOException
	 * @throws JSchException
	 */
	@Override
	public File get(String srcPath, String downloadFile) throws IOException, Exception  {
		
		if (srcPath != null) {
			logger.info(" Download Path : [" + srcPath + "]");
			cd(srcPath);
		}
		FileOutputStream out = null;

		logger.info(" File Download : [" + downloadFile + "]");
		try {
			out = new FileOutputStream(new File(downloadFile));
			sftpClient.get(downloadFile, out);

		} catch (SftpException e) {
			logout();
			throw new CommException(CommonErrCode.SFTP_ERR_0005,downloadFile);
		} finally {
			out.close();
		}
		return null;
	}
	
	
	/**
	 * 파일리스트를 sftp를 이용하여 업로드한다.
	 * @param destPath - 업로드하는 경로
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	@Override
	public void put(String uploadFile) throws Exception {
		try {
			FileInputStream in = null; // 업로드할 파일을 저장할 객체
			in = new FileInputStream(new File(uploadFile));
			// 파일을 업로드 한다.
			sftpClient.put(in, new File(uploadFile).toPath().getFileName().toString());

			if (in != null)
				in.close();

		} catch (SftpException e) {
			logout();
			e.printStackTrace();
			throw new CommException(CommonErrCode.SFTP_ERR_0003, uploadFile);
		}

	}
	
	/**
	 * 파일리스트를 sftp를 이용하여 업로드한다.
	 * @param fileList - 업로드할 파일 리스트
	 * @param destPath - 업로드하는 경로
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */

	public void put(String src, String dst) throws Exception {
		try {
			FileInputStream in = null; // 업로드할 파일을 저장할 객체
			in = new FileInputStream(new File(src));
			// 파일을 업로드 한다.
			sftpClient.put(in, new File(dst).toPath().getFileName().toString());

			if (in != null)
				in.close();

		} catch (SftpException e) {
			logout();
			throw new CommException(CommonErrCode.SFTP_ERR_0003, src,dst);
		}
	}

	/**
	 * 리모트 서버에 디렉토리를 생성한다.
	 * @param path
	 * @throws SftpException 
	 */
	private void mkdir(String path) throws Exception {
		final String FOLDER_SEPARATOR = "[\\/|\\\\]";
		try {
			// 마지막 경로에 "/" 또는 "\" 문자가 있으면 지우고, 각 폴더별로 쪼개서 배열에 집어 넣는다.
			String[] pathArray = path.trim().replace(FOLDER_SEPARATOR + "$", "").split(FOLDER_SEPARATOR);

			// 하위 폴더 부터 한 단계씩 폴더를 이동하면서, 해당 폴더가 존재하지 않으면 생성하고 이동한다.
			for (String dir : pathArray) {
				try {
					sftpClient.cd(dir);
				} catch (SftpException e) {
					// 이동하려는 폴더가 없으면 예외처리로 들어옴. - 폴더 생성하고 이동한다.
					logger.debug("create directory [" + dir + "]");
					sftpClient.mkdir(dir);
					sftpClient.cd(dir);
				}
			}
		} catch (SftpException e) {
			logout();
			throw new CommException(CommonErrCode.SFTP_ERR_0003,path);
		}
	}

	@Override
	public void cd(String path) throws CommException {
		// TODO Auto-generated method stub
		try {
			sftpClient.cd(path);
			
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			logout();
			throw new CommException(CommonErrCode.SFTP_ERR_0002,path);

		}
		
	}



}
