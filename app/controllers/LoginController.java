package controllers;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.JsonContent;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.Secure;
import org.pac4j.play.store.PlaySessionStore;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jesu on 27/06/17.
 */
public class LoginController extends Controller {

	@Inject
	private Config config;

	@Inject
	private PlaySessionStore playSessionStore;

	public Result login() {
		final FormClient formClient = (FormClient) config.getClients().findClient("FormClient");
		return ok(views.html.admin.login.render(formClient.getCallbackUrl()));
	}

	private List<CommonProfile> getProfiles() {
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final ProfileManager<CommonProfile> profileManager = new ProfileManager(context);
		return profileManager.getAll(true);
	}

	@Secure(clients = "AnonymousClient", authorizers = "csrfToken")
	public Result index() {
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final String sessionId = context.getSessionIdentifier();
		final String token = (String) context.getRequestAttribute(Pac4jConstants.CSRF_TOKEN);
		// profiles (maybe be empty if not authenticated)
		return ok(views.html.admin.index.render());
	}

	private Result protectedIndexView() {
		// profiles
		return ok(views.html.protectedIndex.render(getProfiles()));
	}

	@Secure
	public Result protectedIndex() {
		return protectedIndexView();
	}

	//@Secure(clients = "FormClient")
	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result formIndex() {
		return protectedIndexView();
	}

	// Setting the isAjax parameter is no longer necessary as AJAX requests are automatically detected:
	// a 401 error response will be returned instead of a redirection to the login url.
	@Secure(clients = "FormClient")
	public Result formIndexJson() {
		Content content = views.html.protectedIndex.render(getProfiles());
		JsonContent jsonContent = new JsonContent(content.body());
		return ok(jsonContent);
	}

	public Result loginForm() throws TechnicalException {
		final FormClient formClient = (FormClient) config.getClients().findClient("FormClient");
		return ok(views.html.admin.login.render(formClient.getCallbackUrl()));
	}

	public Result forceLogin() {
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final Client client = config.getClients().findClient(context.getRequestParameter(Clients.DEFAULT_CLIENT_NAME_PARAMETER));
		try {
			final HttpAction action = client.redirect(context);
			return (Result) config.getHttpActionAdapter().adapt(action.getCode(), context);
		} catch (final HttpAction e) {
			throw new TechnicalException(e);
		}
	}

}
