package com.network; 
 /**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 4.
 * @author        : min
 * @history       :
 */
public class Server {
	
	public static final int SSH_DEFAULT_TIMEOUT=20*1000;
	public static final int SSH_CONNECTION_TIMEOUT=20*1000;
	protected final static String  prompt = "$ ";
	
	private String host;
	private String user;
	private String password;
	private int connect_timeout;
	private int default_timeout;
	
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	public int getConnect_timeout() {
		return connect_timeout;
	}
	public void setConnect_timeout(int connect_timeout) {
		this.connect_timeout = connect_timeout;
	}
	
	public int getDefault_timeout() {
		return default_timeout;
	}
	public void setDefault_timeout(int default_timeout) {
		this.default_timeout = default_timeout;
	}
	
	public Server(String host, String user, String password, int connect_timeout, int default_timeout) {

		this.host = host;
		this.user = user;
		this.password = password;
		this.connect_timeout = connect_timeout;
		this.default_timeout = default_timeout;
	}
	
	public Server(String host, String user, String password) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.connect_timeout = SSH_DEFAULT_TIMEOUT;
		this.default_timeout = SSH_CONNECTION_TIMEOUT;
	}
	
	@Override
	public String toString() {
		return "Server [host=" + host + ", user=" + user + ", password=" + password + ", connect_timeout="
				+ connect_timeout + ", default_timeout=" + default_timeout + "]";
	}

	
	

}
