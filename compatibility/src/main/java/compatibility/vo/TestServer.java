package compatibility.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.network.Server;
import com.network.Sftp;

/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 11.
 * @author        : min
 * @param <T>
 * @history       :
 */
public class TestServer {
	private static final Logger logger = LoggerFactory.getLogger(TestServer.class);
	private Server server; 
	
	private int server_seq;
	private String server_type;
	private String os_type;
	private String os_version;
	private String host_name;
	private String server_ip;
	private String server_id;
	private String server_passwd;
	private String server_home;
	private int tibero_listen_port;
	private String client_add_tbpccfg;
	
	public int getServer_seq() {
		return server_seq;
	}
	public void setServer_seq(int server_seq) {
		this.server_seq = server_seq;
	}
	public String getServer_type() {
		return server_type;
	}
	public void setServer_type(String server_type) {
		this.server_type = server_type;
	}
	public String getOs_type() {
		return os_type;
	}
	public void setOs_type(String os_type) {
		this.os_type = os_type;
	}
	public String getOs_version() {
		return os_version;
	}
	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}
	public String getHost_name() {
		return host_name;
	}
	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}
	public String getServer_ip() {
		return server_ip;
	}
	public void setServer_ip(String server_ip) {
		this.server_ip = server_ip;
	}
	public String getServer_id() {
		return server_id;
	}
	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}
	public String getServer_passwd() {
		return server_passwd;
	}
	public void setServer_passwd(String server_passwd) {
		this.server_passwd = server_passwd;
	}
	public String getServer_home() {
		return server_home;
	}
	public void setServer_home(String server_home) {
		this.server_home = server_home;
	}
	public int getTibero_listen_port() {
		return tibero_listen_port;
	}
	public void setTibero_listen_port(int tibero_listen_port) {
		this.tibero_listen_port = tibero_listen_port;
	}
	public String getClient_add_tbpccfg() {
		return client_add_tbpccfg;
	}
	public void setClient_add_tbpccfg(String client_add_tbpccfg) {
		this.client_add_tbpccfg = client_add_tbpccfg;
	}
	



	@Override
	public String toString() {
		return "TestServer [server=" + server + ", server_seq=" + server_seq + ", server_type=" + server_type
				+ ", os_type=" + os_type + ", os_version=" + os_version + ", host_name=" + host_name + ", server_ip="
				+ server_ip + ", server_id=" + server_id + ", server_passwd=" + server_passwd + ", server_home="
				+ server_home + ", tibero_listen_port=" + tibero_listen_port + ", client_add_tbpccfg="
				+ client_add_tbpccfg + "]";
	}
 
	
	
	
}
