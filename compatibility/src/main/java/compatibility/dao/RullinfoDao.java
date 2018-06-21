package compatibility.dao;

import compatibility.vo.Rullinfo;

/**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 11.
 * @author        : min
 * @history       :
 */
public interface RullinfoDao <T, K> extends CommDao <T,K>{
	public abstract T getInfo(K seq);
}
