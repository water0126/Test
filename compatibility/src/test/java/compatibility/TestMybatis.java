package compatibility;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import compatibility.dao.CommDao;
import compatibility.dao.TestServerDao;
import compatibility.vo.TestServer;
import compatibility.vo.GernericVo;

/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 11.
 * @author        : min
 * @history       :
 */
public class TestMybatis {
	private static final Logger logger = LoggerFactory.getLogger(TestMybatis.class);
	public static void main(String[] args) throws IllegalAccessException, InstantiationException {
//		// Mybatis 세션연결
//		SqlSession sqlSession = DbSessionFactory.getSqlSessionFactory()
//				.openSession(true);	
//		
//		CompatibilityServer compsvr = new CompatibilityServer();
//		CompatibilityServerDao compsvrDao = (CompatibilityServerDao)sqlSession.getMapper(CompatibilityServerDao.class);
//		compsvr = compsvrDao.getServerInfo(1);
//		logger.debug("compsvr{}",compsvr);
//		
//		ScenarioRepository secnariorepo = new ScenarioRepository();
//		ScenarioRepositoryDao secnariorepoDao = (ScenarioRepositoryDao)sqlSession.getMapper(ScenarioRepositoryDao.class);
//		secnariorepo = secnariorepoDao.getInfo();
//		logger.debug("secnariorepo{}",secnariorepo);
//		
//		CompatibilityRullinfo compRullinfo = new CompatibilityRullinfo();		
//		CompatibilityRullinfoDao compRullinfoDao = (CompatibilityRullinfoDao)sqlSession.getMapper(CompatibilityRullinfoDao.class);
//		compRullinfo = compRullinfoDao.getInfo();
//		logger.debug("compRullinfo{}",compRullinfo);
//		
		GernericVo<TestServer>  vo = new  GernericVo<TestServer>();
		
		CommDao dao = CommDao.genericDao(TestServerDao.class);
		TestServer info = (TestServer)dao.getInfo(1);
		logger.debug("{}",info.getServer_id());
		
		
	}
	
}
