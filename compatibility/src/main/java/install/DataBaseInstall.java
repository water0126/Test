package install;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.constant.GlobalValue;
import com.exeception.CommException;
import com.exeception.CommonErrCode;
import com.network.Connector;
import com.network.Server;
import com.network.Sftp;
import com.network.Ssh;
import com.utility.FileUtility;

import compatibility.vo.Binary;
import compatibility.vo.TestServer; 
/**
 * <pre>
 * 1. 개요        : config 파일을 서버에 전송한다.(config 파일 압축)   
 *              테스트 서버에서 테스트할 바이너리를 바이너리 서버에서 ftp 로 가져온다.
 *              config 파일을 tibero 디렉토리로 copy 한다.(server.profile ,tbdsn.tbr , tibero.tip , license.xml)  
 *              티베로를 설치 한다. (tb_create_db.sh) 
 * 2. 처리내용    :
 * </pre>
 * @date          : 2017. 10. 23.
 * @author        : min
 * @history       :
 */
public class DataBaseInstall extends InstallInfo{

	public static DataBaseInstall dbinstall = new DataBaseInstall();
	public static  DataBaseInstall getInstance(){
		return dbinstall;	
	}
	
	public static FileUtility fileutil;
	
	private static final Logger logger = LoggerFactory.getLogger(DataBaseInstall.class);	
	

	public void clearServer() throws Exception{
		tbClear();
		logger.info("{}",super.getSsh().execmd("rm -rf *"));		
	}
	
	
	public void tbClear() throws Exception{
		String [] versions  = {new String ("4"),new String ("5"),new String ("6")};
		for (String version :versions){
			setTbEnv(getDbInfo().getServer_home()+"/tibero"+version);
			logger.info("{}",super.getSsh().execmd("tbdown abnormal;rm -rf tibero"+version));
		}		
		logger.info("{}",getSsh().execmd("sh del_ipcs.sh"));	
	}
	

	public void putBinaryToServer() throws Exception{
		
		try{
		logger.info("Binary name : [{}]" ,getBinary().getPath()+File.separator+super.getBinary().getBinary_file_name());
		super.getSftp().put(super.getBinary().getPath()+File.separator+super.getBinary().getBinary_file_name());

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void configCopyToTbDir() throws Exception{
		
		String tbHome ="tibero"+super.getBinary().getVersion() ;
		try {
			//license.xml,tibero.tip, tbdsn.tbr,
			getSsh().execmd("cd ");
			logger.info("{}",getSsh().execmd("tar -xvzf "+super.getBinary().getBinary_file_name() ));				
//			logger.info("{}",getSsh().execmd("tar -xvzf "+ GlobalValue.configArchiveFilename ));
			logger.debug("{}",getSsh().execmd("cp -rf  config/del_ipcs.sh ."  ));
			logger.debug("{}",getSsh().execmd("cp -rf  config/server.profile ."  ));
			logger.debug("{}",getSsh().execmd("cp -rf config/tibero.tip "+ tbHome+"/config/tibero.tip"  ));		
			logger.debug("{}",getSsh().execmd("cp -rf config/tbdsn.tbr "+ tbHome+"/client/config/tbdsn.tbr"  ));
			logger.debug("{}",getSsh().execmd("cp -rf config/license/"+super.getBinary().getLicenseName() + " "+ tbHome+"/license/license.xml" ));
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setTbEnv(String tbhome) throws Exception{
		logger.debug("{}",getSsh().execmd("export TB_HOME="+tbhome
				+ ";export TB_SID=tibero"
				+ ";export NB_HOME=$TB_HOME"
				+ ";export NB_SID=$TB_SID"
				+ ";export TB_PROF_DIR=$TB_HOME/instance/$TB_SID"
				+ ";export PATH=.:$TB_HOME/bin:$TB_HOME/client/bin:/usr/sbin:$PATH"
				+ ";export LD_LIBRARY_PATH=.:$TB_HOME/lib:$TB_HOME/client/lib:/lib:/usr/lib:/usr/local/lib:/usr/lib/threads:$LD_LIBRARY_PATH"
				+ ";export LD_LIBRARY_PATH_64=$TB_HOME/lib:$TB_HOME/client/lib:/usr/lib/64:/usr/ucblib:/usr/local/lib"
				+ ";export LIBPATH=$LD_LIBRARY_PATH;echo $TB_HOME"));

	}
	
	public boolean dbInstall_test() throws Exception {
		boolean result = true;
		String tbHome =super.getDbInfo().getServer_home()+"/tibero"+super.getBinary().getVersion() ;
		setTbEnv("/home/sampler1/tibero6");
		// read it with BufferedReader
		InputStream is = null;
		BufferedReader br = null;
		String readLine = "";
		try {
			is =super.getSsh().tail("tbboot -v;echo TEST END");
			br = new BufferedReader(new InputStreamReader(is));
			byte[] buffer = new byte[1024];

			while (readLine!=null ) {
				readLine = br.readLine();
				logger.info("tail line {}:", readLine);
				if (!StringUtils.isEmpty(readLine)  ) {
					if (readLine.matches(".*Linux stopbugs.*")) {
						logger.info("{}",readLine);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			throw new CommException(CommonErrCode.DBINSTALL_ERR_0001, readLine);
		}

		return result;
	}
	public boolean dbInstall() throws Exception {
		boolean result = true;
		String tbHome =super.getDbInfo().getServer_home()+"/tibero"+super.getBinary().getVersion() ;
		setTbEnv(tbHome);
		// read it with BufferedReader
		InputStream is = null;
		BufferedReader br = null;
		String readLine = "";
		try {
			is =super.getSsh().tail("tb_create_db.sh");
			br = new BufferedReader(new InputStreamReader(is));

			Pattern p = Pattern.compile(GlobalValue.errorRegex);

			while (readLine != null) {
				readLine = br.readLine();
				logger.info("tail line {}:", readLine);
				if (!StringUtils.isEmpty(readLine)) {
					if (readLine.matches(GlobalValue.installEndRegex)) {
						logger.info("{}",GlobalValue.installEndRegex);
						break;
					}

					Matcher m = p.matcher(readLine);

					if (m.find()) {
						logger.error("error{}", readLine);
						throw new CommException(CommonErrCode.DBINSTALL_ERR_0001, readLine);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			throw new CommException(CommonErrCode.DBINSTALL_ERR_0001, readLine);
		} 
		return result;
	}
	
	
	public static void main(String[] args) throws Exception {
		String tbhome ="/home/sampler1/tibero6";
		DataBaseInstall dbinstall = new DataBaseInstall();
		try{
		

//		dbinstall.init(new Server("192.168.105.170","sampler1","sampler1"));
//		dbinstall.tbClear("/home/sampler1");
//		dbinstall.clearServer("/home/sampler1");
		
//		dbinstall.dbInstall(tbhome);
		logger.debug("END");
		}catch(Exception e){
			logger.debug(e.getMessage());
			logger.error("END1");
		}finally {
			dbinstall.close();
			logger.error("END2");
		}
	}

}
