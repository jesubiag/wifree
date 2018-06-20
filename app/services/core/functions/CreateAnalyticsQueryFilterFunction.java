package services.core.functions;

import daos.AnalyticsQueryFilterDAO;
import models.AnalyticsQueryFilter;
import operations.requests.CreateAnalyticsQueryFilterRequest;
import operations.responses.CreateAnalyticsQueryFilterResponse;
import services.core.ServiceType;
import services.core.WiFreeFunction;

import java.util.function.Function;

public class CreateAnalyticsQueryFilterFunction extends WiFreeFunction<CreateAnalyticsQueryFilterRequest, CreateAnalyticsQueryFilterResponse> {
	
	{
		rqClass = CreateAnalyticsQueryFilterRequest.class;
		rsClass = CreateAnalyticsQueryFilterResponse.class;
		serviceType = ServiceType.TESTING_SERVICE;			// TODO TBD
	}
	
	@Override
	public Function<CreateAnalyticsQueryFilterRequest, CreateAnalyticsQueryFilterResponse> function() {
		function = request -> {
			final AnalyticsQueryFilter analyticsQueryFilter = request.queryFilter();
			final AnalyticsQueryFilterDAO analyticsQueryFilterDAO = new AnalyticsQueryFilterDAO();
			analyticsQueryFilterDAO.save(analyticsQueryFilter);
			return new CreateAnalyticsQueryFilterResponse(analyticsQueryFilter, true, null); // TODO check
		};
		return function;
	}
	
}
