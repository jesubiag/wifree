package daos;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import play.Logger;

import java.util.List;

/**
 * Created by jesu on 12/06/17.
 */
public abstract class GenericDAO<T> {

	protected EbeanServer server;
	protected final Class<T> ENTITY_TYPE;

	protected GenericDAO(Class<T> cls) {
		ENTITY_TYPE = cls;
	}


	public void save(T object) {
		Ebean.save(object);
		Logger.debug("Object saved={}", object);
	}

	public boolean delete(T object) {
		boolean deleted = Ebean.delete(object);
		if (deleted) Logger.debug("Object deleted={}", object);
		return deleted;
	}

	public <TID> T get(TID id) {
		Logger.debug("Returning object type {} with id {}", ENTITY_TYPE, id);
		return Ebean.find(ENTITY_TYPE, id);
	}

	public List<T> getAll() {
		Logger.debug("Returning all objects type {}", ENTITY_TYPE);
		return Ebean.find(ENTITY_TYPE).findList();
	}


	public EbeanServer getServer() {
		return server;
	}

	public Class<T> getENTITY_TYPE() {
		return ENTITY_TYPE;
	}

}
