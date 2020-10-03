package controllers.admin;

import controllers.WiFreeController;
import models.AnalyticsQueryFilter;
import operations.requests.CreateAnalyticsQueryFilterRequest;
import operations.requests.RunAnalyticsQueryFilterRequest;
import operations.responses.CreateAnalyticsQueryFilterResponse;
import operations.responses.RunAnalyticsQueryFilterResponse;
import play.data.Form;
import play.mvc.Result;
import services.AnalyticsService;

import java.util.concurrent.CompletionStage;

public class AnalyticsController extends WiFreeController {
	
	public CompletionStage<Result> createAnalyticsQuery() {
		final Form<AnalyticsQueryFilter> form = formFactory.form(AnalyticsQueryFilter.class);
		AnalyticsQueryFilter queryFilter = form.bindFromRequest().get();
		
		final CreateAnalyticsQueryFilterRequest request = new CreateAnalyticsQueryFilterRequest(queryFilter);
		final CompletionStage<CreateAnalyticsQueryFilterResponse> futureResponse = AnalyticsService.createAnalyticsQueryFilter(request);
		
		// TODO revisar
		return futureResponse.thenApplyAsync(response -> {
			if (response.isOk()) return ok();
			else return badRequest();
		});
	}
	
	public CompletionStage<Result> runAnalyticsQuery() {
		final Form<AnalyticsQueryFilter> form = formFactory.form(AnalyticsQueryFilter.class);
		final AnalyticsQueryFilter queryFilter = form.bindFromRequest().get();
		
		final RunAnalyticsQueryFilterRequest request = new RunAnalyticsQueryFilterRequest(queryFilter);
		final CompletionStage<RunAnalyticsQueryFilterResponse> futureResponse = AnalyticsService.runAnalyticsQueryFilter(request);
		
		futureResponse.thenApplyAsync(response -> {
			if (response.isOk()) return ok();
			else return badRequest();
		});
		
		throw new IllegalArgumentException("To be implemented");
	}
	
}
