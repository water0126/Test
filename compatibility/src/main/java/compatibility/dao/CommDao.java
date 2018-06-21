package compatibility.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;

import com.network.Connector;
import com.network.Server;

import compatibility.connect.DbSessionFactory;
import compatibility.vo.GernericVo;

/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 14.
 * @author        : min
 * @history       :
 */
public interface CommDao <T,K>{

	public static CommDao genericDao(Class<?> cls) throws IllegalAccessException, InstantiationException {
		SqlSession sqlSession = DbSessionFactory.getSqlSessionFactory().openSession(true);			
		return (CommDao)sqlSession.getMapper(cls);
	}

	public abstract List<T> getInfoList(K key);
	public abstract List<T> getInfoList();
	public abstract T getInfo(K key);
	public abstract T getInfo();
	
}
