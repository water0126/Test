package com.constant; 

import java.util.HashMap;
import java.util.Map;
 /**
 * <pre>
 * 1. 개요        :
 * 2. 처리내용    :
 * </pre>
 * @package name  : dbqa.sampler.run 
 * @Class name    : EnumAgentStatus.java
 * @date          : 2017. 2. 15.
 * @author        : min
 * @history       : Agent Running 상태를 체크한다.
 *	-----------------------------------------------------------------------------
 *	변경일			최초 작성자		수정자				    변경내용
 *	-----------     ----------     ----------          --------------
 *	2017. 2. 15.		 민정기				                최초 작성

 *
 */
public enum  EnumCHKStatus {
	
    SERVER_INSTALL_START(1, "Sampler initializing", "Database install start.", "(Database install start.)"),
    SERVER_INSTALL_SUCCESS(2, "Sampler initializing", "Database install Success.", "(Database install Success.)"),
    SCENARIO_SVNGET_START(3, "Sampler initializing", "Scenario SVN Get start.","(Scenario SVN Get start.)"),
    SCENARIO_SVNGET_SUCESS(4, "Sampler initializing", "Scenario SVN Get Success.","(Scenario SVN Get Success.)"),
    CLIENT_INSTALL_START(5, "Sampler initializing", "Client install start.","(Client install start.)"),                                                     
    CLIENT_INSTALL_SUCCESS(6, "Sampler initializing", "Client install Success.","(Client install Success.)"),
    AGENT_INSTALL_ALL_SUCESS(7, "Success Sampler initialization", "Agent install Success.","(Agent install Success.)"),
    SAMPLER_SCENARIO_END(8, "Sampler test End", "Sampler test End.","(Sampler test End)"),
    AGENT_INSTALL_FAIL(9, "Agent Install Fail", "Agent Install Fail.","(Agent Install Fail.)"),
	AGENT_SAMPLER_FAIL(10, "Agent Sampler run fail", "Agent Sampler run fail.","(Agent Sampler run fail.)"),
	AGENT_UNKNOWN_ERR(11, "Sampler Unknown Error 발생", "Sampler Unknown Error 발생.","(Sampler Unknown Error.)"),
	AGENT_INSTALL_FAIL_CHECK_STRING(12, "Agent install Fail Strings", "Agent install Fail Strings"
			,"(startup failed|spawn id exp[0-9]|TBS-70004: Not connected to the server)"),
	AGENT_SCENARIO_FAIL_CHECK_STRING(13, "Agent install Fail Strings", "Agent install Fail Strings"
			,"(startup failed|spawn id exp6 not open|No such file or directory|sampler.SaveDbqaLog.getScPsmt|Connection_Fail_DbqaDB|sampler.SaveDbObject.savePropertyFromDB)");
    
	
	public int code;
    public String status;
    public String description;
    public String regex;
 
    /**
     * A mapping between the integer code and its corresponding Status to facilitate lookup by code.
     */
    private static Map<Integer, EnumCHKStatus> codeToStatusMapping;
 
    private EnumCHKStatus(int code, String status, String description ,String regex) {
        this.code = code;
        this.status = status;
        this.description = description;
        this.regex = regex;
    }
 
    public static EnumCHKStatus setStatus(int i) {
        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(i);
    }
 
    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, EnumCHKStatus>();
        for (EnumCHKStatus s : values()) {
            codeToStatusMapping.put(s.code, s);
        }
    }
 
    public int getCode() {
        return code;
    }
 
    public String getStatus() {
        return status;
    }
 
    public String getDescription() {
        return description;
    }
    
    public String getRegex() {
        return regex;
    }
 
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Status");
        sb.append("{code=").append(code);
        sb.append(", status='").append(status).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", regex='").append(regex).append('\'');
        sb.append('}');
        return sb.toString();
    }
}



