package models.types;

import io.ebean.Expr;
import io.ebean.Expression;
import io.ebean.annotation.EnumValue;
import utils.DateHelper;

import java.time.Instant;
import java.util.function.Function;
import java.util.function.UnaryOperator;


public enum RelativeTimePeriod {
	
	@EnumValue("LAST_DAY") LAST_DAY(buildExpression(DateHelper::oneDayAgo)),
	@EnumValue("LAST_WEEK") LAST_WEEK(buildExpression(DateHelper::oneWeekAgo)),
	@EnumValue("LAST_MONTH") LAST_MONTH(buildExpression(DateHelper::oneMonthAgo)),
	@EnumValue("LAST_YEAR") LAST_YEAR(buildExpression(DateHelper::oneYearAgo)),
	@EnumValue("TODAY") TODAY(buildExpression(DateHelper::dayBeginning)),
	@EnumValue("THIS_WEEK") THIS_WEEK(buildExpression(DateHelper::weekBeginning)),
	@EnumValue("THIS_MONTH") THIS_MONTH(buildExpression(DateHelper::monthBeginning)),
	@EnumValue("THIS_YEAR") THIS_YEAR(buildExpression(DateHelper::yearBeginning)),
	@EnumValue("ERROR") ERROR((String date) -> { throw new IllegalArgumentException("There is no valid Expression value for ERROR"); } );

	private final Function<String, Expression> expr;

	RelativeTimePeriod(Function<String, Expression> expressionFunction) {
		this.expr = expressionFunction;
	}
	
	public Function<String, Expression> getExpression() {
		return expr;
	}
	
	private static Function<String, Expression> buildExpression(UnaryOperator<Instant> operator) {
		return (String propertyName) -> between(propertyName, operator);
	}
	
	private static Expression between(String propertyName, UnaryOperator<Instant> operator) {
		final Instant now = DateHelper.now();
		return Expr.between(propertyName, operator.apply(now), now);
	}

}
