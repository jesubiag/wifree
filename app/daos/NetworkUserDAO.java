package daos;

import io.ebean.Expr;
import models.NetworkUser;

/**
 * Created by jesu on 20/06/17.
 */
public class NetworkUserDAO extends GenericDAO<NetworkUser> {

	public NetworkUserDAO() {
		super(NetworkUser.class);
	}

	public NetworkUser findByEmail(String email) {
		return find(Expr.eq("email", email));
	}
	
}
