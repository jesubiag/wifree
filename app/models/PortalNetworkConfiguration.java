package models;

import models.types.LoginMethodType;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by jesu on 10/06/17.
 */
@Entity
public class PortalNetworkConfiguration extends BaseModel {

	private Integer connectionTimeout;

	private LoginMethodType loginMethod;

	private boolean enableBans;

	@OneToOne(mappedBy = "networkConfiguration")
	private Portal portal;


	public PortalNetworkConfiguration() {
	}
	
	public PortalNetworkConfiguration(Long id) {
		this.id = id;
	}

	public PortalNetworkConfiguration(Integer connectionTimeout, LoginMethodType loginMethod, boolean enableBans) {
		this.connectionTimeout = connectionTimeout;
		this.loginMethod = loginMethod;
		this.enableBans = enableBans;
	}


	@Override
	public String toLogString() {
		return toLogString("id: " + id, "connectionTimeout: " + connectionTimeout, "loginMethod: " + loginMethod, "enableBans: " + enableBans);
	}


	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public LoginMethodType getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(LoginMethodType loginMethod) {
		this.loginMethod = loginMethod;
	}

	public boolean isEnableBans() {
		return enableBans;
	}

	public void setEnableBans(boolean enableBans) {
		this.enableBans = enableBans;
	}
}
