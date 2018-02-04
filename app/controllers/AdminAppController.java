package controllers;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import com.google.inject.Inject;
import org.pac4j.core.config.Config;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.store.PlaySessionStore;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by jesu on 27/06/17.
 */
public class AdminAppController extends Controller {

	@Inject
	private Config config;

	@Inject
	private PlaySessionStore playSessionStore;

	private List<CommonProfile> getProfiles() {
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final ProfileManager<CommonProfile> profileManager = new ProfileManager(context);
		return profileManager.getAll(true);
	}

	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result dashboard() {
		return ok(views.html.admin.dashboard.render(getProfiles()));
	}

	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result analytics() {
		return ok(views.html.admin.analytics.render(getProfiles()));
	}

	public Result connections() {
		return notFound();
	}

	public Result portalSettings() {
		return notFound();
	}

}
