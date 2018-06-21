package com.network;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2017. 8. 17.
 * @author        : min
 * @history       :
 */
public abstract class Connector {

	private static Server server;
	public static Connector getClient(Class<?> cls,Server server) throws IllegalAccessException, InstantiationException {
		Logger.getLogger("com.sshtools").setLevel(Level.OFF);
		setServer(server);	
		return (Connector)cls.newInstance();
	}

	public static Server getServer() {
		return server;
	}

	public static void setServer(Server server) {
		Connector.server = server;
	}
	
	public boolean login() throws Exception{
		return login();
	}
	
	public boolean logout() throws Exception{
		return logout();
	}
	
	public String execmd(String cmd) throws Exception{
		return execmd(cmd);
	}
	
	public InputStream tail(String cmd) throws Exception{
		return tail(cmd);
	}
	
	public void cd(String path) throws Exception{
		cd(path);
	}
	
	public File get(String srcPath, String downloadFile) throws Exception{
		return get(srcPath,downloadFile);
	}
	
	public void put(String uploadFile) throws Exception{
		put(uploadFile);
	}


}
