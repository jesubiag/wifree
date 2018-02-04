package controllers;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.play.store.PlaySessionStore;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * Created by jesu on 08/07/17.
 */
public class ConsoleController extends Controller {

	@Inject
	private Config config;

	@Inject
	private PlaySessionStore playSessionStore;

	public Result login() {
		final FormClient formClient = (FormClient) config.getClients().findClient("ConsoleClient");
		return ok(views.html.console.login.render(formClient.getCallbackUrl()));
	}

	@SubjectPresent(handlerKey = "ConsoleClient", forceBeforeAuthCheck = true)
	public Result index() {
		return ok(views.html.console.index.render());
	}

}
