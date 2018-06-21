package install;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.constant.GlobalValue;
import com.exeception.CommException;
import com.exeception.CommonErrCode;
import com.network.Connector;
import com.network.Server;
import com.network.Sftp;
import com.network.Ssh;

import compatibility.dao.CommDao;
import compatibility.dao.ScenarioRepositoryDao;
import compatibility.vo.Binary;
import compatibility.vo.ScenarioRepository;
import compatibility.vo.TestServer;

/**
 * <pre>
 * 1. 개요        : Tibero Client Setting 
 *              Scenario svn File update
 * 2. 처리내용    :
 * </pre>
 * @date          : 2017. 10. 23.
 * @author        : min
 * @history       :
 */
public class AgentInstall extends InstallInfo {
	public static String agentDir ="agent";
	public static String prompt ="$ ";
	
	public static AgentInstall dbinstall = new AgentInstall();
	public static  AgentInstall getInstance(){
		return dbinstall;	
	}

	private static final Logger logger = LoggerFactory.getLogger(AgentInstall.class);	

	
	public void clear() throws Exception{
//		super.getSsh().execmd("kill -9 -1");
		super.getSsh().execmd("rm -rf "+ agentDir);
		super.getSsh().execmd("rm -rf *");
		
		
	}
	
	public void setAgent() throws Exception{
		super.getSsh().execmd("rm -rf * ");
		super.getSsh().execmd("mkdir "+agentDir);
		
	}
	
	public void samplerLibupdate(List<String> filenames) throws Exception{
		super.getSsh().execmd("cd $HOME");
		for (String filename : filenames) {
			logger.info("{}",super.getSsh().execmd("cd "+agentDir));
			logger.info("{}",super.getSsh().execmd("svn export "
		                                    + super.getSvn().getSvn_url() 
					                        + filename 
					                        +" --username "+super.getSvn().getId()
					                        +" --password "+super.getSvn().getPassword()+" --non-interactive "));
			super.getSsh().execmd("cd $HOME");
		}
		logger.info("{}",super.getSsh().execmd("cd "+agentDir +" ;chmod -Rf  755 *"));
	}
	
	
	public void scenarioUpdate(List<String> filenames) throws Exception{
		super.getSsh().execmd("cd $HOME");
		for (String filename : filenames) {
			logger.info("{}",super.getSsh().execmd("cd "+agentDir));
			logger.info("{}",super.getSsh().execmd("svn checkout "
					         + super.getSvn().getSvn_url() 
					         + filename 
		                     +" --username "+super.getSvn().getId()
		                     +" --password "+super.getSvn().getPassword()+" --non-interactive " 
		                     + filename));
			super.getSsh().execmd("cd $HOME");
		}
		logger.info("{}",super.getSsh().execmd("chmod 755 *"));
				
	}
	
	public void tbClientSet(List<Binary> binarys) throws Exception{
		

        for(Binary binary : binarys) {
        	logger.debug("{}",binary.getBinary_file_name());
        	super.getSsh().execmd("cd $HOME");
        	String remoteFixsetBinaryDir =GlobalValue.remoteAllBinDir+"/"+binary.getVersion()+"/"+binary.getFixset();        	
        	super.getSsh().execmd("mkdir -p " +remoteFixsetBinaryDir);
        	super.getSftp().cd(remoteFixsetBinaryDir);
        	super.getSftp().put(binary.getPath()+File.separator+binary.getBinary_file_name());
        	super.getSftp().cd("../../..");
        	super.getSsh().execmd("cd "+remoteFixsetBinaryDir+"; tar xvzf "+binary.getBinary_file_name());
        	configCopyToTbDir(binary.getVersion(),remoteFixsetBinaryDir);
        	
        }
	}

	public void configCopyToTbDir(String version,String FisxsetBinaryDir) throws Exception{
		String tiberoDir = FisxsetBinaryDir+"/"+"tibero"+version;		
		String jdbcfile = "tibero"+version+"-jdbc.jar" ;
		logger.debug("tiberoDir:{} jdbcfile:{}", tiberoDir,jdbcfile);
		super.getSsh().execmd("cd $HOME");	
		logger.info("{}",super.getSsh().execmd("cp -rf $HOME/config/sampler.properties "+ agentDir));
		logger.info("{}",super.getSsh().execmd("cp -rf $HOME/config/dbqa.properties "+  agentDir));
		logger.info("{}",super.getSsh().execmd("cp -rf $HOME/config/server.profile "+  agentDir));
		logger.info("{}",super.getSsh().execmd("cp -rf config/tbdsn.tbr "+ tiberoDir+"/client/config/tbdsn.tbr"  ));
		logger.info("{}",super.getSsh().execmd("cp -rf config/tbpc.cfg "+ tiberoDir+"/client/config/tbpc.cfg"  ));
		logger.info("{}",super.getSsh().execmd("cp -rf " +agentDir+"/resource/include/* "+ tiberoDir+"/client/include/"  ));
		logger.info("{}",super.getSsh().execmd("cp -rf " + tiberoDir+"/client/lib/jar/"+jdbcfile +" "+  tiberoDir+"/client/lib/jar/tibero-jdbc.jar "));
		logger.info("{}",super.getSsh().execmd("cp -rf " + tiberoDir+"/client/lib/jar/"+jdbcfile +" "+  agentDir+"/lib/"+jdbcfile));
		logger.info("{}",super.getSsh().execmd("cp -rf " + tiberoDir+"/client/lib/jar/"+jdbcfile +" "+  agentDir+"/lib/tibero-jdbc.jar"));

	}
	

	public void runSampler(String tbhome) throws Exception{
		
		
		InputStream is = null;
		BufferedReader br = null;
		try{
		String line = "";
		super.getSsh().execmd("cd $HOME");
		
		super.getSsh().execmd("export TB_HOME="+tbhome
				+ ";export TB_SID=tibero"
				+ ";export NB_HOME=$TB_HOME"
				+ ";export NB_SID=$TB_SID"
				+ ";export TB_PROF_DIR=$TB_HOME/instance/$TB_SID"
				+ ";export PATH=.:$TB_HOME/bin:$TB_HOME/client/bin:/usr/sbin:$PATH"
				+ ";export LD_LIBRARY_PATH=.:$TB_HOME/lib:$TB_HOME/client/lib:/lib:/usr/lib:/usr/local/lib:/usr/lib/threads:$LD_LIBRARY_PATH"
				+ ";export LD_LIBRARY_PATH_64=$TB_HOME/lib:$TB_HOME/client/lib:/usr/lib/64:/usr/ucblib:/usr/local/lib"
				+ ";export LIBPATH=$LD_LIBRARY_PATH");
		logger.info("TB_HOME:{}",super.getSsh().execmd("echo $TB_HOME"));
		is = super.getSsh().tail("cd "+agentDir+" ; sh runV2.sh 10465 \"file\" \"\" 21 ;echo ' < New Sampler Compatibility Test End> '");
		br = new BufferedReader(new InputStreamReader(is));
		int endline = 0;
		while (line != null) {

			line = br.readLine();
			logger.info("tail line {}:", line);
			if(endline >10 && (line.matches(GlobalValue.SamplerTestEndRegex))){
				logger.info("{}","Sampler Test End");
				break;
			}  
			endline++;
		}
		}catch (Exception e){
			e.printStackTrace();
			throw new CommException(CommonErrCode.DBINSTALL_ERR_0001, e.getMessage());
		}
		
	}
	

	
}
	
