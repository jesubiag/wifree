package controllers;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.Portal;
import models.PortalNetworkConfiguration;
import operations.requests.GetAnalyticsHomeDatasetsRequest;
import operations.responses.GetAnalyticsHomeDatasetsResponse;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import play.api.libs.json.JsValue;
import play.api.libs.json.Json;
import play.data.Form;
import play.mvc.Result;
import services.AnalyticsService;
import services.ConnectionsService;
import views.dto.ConnectedUser;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Created by jesu on 27/06/17.
 */
public class AdminAppController extends WiFreeController {

	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result dashboard() {
		return ok(views.html.admin.dashboard.render(getProfiles()));
	}

	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result analytics() {
		/** Call something to get JSON
		 * 1 - Search configured graphics for this portal
		 * 2 - Return graphics:
		 * 		a) Search graphics's data
		 * 		b) Build graphics
		 */
		final Long portalId = 1L; // TODO
		final GetAnalyticsHomeDatasetsRequest request = new GetAnalyticsHomeDatasetsRequest(portalId);
		final CompletionStage<GetAnalyticsHomeDatasetsResponse> futureResponse = AnalyticsService.getAnalyticsHomeDatasets(request);
		futureResponse.thenApplyAsync(response -> {
			response.datasetFilters();
			return null;
		});
		
		final String json = "[{\"name\": \"Direct Access\",\"value\": 335},{\"name\": \"E-mail Marketing\",\"value\": 310},{\"name\": \"Union Ad\",\"value\": 234},{\"name\": \"Video Ads\",\"value\": 135},{\"name\": \"Search Engine\",\"value\": 1548}]";
		final JsValue jsValue = Json.parse(json);

		return ok(views.html.admin.analytics.render(getProfiles(), jsValue));
	}

	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result connections() {
		final Form<PortalNetworkConfiguration> form = formFactory.form(PortalNetworkConfiguration.class);
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final List<ConnectedUser> connectedUsers = ConnectionsService.connectedUsers( (Portal) playSessionStore.get(context, "portal"));
		return ok(views.html.connections.index.render(form, connectedUsers));
	}

	public Result portalSettings() {
		return notFound();
	}

	private List<CommonProfile> getProfiles() {
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final ProfileManager<CommonProfile> profileManager = new ProfileManager(context);
		return profileManager.getAll(true);
	}

}
