package controllers;

import play.mvc.Http.RequestBuilder;

import org.junit.Test;
import play.mvc.Result;
import play.test.Helpers;
import utils.WiFreeSuite;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class LoginAdminSuite extends WiFreeSuite {

	@Test
	public void testLoginAdmin() {
		final RequestBuilder loginRequest = Helpers.fakeRequest().method(GET).uri("login");
		final Result result = route(app, loginRequest);
		assertEquals(OK, result.status());
	}

}
