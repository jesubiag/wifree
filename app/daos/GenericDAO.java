package daos;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.Expression;
import play.Logger;

import java.util.List;

/**
 * Created by jesu on 12/06/17.
 */
public abstract class GenericDAO<T> {

	protected EbeanServer server;
	protected final Class<T> ENTITY_TYPE;
	protected final Logger.ALogger logger = Logger.of(this.getClass());


	protected GenericDAO(Class<T> cls) {
		ENTITY_TYPE = cls;
	}


	public void save(T object) {
		Ebean.save(object);
		logger.debug("Object saved={}", object);
	}

	public boolean delete(T object) {
		boolean deleted = Ebean.delete(object);
		if (deleted) logger.debug("Object deleted={}", object);
		return deleted;
	}

	public <TID> T get(TID id) {
		logger.debug("Returning object type {} with id {}", ENTITY_TYPE, id);
		return Ebean.find(ENTITY_TYPE, id);
	}

	public List<T> getAll() {
		logger.debug("Returning all objects type {}", ENTITY_TYPE);
		return Ebean.find(ENTITY_TYPE).findList();
	}

	public List<T> listWhere(Expression expression) {
		logger.debug("Returning all objects type {} matching {}", ENTITY_TYPE, expression);
		return Ebean.find(ENTITY_TYPE).where(expression).findList();
	}
	
	public T find(Expression expression) {
		logger.debug("Returning first object of type {} matching {}", ENTITY_TYPE, expression);
		return listWhere(expression).get(0);
	}


	public EbeanServer getServer() {
		return server;
	}

	public Class<T> getENTITY_TYPE() {
		return ENTITY_TYPE;
	}

}
