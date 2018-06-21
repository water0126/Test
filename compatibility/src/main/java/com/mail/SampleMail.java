package com.mail;

public class SampleMail {


    public static void main(String args[]) throws Exception {
    	
	   	MailSenderInfo sender = new MailSenderInfo();
	   	
	   	sender.setHost("192.168.105.58");
	   	sender.setPort(25); 

	   	// 첨부파일 full path 로 지정 
		String[] attach_files ={"G:\\eclipse\\workspace\\DBQATft\\client2_192.168.5.116.agent.properties"} ;
    	
		sender.setSender("jungki_min@tmax.co.kr");
		sender.setSenderName("jungki_min");
    	
    	MailReceiverInfo mailreceive = new MailReceiverInfo();
		mailreceive.setAttach_files(attach_files);
    	mailreceive.setTo("jungki_min@tmax.co.kr");
    	mailreceive.setSubject("Agent error");
    	mailreceive.setContents("mail test contents");
    	
    	MailSender mail = new MailSender(sender);
    	mail.send(mailreceive);
    	
    }

}
