package com.mail;

/**
 * @Class Name : mailSenderInfo.java
 * @Description : 메일을 보내는 사람에 대한 정보를 설정한다.
 * @Modification Information
 *
 *    수정일                               수정자             수정내용
 *    -------        -------     -------------------
 *    2015.02.06.     전혜나		최초작성
 *
 */
public class MailSenderInfo {
	 /** 메일 전송 host (mail.smtp.host) **/
	 private String host;
	 /** 메일 전송 포트 (mail.smtp.port) **/
	 private int port;
	 /** 메일 보내는 사람의 주소 **/
	 private String sender;
	 /** 메일 보내는 사람 이름 **/
	 private String senderName;
	 /** 메일 계정 아이디 **/
	 private String username;
	 /** 메일 계정 패스워드 **/
	 private String userpasswd;
	 
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpasswd() {
		return userpasswd;
	}
	public void setUserpasswd(String userpasswd) {
		this.userpasswd = userpasswd;
	}
	@Override
	public String toString() {
		return "mailSenderInfo [host=" + host + ", port=" + port + ", sender="
				+ sender + ", senderName=" + senderName + ", username="
				+ username + ", userpasswd=" + userpasswd + "]";
	}
	
}
