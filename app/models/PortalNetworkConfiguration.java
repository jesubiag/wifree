package models;

import models.types.LoginMethodType;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by jesu on 10/06/17.
 */
@Entity
public class PortalNetworkConfiguration extends BaseModel {

	@OneToOne(mappedBy = "networkConfiguration")
	private Portal portal;

	private Integer connectionTimeout;

	private LoginMethodType loginMethod;

	private boolean enableBans;

	public PortalNetworkConfiguration() {}

	public PortalNetworkConfiguration(Portal portal) {
		setPortal(portal);
	}

	public PortalNetworkConfiguration(Long portalId) {
		setId(portalId);
	}

	public PortalNetworkConfiguration(Integer connectionTimeout, LoginMethodType loginMethod, boolean enableBans) {
		this.connectionTimeout = connectionTimeout;
		this.loginMethod = loginMethod;
		this.enableBans = enableBans;
	}

	public boolean hasPortal() {
		return portal == null;
	}

	@Override
	public Long getId() {
		return portal.id;
	}

	@Override
	public String toLogString() {
		return toLogString("id: " + portal.getId(), "connectionTimeout: " + connectionTimeout, "loginMethod: " + loginMethod, "enableBans: " + enableBans);
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

	public void setPortal(Portal portal) {
		this.portal = portal;
		setId(portal.id);
	}

	public Portal getPortal() {
		return portal;
	}
}
