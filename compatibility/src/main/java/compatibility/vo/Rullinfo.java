package compatibility.vo; 
 /**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @date          : 2018. 6. 11.
 * @author        : min
 * @history       :
 */
public class Rullinfo {
	
	private int server_seq;
	private String branch;
	private String version;
	private String install_default_path;
	private String fixset;
	private String binary_name;
	private String backup_binarydir;
	private String test_yn;
	public int getServer_seq() {
		return server_seq;
	}
	public void setServer_seq(int server_seq) {
		this.server_seq = server_seq;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getInstall_default_path() {
		return install_default_path;
	}
	public void setInstall_default_path(String install_default_path) {
		this.install_default_path = install_default_path;
	}
	public String getFixset() {
		return fixset;
	}
	public void setFixset(String fixset) {
		this.fixset = fixset;
	}
	public String getBinary_name() {
		return binary_name;
	}
	public void setBinary_name(String binary_name) {
		this.binary_name = binary_name;
	}
	public String getBackup_binarydir() {
		return backup_binarydir;
	}
	public void setBackup_binarydir(String backup_binarydir) {
		this.backup_binarydir = backup_binarydir;
	}
	public String getTest_yn() {
		return test_yn;
	}
	public void setTest_yn(String test_yn) {
		this.test_yn = test_yn;
	}
	@Override
	public String toString() {
		return "CompatibilityRullinfo [server_seq=" + server_seq + ", branch=" + branch + ", version=" + version
				+ ", install_default_path=" + install_default_path + ", fixset=" + fixset + ", binary_name="
				+ binary_name + ", backup_binarydir=" + backup_binarydir + ", test_yn=" + test_yn + "]";
	}

	
}
