package com.mail;

import java.util.Arrays;
/**
 * @Class Name : mailReceiverInfo.java
 * @Description : 메일을 받는 사람과 전달할 내용을 설정한다.
 * @Modification Information
 *
 *    수정일                               수정자             수정내용
 *    -------        -------     -------------------
 *    2015.02.06.     전혜나		최초작성
 *
 */

public class MailReceiverInfo {
	private String subject; //메일 제목
	private String contents; // 메일 내용
	private String[] attach_files; // 첨부파일
	private String to; // 받는 사람 
	private String cc; // 참조로 받는 사람
	private String bcc; // 숨은 참조로 받는 사람
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String[] getAttach_files() {
		return attach_files;
	}
	public void setAttach_files(String[] attach_files) {
		this.attach_files = attach_files;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {		
		this.bcc = bcc;
	}
	@Override
	public String toString() {
		return "MailReceiverInfo [subject=" + subject + ", contents=" + contents
				+ ", attach_files=" + Arrays.toString(attach_files) + ", to="
				+ to + ", cc=" + cc + ", bcc=" + bcc + "]";
	}
}
