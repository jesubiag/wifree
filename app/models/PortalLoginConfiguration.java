package models;

import models.types.LoginMethodType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by jesu on 09/06/17.
 */
@Table(
		uniqueConstraints = @UniqueConstraint(columnNames = {"portal_id", "login_method"})
)
@Entity
public class PortalLoginConfiguration extends BaseModel {

	@ManyToOne(optional = false)
	private Portal portal;

	private LoginMethodType loginMethod;

	private boolean isSocialLogin;

	private boolean enabled;

	private String redirectURL;


	public PortalLoginConfiguration() {
	}

	public PortalLoginConfiguration(Portal portal, LoginMethodType loginMethod, boolean isSocialLogin, boolean enabled, String redirectURL) {
		this.portal = portal;
		this.loginMethod = loginMethod;
		this.isSocialLogin = isSocialLogin;
		this.enabled = enabled;
		this.redirectURL = redirectURL;
	}


	@Override
	public String toLogString() {
		return toLogString("id: " + id, "portal: " , "loginMethod: " + loginMethod, "isSocialLogin: " + isSocialLogin,
				"enabled: " + enabled, "redirectURL: " + redirectURL);
	}


	public Portal getPortal() {
		return portal;
	}

	public void setPortal(Portal portal) {
		this.portal = portal;
	}

	public LoginMethodType getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(LoginMethodType loginMethod) {
		this.loginMethod = loginMethod;
	}

	public boolean isSocialLogin() {
		return isSocialLogin;
	}

	public void setSocialLogin(boolean socialLogin) {
		isSocialLogin = socialLogin;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
}
