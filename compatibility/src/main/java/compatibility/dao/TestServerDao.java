package compatibility.dao;

import compatibility.vo.TestServer;

/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 11.
 * @author        : min
 * @param <T>
 * @param <K>
 * @history       :
 */
public interface TestServerDao<T, K> extends CommDao <T,K>{
	public abstract T getInfo(K key);
}
