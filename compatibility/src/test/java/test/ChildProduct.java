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
public class ChildProduct<T, M, C> {
    private C company;
    
    public C getCompany() {
        return company;
    }
 
    public void setCompany(C company) {
        this.company = company;
    }

}
