package install;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.network.Connector;
import com.network.Server;
import com.network.Sftp;
import com.network.Ssh;
import com.utility.FileUtility;

import compatibility.vo.Binary;
import compatibility.vo.ScenarioRepository;
import compatibility.vo.TestServer;

/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 21.
 * @author        : min
 * @history       :
 */
public abstract class InstallInfo {
	private static final Logger logger = LoggerFactory.getLogger(InstallInfo.class);	
	private Server server ;
	private Binary binary;
	private TestServer testServer;
	private ScenarioRepository svn;
	
	public static FileUtility fileutil;
	private Connector ssh;
	private Connector sftp;
	
	protected Connector getSsh() {
		return ssh;
	}

	protected void setSsh(Connector ssh) {
		this.ssh = ssh;
	}

	protected Connector getSftp() {
		return sftp;
	}

	protected void setSftp(Connector sftp) {
		this.sftp = sftp;
	}


	public void init() throws Exception {		
		ssh = Connector.getClient(Ssh.class,server);
		ssh.login();
		
		sftp = Connector.getClient(Sftp.class,server );		
		sftp.login();		
	} 
	
	public void close() throws Exception {
		ssh.logout();
		sftp.logout();
	}
	
	public void setInfo(TestServer testServer) {
		this.server = new Server(testServer.getServer_ip(),testServer.getServer_id(),testServer.getServer_passwd());
		this.testServer = testServer;
	}
	
	public Server getServerInfo() {		
		return server;
	}
	
	public TestServer getDbInfo() {		
		return testServer;
	}

	public Binary getBinary() {
		return binary;
	}

	public void setBinary(Binary binary) {
		this.binary = binary;
	}
	
	public ScenarioRepository getSvn() {
		return svn;
	}
	public void setSvn(ScenarioRepository svn) {
		this.svn = svn;
	}
}
