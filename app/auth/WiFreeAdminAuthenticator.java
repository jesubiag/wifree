package auth;

import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.CommonHelper;

public class WiFreeAdminAuthenticator implements Authenticator<UsernamePasswordCredentials> {

	@Override
	public void validate(UsernamePasswordCredentials credentials, WebContext context) throws HttpAction, CredentialsException {
		if (credentials == null) {
			throwsException("No credential");
		}
		String username = credentials.getUsername();
		String password = credentials.getPassword();
		if (CommonHelper.isBlank(username)) {
			throwsException("Username cannot be blank");
		}
		if (CommonHelper.isBlank(password)) {
			throwsException("Password cannot be blank");
		}

		// TODO: define appropiate validation method
		String dbPassword = getPasswordFor(username);
		if (CommonHelper.areNotEquals(password, dbPassword)) {
			throwsException("Password for: '" + username + "' does not match input password");
		}

		// TODO: define appropiate profile
		final CommonProfile profile = new CommonProfile();
		profile.setId(username);
		profile.addAttribute(Pac4jConstants.USERNAME, username);
		credentials.setUserProfile(profile);
	}

	protected void throwsException(final String message) throws CredentialsException {
		throw new CredentialsException(message);
	}

	private String getPasswordFor(String username) {
		if ( CommonHelper.areEquals(username, "admin") )
			return "pass";
		else
			return "___error";
	}
}
