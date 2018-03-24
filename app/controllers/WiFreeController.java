package controllers;

import org.pac4j.core.config.Config;
import org.pac4j.play.store.PlaySessionStore;
import play.Logger;
import play.data.FormFactory;
import play.mvc.Controller;

import javax.inject.Inject;

public abstract class WiFreeController extends Controller {

	@Inject
	protected FormFactory formFactory;

	@Inject
	protected Config config;

	@Inject
	protected PlaySessionStore playSessionStore;

	protected final Logger.ALogger logger = Logger.of(this.getClass());

}
