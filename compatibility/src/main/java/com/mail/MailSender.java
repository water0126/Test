package com.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utility.StringUtil;

/**
 * @Class Name : MailSender.java
 * @Description : 메일 전송해주는 객체
 * @Modification Information
 *
 *    수정일                               수정자             수정내용
 *    -------        -------     -------------------
 *    2015.03.02.     전혜나		최초작성
 *
 */
public class MailSender {
	private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
	
	// 보내는 사람 정보
	Properties senderProp = new Properties(); // 보내는 사람의 정보를 담은 객체
	private Address sender_addr;
	private String auth_id; // 메일서버 인증을 위한 사용자 아이디
	private String auth_password; // 메일서버 인증을 위한 사용자 패스워드
	private boolean starttlsEnable;
	private static final String MAIL_TEXT_ENCODE="UTF-8"; // 텍스트 인코딩 설정.
	private boolean isDebug;
	
	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}
	
	public boolean isDebug() {
		return this.isDebug;
	}
	
	public void setStarttlsEnable(boolean starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}
	
	public boolean getStarttlsEnable() {
		return this.starttlsEnable;
	}	
	public Properties getMialProterties() {
		return this.senderProp;
	}	

	/**
	 * 생성자 
	 * - 객체를 생성하게 되면 공통 설정 정보로부터 메일 전송을 위한 기본 정보를 가져와서 설정한다. 
	 * @param senderInfo  - 메일 보내는 서버에 대한 정보가 저장된 객체
	 * @throws UnsupportedEncodingException
	 */
	public MailSender(MailSenderInfo senderInfo) throws UnsupportedEncodingException {
		// 메일전송을 위한 properties 객체 설정.
		senderProp.put("mail.smtp.host", senderInfo.getHost());
		senderProp.put("mail.smtp.port", senderInfo.getPort());
		senderProp.put("mail.smtp.ssl.trust", senderInfo.getHost());  // ?? 좀 더 설정 이유를 찾아보자~~
		if(senderInfo.getHost().equals("smtp.gmail.com")) this.starttlsEnable = true;
		
		// 메일전송을 하는 전달자 설정.
		this.sender_addr = setAddress(senderInfo.getSender(), senderInfo.getSenderName());
		this.auth_id = senderInfo.getUsername();
		this.auth_password = senderInfo.getUserpasswd();
	}
	
	/**
	 * 메일 주소로 입력받은 문자열을 address 객체로 전환
	 * @param addr - 메일주소
	 * @param name - 메일사용자 이름
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private Address setAddress(String addr,  String name) throws UnsupportedEncodingException {
		Address mailAddr = null;
		try {
			mailAddr = new InternetAddress(addr, MimeUtility.encodeText(name, MAIL_TEXT_ENCODE, "B"));
		} catch (UnsupportedEncodingException e) {
			// 표시 이름이 인식할 수 없는 문자(Base 64 기준)일 때 발생
			logger.error("{}", e);
			throw e;
		} 
		return mailAddr;
	}
	
	/**
	 * 메일 주소로 입력받은 문자열을 address 객체로 전환
	 * @param addr - 메일주소
	 * @return
	 * @throws AddressException 
	 */
	private Address setAddress(String addr) throws AddressException {
		Address mailAddr = null;
		try {
			mailAddr = new InternetAddress(addr);
		} catch (AddressException e) {
			// 받는 사람이 인식할 수 없는 이름일 때 발생하는 경우
			logger.error("The recipient's name is not recognized. : {}\n{}", e.getMessage(), e);
			throw e;
		} 
		return mailAddr;
	}	
	
	/**
	 * 메일을 전송한다.
	 * @param reciverInfo - 전달할 메일 주소, 내용 등을 담은 객체 
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	public void send(MailReceiverInfo receiverInfo) throws Exception {		
		Message msg = new MimeMessage(createMailSession()); // 보내는 메시지 객체
		
		try {
			if (StringUtil.isNullOrEmpty(receiverInfo.getTo())) 
				throw new NullPointerException("받는 사람 주소가 없습니다.");
			
			// 메일을 보내기 위한 객체 설정.
			msg = this.setSendMessage(msg, receiverInfo);
			// 메일 전송
			Transport.send(msg);
			logger.info("Mail Send Success.");
		} catch (NullPointerException e) {
			logger.error("No recipient addresses. \n", e);
			throw e;
		} catch (MessagingException e) { 
			logger.error("An error has occurred in the initialization of the message. \n", e);
			throw e;
		} catch (UnsupportedEncodingException e) {
			logger.error("Can not be Encoded subject. \n", e);
			throw e;
		}
	}
	
	/**
	 * 메일을 보내기 위한 객체 설정.
	 * @param msg
	 * @param reciverInfo
	 * @return
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	private Message setSendMessage(Message msg, MailReceiverInfo senderInfo) throws MessagingException, UnsupportedEncodingException {
		msg.setHeader("content-type", "text/html;charset=" + MAIL_TEXT_ENCODE);			
		// 보내는 사람.
		msg.setFrom(this.sender_addr); 
		
		// 받는 사람 설정 
		msg.setRecipients(Message.RecipientType.TO, setRecipients(senderInfo.getTo())); // 받는 사람 타입			
		if (StringUtil.isNotNullOrEmpty(senderInfo.getCc())) 
			msg.setRecipients(Message.RecipientType.CC, setRecipients(senderInfo.getCc()));
		if (StringUtil.isNotNullOrEmpty(senderInfo.getBcc())) 
			msg.setRecipients(Message.RecipientType.BCC, setRecipients(senderInfo.getBcc()));
		
		// 메일 제목
		msg.setSubject(MimeUtility.encodeText(senderInfo.getSubject(), MAIL_TEXT_ENCODE, "B"));
		
		// 메일 내용 
		msg.setContent(this.setMailContents(senderInfo.getContents(), senderInfo.getAttach_files()));
		
		// 보내는 날짜
		msg.setSentDate(new java.util.Date());

		return msg;
	}
	
	/**
	 * 메일 내용을 설정한다.
	 * @param msg - 메일에 작성할 메세지
	 * @param files - 첨부파일
	 * @return
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	private Multipart setMailContents(String msg, String[] files) throws MessagingException, UnsupportedEncodingException {
		Multipart contents = new MimeMultipart();
		MimeBodyPart body = new MimeBodyPart();		
		
		// 메일 내용 설정
		try {
			body.setContent( msg.replaceAll("\n", "<br>"), "text/html;charset=" + MAIL_TEXT_ENCODE);	
			contents.addBodyPart(body);
			
			if(StringUtil.isNotNullOrEmpty(files)) {
				for(String file : files) {
					logger.debug("attch file : " + file);
					MimeBodyPart attach = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(file);
					attach.setDataHandler(new DataHandler(fds));
					attach.setFileName(iso8859(fds.getName()));
					contents.addBodyPart(attach);
				}
			}			
		} catch (MessagingException e) {
			logger.error("{}", e);
			throw e;
		} catch (UnsupportedEncodingException e) {
			logger.error("{}", e);
			throw e;
		}
		
		return contents;
	}
	
	/**
	 * 파일명 인코딩.
	 * @param strStr
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 */
	private static String iso8859(String strStr) throws java.io.UnsupportedEncodingException     
	{
		if (strStr == null) return  null;
		else return new String(strStr.getBytes("KSC5601"), "8859_1");
	}
	
	/**
	 * 메일을 받는 사람의 주소를 설정한다.
	 * @param recipients - 입력받은 받는 사람의 문자열을 ";"로 분리하여 주소 객체로 전달한다.
	 * @return
	 * @throws AddressException 
	 */
	private Address[] setRecipients(String recipients) throws AddressException {
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer stMailAddress = new StringTokenizer(recipients, ";");
		
		while(stMailAddress.hasMoreTokens()) list.add(stMailAddress.nextToken());
		Address[] addrList = new Address[list.size()];
		for(int i=0; i < list.size(); i++) {
			addrList[i] = setAddress(list.get(i));
		}
	
		return addrList;
	}
	
	/**
	 * 메일을 전달할 세션을 생성한다.
	 * @return
	 */
	private Session createMailSession() {
		Authenticator authenticator = null;

		// gmail로 보내는 거라면 설정 필요.
		if(starttlsEnable)
			senderProp.put("mail.smtp.starttls.enable", "true"); // gmail 인증용 Secure Socket Layer(SSL) 설정
		
		// debug 출력 여부 설정
		if(isDebug)
			senderProp.put("mail.debug", "true");		
		
		
		// 사용자 아이디가 등록되어있으면...인증이 필요하므로 관련 옵션을 설정한다.
		if (StringUtil.isNotNullOrEmpty(auth_id)) {
			senderProp.put("mail.smtp.auth", "true");
			authenticator = new SMTPAuthenticator(this.auth_id, this.auth_password);
			return Session.getInstance(senderProp, authenticator);
		}else{
			return Session.getInstance(senderProp);
		}
		
	}
	
	/**
	 * 메일 전송 권한 설정
	 * @author  : soul
	 * @date    : 2015. 2. 8.
	 * @desc    :
	 *
	 */
	class SMTPAuthenticator extends Authenticator {
		PasswordAuthentication passwordAuthentication;

		SMTPAuthenticator(String userName, String password) {
			passwordAuthentication = new PasswordAuthentication(userName, password);

		}
		public PasswordAuthentication getPasswordAuthentication() {
			return passwordAuthentication;
		}
	}
}
