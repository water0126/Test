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
public class StorageImpl<T> implements Storage<T>  {
    private T[] array;
    
    public StorageImpl(int capacity) {
        this.array = (T[]) (new Object[capacity]);
    }
 
    @Override
    public void add(T item, int index) {
        array[index] = item;
    }
 
    @Override
    public T get(int index) {
        return array[index];
    }


}
