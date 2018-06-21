package com.exeception;

import java.io.IOException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2017. 9. 3.
 * @author        : min
 * @history       :
 */
public enum CommonErrCode implements ErrCode{
	
	/**SSH 접속 실패*/ 	
	SSH_ERR_0001("SSH_ERR_0001", "SSH [%1] 접속에 실패 하였습니다."),
	/**SSH 명령 실패*/
	SSH_ERR_0002("SSH_ERR_0002", "SSH 명령 [%1] 실패"),
	/**SSH Tail Command 실패*/
	SSH_ERR_0003("SSH_ERR_0003", "SSH Tail Command [%1] 실패"),

	/**SFTP 접속 실패*/ 	
	SFTP_ERR_0001("SFTP_ERR_0001", "FTP [%1] 접속에 실패 하였습니다."),
	/**SFTP CD 실패*/
	SFTP_ERR_0002("SFTP_ERR_0002", "FTP [%1] No such file Directory"),
	/**SFTP  Diretory 를 생성 실패*/
	SFTP_ERR_0003("SFTP_ERR_0003", "FTP [%1] Diretory 를 생성 할수 없습니다."),
	/**SFTP  File Uplogad 실패*/
	SFTP_ERR_0004("SFTP_ERR_0004", "FTP File: [%1] 업로드에 실패 하였습니다.."),
	/**SFTP  File downlogad 실패*/
	SFTP_ERR_0005("SFTP_ERR_0005", "FTP File: [%1] 다운로드에 실패 하였습니다.."),
	
	/**Tibero Install Error*/ 	
	DBINSTALL_ERR_0001("DBINSTALL_ERR_0001", "Tibero Install Fail :[%1]"),
	/**Tibero Install Error*/ 	
	SAMPLER_TEST_ERR_0001("DBINSTALL_ERR_0001", "Sampler Test Fail :[%1]");
;

	
	

	
	private String errCode;
	private String msg;

	public String getErrCode() {
		return this.errCode;
	}

	public String getMessage(String... args) {
		return ErrCodeUtil.parseMessage(this.msg, args);
	}

	CommonErrCode(String errCode, String msg) {
	      this.errCode = errCode;
	      this.msg = msg;
	}

}
