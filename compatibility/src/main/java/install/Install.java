package install;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.constant.GlobalValue;
import com.network.Connector;
import com.network.Server;
import com.network.Sftp;
import com.network.Ssh;
import com.utility.FileUtility;

import compatibility.dao.BinaryDao;
import compatibility.dao.CommDao;
import compatibility.dao.ScenarioRepositoryDao;
import compatibility.dao.TestServerDao;
import compatibility.vo.Binary;
import compatibility.vo.ScenarioRepository;
import compatibility.vo.TestServer;
import launcher.Generate;

/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * 
 * @date : 2018. 6. 14.
 * @author : min
 * @history :
 */
public class Install {

	public static Binary serverBinary = new Binary();
	public static Binary clientBinary = new Binary();

	public static TestServer agentSvr = new TestServer();
	public static TestServer dbSvr = new TestServer();
	public static TestServer binSvr = new TestServer();

	public static ScenarioRepository svnrepo = new ScenarioRepository();

	public static FileUtility fileutil;
	public static CommDao dao;

	private static final Logger logger = LoggerFactory.getLogger(Install.class);

	public void setDbData() throws IllegalAccessException, InstantiationException {
		// 테스트 서버 정보 셋팅
		dao = CommDao.genericDao(TestServerDao.class);
		agentSvr = (TestServer) dao.getInfo(1);
		dbSvr = (TestServer) dao.getInfo(2);
		binSvr = (TestServer) dao.getInfo(3);

		dao = CommDao.genericDao(ScenarioRepositoryDao.class);
		svnrepo = (ScenarioRepository) dao.getInfo();
		// logger.debug("{}",screpo);
	}

	public void configFilePutToServer(Server server, String srcDirPath) throws Exception {

		fileutil.compressTarGz(srcDirPath + "/" + GlobalValue.configDir,
				srcDirPath + "/" + GlobalValue.configArchiveDir + "/" + GlobalValue.configArchiveFilename);

		// config file put to server
		Connector sftp = Connector.getClient(Sftp.class, server);
		sftp.login();

		sftp.put(srcDirPath + "/" + GlobalValue.configArchiveDir + "/" + GlobalValue.configArchiveFilename);
		sftp.logout();

		// config file unzip
		Connector ssh = Connector.getClient(Ssh.class, server);
		ssh.login();
		ssh.execmd("tar xvzf " + GlobalValue.configArchiveFilename);
		ssh.logout();

	}

	public static void main(String[] args) throws Exception {
		// Template file generation
		Generate gen = new Generate();
		gen.genFile(GlobalValue.genFileDir + "/" + GlobalValue.configDir);
		fileutil.removeNewlinefromDiretoryfile(GlobalValue.genFileDir + "/" + GlobalValue.configDir);
		Install install = new Install();
		// Table Data Setting
		install.setDbData();

		dao = CommDao.genericDao(BinaryDao.class);
		List<Binary> binarys = dao.getInfoList();
		List<Binary> serverbinarys = binarys;
		// Agent install
		AgentInstall agentinstall =AgentInstall.getInstance();
		// Tibero Install
		DataBaseInstall dbinstall = DataBaseInstall.getInstance();
		try {
			agentinstall.setInfo(agentSvr);
			agentinstall.setSvn(svnrepo);

			agentinstall.init();
			agentinstall.clear();
			agentinstall.setAgent();

			install.configFilePutToServer(agentinstall.getServerInfo(), GlobalValue.genFileDir);
			// 시나리오 업데이트
			agentinstall.scenarioUpdate(Arrays.asList(svnrepo.getScenarioDir().split("\\s*,\\s*")));
			// Sampler lib 업데이트
			agentinstall.samplerLibupdate(Arrays.asList(svnrepo.getSamplerLib().split("\\s*,\\s*")));
			
			agentinstall.tbClientSet(binarys);


			try {
				dbinstall.setInfo(dbSvr);				
				dbinstall.init();
				dbinstall.tbClear();
				dbinstall.clearServer();
				install.configFilePutToServer(new Server(dbSvr.getServer_ip(),dbSvr.getServer_id(),dbSvr.getServer_passwd()), GlobalValue.genFileDir);
				for (Binary testClientbinary : binarys) {
					// 서버 바이너리 테스트
					for (Binary serverbinary : serverbinarys) {
						dbinstall.setBinary(serverbinary);
						dbinstall.tbClear();
						dbinstall.putBinaryToServer();
						dbinstall.configCopyToTbDir();
						dbinstall.dbInstall();
//						dbinstall.dbInstall_test(); // Test install 
						agentinstall.runSampler(agentSvr.getServer_home() + "/tibero" + testClientbinary.getVersion());
						logger.info("DB install TEST END:{}",serverbinary.getBinary_file_name());
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("DB Install Test error :[{}]", e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Agent Test error :[{}]", e.getMessage());
		} finally {
			//Cleansing
			logger.error("== Cleansing Resource ==");
			dbinstall.close();
			agentinstall.close();
		}

	}

}
