package test; 
 /**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 15.
 * @author        : min
 * @history       :
 */
public interface Storage<T> {
    public void add(T item, int index);
    
    public T get(int index);


}
