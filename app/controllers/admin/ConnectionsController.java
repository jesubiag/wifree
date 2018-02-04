package controllers.admin;

import common.constants.ConnectionsConstants;
import controllers.WiFreeController;
import models.PortalNetworkConfiguration;
import play.data.Form;
import play.mvc.Result;
import services.ConnectionsService;

public class ConnectionsController extends WiFreeController implements ConnectionsConstants {

	public Result setConnectionTimeout() {
		final Form<PortalNetworkConfiguration> form = formFactory.form(PortalNetworkConfiguration.class);
		final PortalNetworkConfiguration portalNetworkConfiguration = form.bindFromRequest().get();
		ConnectionsService.saveConnectionTimeout(portalNetworkConfiguration);
		return redirect(controllers.routes.ApplicationController.testTheme()); // with something
	}


}
