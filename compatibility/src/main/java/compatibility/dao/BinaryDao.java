package compatibility.dao;

import java.util.List;

import compatibility.vo.Binary;

/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 12.
 * @author        : min
 * @history       :
 */
public interface BinaryDao<T, K> extends CommDao <T,K>{
	public abstract List<T> getInfoList();
	public abstract List<Binary> getTestBinaryInfo(K key);
}
