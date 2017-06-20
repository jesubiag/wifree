package models;

import models.types.PortalApplicationType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by jesu on 09/06/17.
 */
@Entity
public class PortalApp extends BaseModel {

	@ManyToOne(optional = false)
	private Portal portal;

	private PortalApplicationType type;

	private String name;

	private boolean enabled;

	public PortalApp() {
	}

	public PortalApp(Portal portal, PortalApplicationType type, String name, boolean enabled) {
		this.portal = portal;
		this.type = type;
		this.name = name;
		this.enabled = enabled;
	}


	@Override
	public String toLogString() {
		return toLogString("id: " + id, "portal: " + portal, "type: " + type, "name: " + name, "enabled: " + enabled);
	}


	public Portal getPortal() {
		return portal;
	}

	public void setPortal(Portal portal) {
		this.portal = portal;
	}

	public PortalApplicationType getType() {
		return type;
	}

	public void setType(PortalApplicationType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
