package models;

import io.ebean.annotation.Aggregation;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Instant;

/**
 * Created by jesu on 10/06/17.
 */
@Entity
public class NetworkUserConnectionLog extends BaseModel {

	@ManyToOne(optional = false)
	private Portal portal;

	@ManyToOne(optional = false)
	private NetworkUser networkUser;

	private Instant connectionStartDate;

	private Instant connectionEndDate;

	private String userDeviceMACAddress;

	public NetworkUserConnectionLog() {
	}

	public NetworkUserConnectionLog(Portal portal, NetworkUser networkUser, Instant connectionStartDate, Instant connectionEndDate, String userDeviceMACAddress) {
		this.portal = portal;
		this.networkUser = networkUser;
		this.connectionStartDate = connectionStartDate;
		this.connectionEndDate = connectionEndDate;
		this.userDeviceMACAddress = userDeviceMACAddress;
	}


	@Override
	public String toLogString() {
		return toLogString("id: " + id, "portal: " + portal, "networkUser: " + networkUser, "userDeviceMACAddress: " + userDeviceMACAddress);
	}


	public Portal getPortal() {
		return portal;
	}

	public void setPortal(Portal portal) {
		this.portal = portal;
	}

	public NetworkUser getNetworkUser() {
		return networkUser;
	}

	public void setNetworkUser(NetworkUser networkUser) {
		this.networkUser = networkUser;
	}

	public Instant getConnectionStartDate() {
		return connectionStartDate;
	}

	public void setConnectionStartDate(Instant connectionStartDate) {
		this.connectionStartDate = connectionStartDate;
	}

	public Instant getConnectionEndDate() {
		return connectionEndDate;
	}

	public void setConnectionEndDate(Instant connectionEndDate) {
		this.connectionEndDate = connectionEndDate;
	}

	public String getUserDeviceMACAddress() {
		return userDeviceMACAddress;
	}

	public void setUserDeviceMACAddress(String userDeviceMACAddress) {
		this.userDeviceMACAddress = userDeviceMACAddress;
	}

}
