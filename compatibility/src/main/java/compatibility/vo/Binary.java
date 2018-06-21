package compatibility.vo; 
 /**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 12.
 * @author        : min
 * @history       :
 */
public class Binary {
	
	
	private String version;	
	private String fixset;
	private String binary_file_name;
	private String path;
	private String licenseName;
	private String testYn;
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFixset() {
		return fixset;
	}
	public void setFixset(String fixset) {
		this.fixset = fixset;
	}
	public String getBinary_file_name() {
		return binary_file_name;
	}
	public void setBinary_file_name(String binary_file_name) {
		this.binary_file_name = binary_file_name;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	

	public String getLicenseName() {
		return licenseName;
	}
	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}
	public String getTestYn() {
		return testYn;
	}
	public void setTestYn(String testYn) {
		this.testYn = testYn;
	}
	@Override
	public String toString() {
		return "Binary [version=" + version + ", fixset=" + fixset + ", binary_file_name=" + binary_file_name
				+ ", path=" + path + ", licenseName=" + licenseName + ", testYn=" + testYn + "]";
	}

	

	
	
}
