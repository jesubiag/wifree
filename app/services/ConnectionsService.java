package services;

import daos.PortalNetworkConfigurationDAO;
import models.Portal;
import models.PortalNetworkConfiguration;
import views.admin.ConnectedUser;

import java.util.ArrayList;
import java.util.Date;

public class ConnectionsService implements WiFreeService {

	public static void saveConnectionTimeout(PortalNetworkConfiguration portalNetworkConfiguration) {
		if (isOk(portalNetworkConfiguration)) {
			final PortalNetworkConfigurationDAO portalNetworkConfigurationDAO = new PortalNetworkConfigurationDAO();
			portalNetworkConfigurationDAO.save(portalNetworkConfiguration);
			// return ok;
		} else {
			// return error
		}
	}

	public static ArrayList<ConnectedUser> connectedUsers(Portal portal) {
		final ArrayList<ConnectedUser> connectedUsers = new ArrayList<>();
		connectedUsers.add(new ConnectedUser(1, "Juan perez", new Date(), "iPhone X"));
		connectedUsers.add(new ConnectedUser(2, "Roque Saenz Peña", new Date(), "Samsung S8+"));
		return connectedUsers;
	}

	public static <T> boolean isOk(T anything) {
		return false;
	}
}
