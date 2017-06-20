package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.WhenCreated;
import com.avaje.ebean.annotation.WhenModified;
import utils.StringHelper;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jesu on 08/06/17.
 */
@MappedSuperclass
public abstract class BaseModel extends Model implements Serializable, WiFreeEntity {

	@Id
	protected Long id;

	@WhenCreated
	protected Timestamp whenCreated;

	@WhenModified
	protected Timestamp whenModified;


	protected String toLogString(String... attributes) {
		String logString = "";
		try {
			logString = StringHelper.toParagraph(attributes);
		} catch (Exception e) {
			// TODO
		} finally {
			return logString;
		}
	}

	public abstract String toLogString();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getWhenCreated() {
		return whenCreated;
	}

	public void setWhenCreated(Timestamp whenCreated) {
		this.whenCreated = whenCreated;
	}

	public Timestamp getWhenModified() {
		return whenModified;
	}

	public void setWhenModified(Timestamp whenModified) {
		this.whenModified = whenModified;
	}
}
