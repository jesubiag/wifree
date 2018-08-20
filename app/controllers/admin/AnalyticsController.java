package controllers.admin;

import controllers.WiFreeController;
import models.AnalyticsQueryFilter;
import operations.requests.CreateAnalyticsQueryFilterRequest;
import operations.responses.CreateAnalyticsQueryFilterResponse;
import play.data.Form;
import play.mvc.Result;
import services.AnalyticsService;

public class AnalyticsController extends WiFreeController {
	
	public Result createAnalyticsQuery() {
		final Form<AnalyticsQueryFilter> form = formFactory.form(AnalyticsQueryFilter.class);
		AnalyticsQueryFilter queryFilter = form.bindFromRequest().get();
		
		final CreateAnalyticsQueryFilterRequest request = new CreateAnalyticsQueryFilterRequest(queryFilter);
		final CreateAnalyticsQueryFilterResponse response = AnalyticsService.process(request);
		
		if (response.isOk()) {
			return ok();
		} else {
			return badRequest();
		}
	}
	
	public Result runAnalyticsQuery() {
		final Form<AnalyticsQueryFilter> form = formFactory.form(AnalyticsQueryFilter.class);
		final AnalyticsQueryFilter queryFilter = form.bindFromRequest().get();
		
		
		
		throw new IllegalArgumentException("To be implemented");
	}
	
}
