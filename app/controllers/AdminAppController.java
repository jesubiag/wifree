package controllers;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.Portal;
import models.PortalNetworkConfiguration;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import play.data.Form;
import play.mvc.Result;
import services.ConnectionsService;
import views.dto.ConnectedUser;

import java.util.List;

/**
 * Created by jesu on 27/06/17.
 */
public class AdminAppController extends WiFreeController {

	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result dashboard() {
		return ok(views.html.admin.dashboard.render(getProfiles()));
	}

	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result analytics() {
		return ok(views.html.admin.analytics.render(getProfiles()));
	}

	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result connections() {
		final Form<PortalNetworkConfiguration> form = formFactory.form(PortalNetworkConfiguration.class);
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final List<ConnectedUser> connectedUsers = ConnectionsService.connectedUsers( (Portal) playSessionStore.get(context, "portal"));
		return ok(views.html.connections.index.render(form, connectedUsers));
	}

	public Result portalSettings() {
		return notFound();
	}

	private List<CommonProfile> getProfiles() {
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final ProfileManager<CommonProfile> profileManager = new ProfileManager(context);
		return profileManager.getAll(true);
	}

}
