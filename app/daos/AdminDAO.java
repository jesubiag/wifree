package daos;

import io.ebean.Ebean;
import models.Admin;

/**
 * Created by jesu on 12/06/17.
 */
public class AdminDAO extends GenericDAO<Admin> {

	public AdminDAO() {
		super(Admin.class);
	}
	
	public String getPasswordForUser(String email) {
		return Ebean.find(ENTITY_TYPE)
				.select("password")
				.where()
				.eq("email", email)
				.findOne()
				.getPassword();
	}

	public Long getPortalForUser(String email) {
		return Ebean.find(ENTITY_TYPE)
				.select("portal_id")
				.where()
				.eq("email", email)
				.findOne()
				.getPortal()
				.getId();
	}
	
}
