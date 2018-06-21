package compatibility;

import com.network.Connector;
import com.network.Server;
import com.network.Sftp;
import com.network.Ssh;

/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 5.
 * @author        : min
 * @history       :
 */
public class TestNetwork {
	public static void main(String[] args) {
		String host ="192.168.17.72";
		String user ="functest2";
		String password ="tibero";
		int connect_timeout =20*1000;
		int default_timeout =20*1000;
		
		Server server = new Server(host,user,password,connect_timeout,default_timeout);
		try {
			
//			Client ssh = (Client) Client.getClient("com.network.Ssh", server);
//			ssh.login();
//			System.out.println(ssh.execmd("ls -al"));		
//			
//			// read it with BufferedReader
//			InputStream is =null ;				
//			BufferedReader br =null;
//			String readLine ="";
//
//			
//			is = ssh.tail("sh loop.sh");
//			br = new BufferedReader(new InputStreamReader(is));			
//			while (readLine!=null){
//				readLine = br.readLine();
//				System.out.println("line :" + readLine);
//			}
//			
//			ssh.logout();
			Server testserver = new Server("192.168.17.72","functest2","tibero");
			Server binServer = new Server("192.168.105.34","binary","tiberoqm1234");
//

			
		    Connector ssh = Connector.getClient(Ssh.class, testserver);
		    ssh.login();
		    System.out.println(ssh.execmd("la -al"));
//			ftp.get(binDir,binaryName);		
		    ssh.logout();
		    ssh = null;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
