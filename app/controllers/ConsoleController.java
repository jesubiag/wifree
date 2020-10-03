package controllers;

import daos.AdminDAO;
import daos.PortalDAO;
import models.Admin;
import models.Portal;
import org.pac4j.http.client.indirect.FormClient;
import play.data.Form;
import play.mvc.Result;
import utils.MockupInitDataHelper;

import java.util.List;

/**
 * Created by jesu on 08/07/17.
 */
public class ConsoleController extends WiFreeController {

	public Result login() {
		final FormClient formClient = (FormClient) config.getClients().findClient("ConsoleClient");
		return ok(views.html.console.login.render(formClient.getCallbackUrl()));
	}

//	@SubjectPresent(handlerKey = "ConsoleClient", forceBeforeAuthCheck = true)
	public Result index() {
		final PortalDAO portalDAO = new PortalDAO();
		final List<Portal> portals = portalDAO.getAll();
		final List<Admin> admins = new AdminDAO().getAll();
		return ok(views.html.console.index.render(formFactory.form(Admin.class), formFactory.form(Portal.class), portals, admins));
	}
	
	public Result createAdmin() {
		final Form<Admin> adminForm = formFactory.form(Admin.class).bindFromRequest();
		final Admin admin = adminForm.get();
		admin.save();

		return ok(views.html.console.created.render("*** User *** " + admin.toLogString() + " *** saved ***"));
	}
	
	public Result createPortal() {
		final Form<Portal> portalForm = formFactory.form(Portal.class).bindFromRequest();
		final Portal portal = portalForm.get();
		portal.save();
		return ok(views.html.console.created.render("*** Portal *** " + portal.toLogString() + " *** saved ***"));
	}

	public Result mockup(Long portalId) {
		MockupInitDataHelper.run(portalId);
		return ok("Mockups created");
	}

}
