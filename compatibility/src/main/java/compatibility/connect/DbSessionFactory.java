package compatibility.connect;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * <pre>
 * 1. 개요        : database 접속 
 * 2. 처리내용    : database 접속  팩토리
 * </pre>
 * @date          : 2017. 8. 18.
 * @author        : min
 * @history       :
 */
public class DbSessionFactory {
    private static SqlSessionFactory sqlSessionFactory;
    
    static {
        try {
        	String resource = "config.xml";        	
            Reader reader = Resources.getResourceAsReader(resource);
 
            if (sqlSessionFactory == null) {
            	sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
    
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
