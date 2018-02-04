package controllers;

import play.Logger;
import play.data.FormFactory;
import play.mvc.Controller;

import javax.inject.Inject;

public abstract class WiFreeController extends Controller {

	@Inject
	protected FormFactory formFactory;

	protected final Logger.ALogger logger = Logger.of(this.getClass());

}
