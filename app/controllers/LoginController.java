package controllers;

import models.Admin;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login.adminLogin;

/**
 * Created by jesu on 27/06/17.
 */
public class LoginController extends Controller {

	public Result adminLoginLanding() {
		FormFactory formFactory = new FormFactory(null, null, null);
		Form<Admin> form = formFactory.form(Admin.class);
		return ok(adminLogin.render(form));
	}

	public Result loginAdmin() {
		return ok();
	}

}
