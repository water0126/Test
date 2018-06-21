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
public class Product<T, M> {
    private T kind;
    private M model;
 
    public T getKind() {
        return kind;
    }
 
    public void setKind(T kind) {
        this.kind = kind;
    }
 
    public M getModel() {
        return model;
    }
 
    public void setModel(M model) {
        this.model = model;
    }

}
