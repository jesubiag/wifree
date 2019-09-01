package daos;

import io.ebean.Ebean;
import io.ebean.Expression;
import io.ebean.ExpressionList;
import models.NetworkUserConnectionLog;
import operations.responses.BuiltExpressions;
import operations.responses.DatasetFilter;
import scala.collection.JavaConverters;

import java.util.List;

public class NetworkUserConnectionLogDAO extends GenericDAO<NetworkUserConnectionLog> {
	
	public NetworkUserConnectionLogDAO() {
		super(NetworkUserConnectionLog.class);
	}
	
	public List<NetworkUserConnectionLog> findForFilter(DatasetFilter filter) {
		final ExpressionList<NetworkUserConnectionLog> whereExpressions = Ebean.createQuery(NetworkUserConnectionLog.class).where();
		final BuiltExpressions expressions = filter.expressions();
		expressions.timePeriodExpression().foreach(e -> whereExpressions.add(e.apply("connectionStartDate")));
		expressions.visitsAmountQuery().expression().foreach(whereExpressions::add);
		for (Expression e : JavaConverters.seqAsJavaList(expressions.parameterlessExpressions())) {
			whereExpressions.add(e);
		}
		
		final List<NetworkUserConnectionLog> networkUserConnectionLogs = Ebean.createQuery(NetworkUserConnectionLog.class)
				.select("*")
				.where()
				.addAll(whereExpressions)
				.findList();
		
		return networkUserConnectionLogs;
	}
	
}
