package services;

import daos.PortalDAO;
import daos.PortalNetworkConfigurationDAO;
import models.Portal;
import models.PortalNetworkConfiguration;
import models.types.LoginMethodType;
import views.dto.ConnectedUser;
import views.dto.ConnectionsPage;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class ConnectionsService {

	@Inject
	PortalNetworkConfigurationDAO portalNetworkConfigurationDAO;

	@Inject
	PortalDAO portalDAO;
	
	public void saveNetworkConfiguration(Integer connectionTimeout, LoginMethodType loginMethod, Long portalId) {
		Portal portal = portalDAO.get(portalId);
		PortalNetworkConfiguration networkConfiguration = portal.getNetworkConfiguration();
		if (networkConfiguration == null) {
			networkConfiguration = new PortalNetworkConfiguration(portal);
		}
		networkConfiguration.setConnectionTimeout(connectionTimeout);
		networkConfiguration.setLoginMethod(loginMethod);
		portal.setNetworkConfiguration(networkConfiguration);
		portalNetworkConfigurationDAO.save(networkConfiguration);
		portalDAO.save(portal);
	}

	public ConnectionsPage connectionsPage(Long portalId) {
		ArrayList<ConnectedUser> connectedUsers = connectedUsers();
		Optional<Integer> connectionTimeout = portalNetworkConfigurationDAO.getConnectionTimeout(portalId);
		return new ConnectionsPage(connectedUsers, connectionTimeout);
	}

	public PortalNetworkConfiguration networkConfiguration(Long portalId) {
		return portalNetworkConfigurationDAO.findForPortal(portalId);
	}

	public ArrayList<ConnectedUser> connectedUsers() {
		final ArrayList<ConnectedUser> connectedUsers = new ArrayList<>();
		connectedUsers.add(new ConnectedUser(1, "Juan perez", new Date(), "iPhone X"));
		connectedUsers.add(new ConnectedUser(2, "Roque Saenz Peña", new Date(), "Samsung S8+"));
		return connectedUsers;
	}

}
