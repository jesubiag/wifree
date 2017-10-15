package modules;

import auth.AuthConstants;
import auth.WiFreeAdminAuthenticator;
import auth.WiFreeConsoleAuthenticator;
import be.objectify.deadbolt.java.cache.HandlerCache;
import com.google.inject.AbstractModule;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.direct.AnonymousClient;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.play.CallbackController;
import org.pac4j.play.LogoutController;
import org.pac4j.play.deadbolt2.Pac4jHandlerCache;
import org.pac4j.play.deadbolt2.Pac4jRoleHandler;
import org.pac4j.play.store.PlayCacheSessionStore;
import org.pac4j.play.store.PlaySessionStore;
import play.Configuration;
import play.Environment;
import play.cache.SyncCacheApi;

/**
 * Created by jesu on 11/07/17.
 */
public class SecurityModule extends AbstractModule {

	public final static String JWT_SALT = "11345678901234567890123456789012";

	private final Configuration configuration;

	private static class MyPac4jRoleHandler implements Pac4jRoleHandler { }

	public SecurityModule(final Environment environment, final Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	protected void configure() {

		bind(HandlerCache.class).to(Pac4jHandlerCache.class);

		bind(Pac4jRoleHandler.class).to(MyPac4jRoleHandler.class);
		final PlayCacheSessionStore playCacheSessionStore = new PlayCacheSessionStore(getProvider(SyncCacheApi.class));
		//bind(PlaySessionStore.class).toInstance(playCacheSessionStore);
		bind(PlaySessionStore.class).to(PlayCacheSessionStore.class);

		final String baseUrl = configuration.getString("baseUrl");

		// Admin - HTTP - Form
		final FormClient adminLoginClient = new FormClient(baseUrl + AuthConstants.ADMIN_LOGIN_URL, new WiFreeAdminAuthenticator());
		//adminLoginClient.setName("AdminClient");

		// Console - HTTP - Form
		final FormClient consoleLoginClient = new FormClient(baseUrl + AuthConstants.CONSOLE_LOGIN_URL, new WiFreeConsoleAuthenticator());
		consoleLoginClient.setName("ConsoleClient");

		final Clients clients = new Clients(baseUrl + AuthConstants.CALLBACK_URL, adminLoginClient, consoleLoginClient, new AnonymousClient());

		final Config config = new Config(clients);
		config.addAuthorizer("admin", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN"));
		config.addAuthorizer("custom", new CustomAuthorizer());
		config.setHttpActionAdapter(new DemoHttpActionAdapter());
		bind(Config.class).toInstance(config);

		// callback
		final CallbackController callbackController = new CallbackController();
		callbackController.setDefaultUrl("/");
		callbackController.setMultiProfile(true);
		bind(CallbackController.class).toInstance(callbackController);

		// logout
		final LogoutController logoutController = new LogoutController();
		logoutController.setDefaultUrl(AuthConstants.DEFAULT_LOGOUT_URL);
		//logoutController.setDestroySession(true);
		bind(LogoutController.class).toInstance(logoutController);
	}
}