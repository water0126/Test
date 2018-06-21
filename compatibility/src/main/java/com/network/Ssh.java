package com.network;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exeception.CommException;
import com.exeception.CommonErrCode;
//import com.jcraft.jsch.Logger;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.configuration.SshConnectionProperties;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;
import com.utility.StringUtil;
/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 4.
 * @author        : min
 * @history       :
 */
public class Ssh extends Connector {
	

	private SshClient sshClient = new SshClient();
	private SessionChannelClient session;

	private static final int SSH_PROT=22;
	private Log logger = LogFactory.getLog(Ssh.class);	
	
	public boolean login() throws Exception {	
		
        try {
            if (super.getServer().getHost() == null || super.getServer().getUser() == null || super.getServer().getPassword() == null) {
                System.out.println("Sftp login Parameter is null!");
            }
 
            sshClient = new SshClient();
//            sshClient.setSocketTimeout(0);
            
            SshConnectionProperties properties = new SshConnectionProperties();
            properties.setHost(super.getServer().getHost());
            properties.setPort(SSH_PROT);
            properties.setPrefPublicKey("ssh-dss");
            // Connect to the host
            sshClient.connect(properties, new IgnoreHostKeyVerification());
 
			// # 패스워드를 이용한 인증 접속        
			// ------------------------------------------------------------
			PasswordAuthenticationClient authClient = new PasswordAuthenticationClient();
			authClient.setUsername(super.getServer().getUser());
			authClient.setPassword(super.getServer().getPassword());
			 //------------------------------------------------------------

            int result = sshClient.authenticate(authClient);
 
            if (result != AuthenticationProtocolState.COMPLETE) {
                throw new Exception("Login failed");
            }    
 
            // SSH 터미널 커맨드 실행용
            session = sshClient.openSessionChannel();
            session.requestPseudoTerminal("vt100", 80, 25, 0, 0 , "");
            session.startShell();
        } catch (Exception e) {
			logout();			
			throw new CommException(CommonErrCode.SSH_ERR_0001,super.getServer().getHost());

        }   

        return true;
	}

	
	public boolean logout() throws Exception {
		boolean rtn = false;
		try {
			if (session != null ||!session.isClosed()) {			
				session.close();
				logger.info("session isClosed ? : " + session.isClosed());

			}
			if (sshClient != null ||!sshClient.isConnected()){
				sshClient.disconnect();		
				logger.info("ssh isConnected ? : " + sshClient.isConnected());
			}
			rtn = true;

		} catch(Exception e) {
	    	e.printStackTrace();
	    	logger.error(e);
		}
	
		return rtn;
		
	}
	

	
	@Override
	public String execmd(String cmd) throws Exception {

		StringBuffer rtnStr = new StringBuffer();
		try {

			if (session == null)
				throw new Exception("Session is not connected!");
			OutputStream out = session.getOutputStream(); // Write commands to
															// the output stream
			out.write((cmd.trim() + "\n\n").getBytes()); // 행 단위 명령을 위해 "\n" 추가
			out.flush();
			InputStream in = session.getInputStream(); // Read results from the
														// input stream

			byte[] buffer = new byte[1024];
			boolean promptReturned = false;
			String output = null;
			int read = 0; // Readed Byte
			int length = 0; // output length
			int cnt = 0;

			while (promptReturned == false && (read = in.read(buffer)) > 0) {
				output = new String(buffer, 0, read);
				if (cnt == 1) {
					length += output.trim().length(); // 사용자로부터 입력받은 커맨드를 저장하지
														// 않기 위해 output의 결과 값 길이
														// 체크.
					if (length >= cmd.length() && output.indexOf(super.getServer().prompt) == -1) {
						// logger.debug("length : " + length + ", cmd length : "
						// + cmd.trim().length() + ", output : " + output); //
						rtnStr.append(output.toString());
					}
				}
				if (StringUtil.isNotNullOrEmpty(output) && output.indexOf(super.getServer().prompt) >= 0) {
					++cnt;
					if (cnt >= 2)
						promptReturned = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logout();			
			throw new CommException(CommonErrCode.SSH_ERR_0002, cmd);

		}
		return rtnStr.toString();
	}
    
	@Override
	public InputStream tail(String cmd) throws Exception {
		
		try {
			
			if (session == null) throw new Exception("Session is not connected!");
			OutputStream out = session.getOutputStream(); // Write commands to the output stream		
			out.write((cmd.trim() + "\n\n").getBytes()); // 행 단위 명령을 위해 "\n" 추가
			out.flush();		
			
		} catch (Exception e) {
			logger.error("{}", e);
			throw new Exception("Tail UnExpected Exception!");
		}	

		return session.getInputStream();
	}

	
}
