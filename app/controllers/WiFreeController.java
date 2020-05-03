package controllers;

import org.pac4j.core.config.Config;
import org.pac4j.play.store.PlaySessionStore;
import play.Logger;
import play.data.FormFactory;
import play.mvc.Controller;
import utils.DateHelper;

import javax.inject.Inject;
import java.time.Instant;

public abstract class WiFreeController extends Controller {

	@Inject
	protected FormFactory formFactory;

	@Inject
	protected Config config;

	@Inject
	protected PlaySessionStore playSessionStore;

	protected final Logger.ALogger logger = Logger.of(this.getClass());

	protected Instant now() {
		return DateHelper.now();
	}

}
