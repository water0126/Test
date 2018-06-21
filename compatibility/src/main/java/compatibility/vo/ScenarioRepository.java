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
public class ScenarioRepository {
	
	private String svn_url;
	private String id;
	private String password;
	private String samplerLib;
	private String scenarioDir;
	public String getScenarioDir() {
		return scenarioDir;
	}
	public void setScenarioDir(String scenarioDir) {
		this.scenarioDir = scenarioDir;
	}
	public String getSvn_url() {
		return svn_url;
	}
	public void setSvn_url(String svn_url) {
		this.svn_url = svn_url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getSamplerLib() {
		return samplerLib;
	}
	public void setSamplerLib(String samplerLib) {
		this.samplerLib = samplerLib;
	}
	
	@Override
	public String toString() {
		return "ScenarioRepository [svn_url=" + svn_url + ", id=" + id + ", password=" + password + ", samplerLib="
				+ samplerLib + ", scenarioDir=" + scenarioDir + "]";
	}


	
}
