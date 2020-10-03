package controllers;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.Portal;
import models.PortalNetworkConfiguration;
import operations.requests.GetAnalyticsDataRequest;
import operations.responses.GetAnalyticsDataResponse;
import operations.responses.VisitsByDayByTimeRange;
import operations.responses.VisitsByPeriod;
import operations.responses.VisitsByPeriodByGender;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import play.api.libs.json.JsValue;
import play.api.libs.json.Json;
import play.data.Form;
import play.mvc.Result;
import scala.Tuple2;
import services.AnalyticsService;
import services.ConnectionsService;
import services.core.MinutesRange;
import utils.DateHelper;
import utils.JsonHelper;
import views.dto.ConnectedUser;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by jesu on 27/06/17.
 */
public class AdminAppController extends WiFreeController {

	@Inject
	AnalyticsService analyticsService;

	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result dashboard() throws NoProfileFoundException {
		final String genderGraphData = "[{\"name\": \"Direct Access\",\"value\": 335},{\"name\": \"E-mail Marketing\",\"value\": 310},{\"name\": \"Union Ad\",\"value\": 234},{\"name\": \"Video Ads\",\"value\": 135},{\"name\": \"Search Engine\",\"value\": 1548}]";
		final JsValue jsGenderGraphData = Json.parse(genderGraphData);
		final String ageGraphData = "[{\"name\": \"Direct Access\",\"value\": 335},{\"name\": \"E-mail Marketing\",\"value\": 310},{\"name\": \"Union Ad\",\"value\": 234},{\"name\": \"Video Ads\",\"value\": 135},{\"name\": \"Search Engine\",\"value\": 1548}]";
		final JsValue jsAgeGraphData = Json.parse(ageGraphData);
		final String connectionsGraphDataThisWeek = "[{\"name\": \"Direct Access\",\"value\": 335},{\"name\": \"E-mail Marketing\",\"value\": 310},{\"name\": \"Union Ad\",\"value\": 234},{\"name\": \"Video Ads\",\"value\": 135},{\"name\": \"Search Engine\",\"value\": 1548}]";
		final JsValue jsConnectionsGraphDataThisWeek = Json.parse(connectionsGraphDataThisWeek);
		final String connectionsGraphDataLastWeek = "[{\"name\": \"Direct Access\",\"value\": 335},{\"name\": \"E-mail Marketing\",\"value\": 310},{\"name\": \"Union Ad\",\"value\": 234},{\"name\": \"Video Ads\",\"value\": 135},{\"name\": \"Search Engine\",\"value\": 1548}]";
		final JsValue jsConnectionsGraphDataLastWeek = Json.parse(connectionsGraphDataLastWeek);
		return ok(views.html.admin.dashboard.render(
				getCurrentProfile(),
				jsGenderGraphData,
				jsAgeGraphData,
				jsConnectionsGraphDataThisWeek,
				jsConnectionsGraphDataLastWeek)
		);
	}

	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result analytics() throws NoProfileFoundException {
		/** Call something to get JSON
		 * 1 - Search configured graphics for this portal
		 * 2 - Return graphics:
		 * 		a) Search graphics's data
		 * 		b) Build graphics
		 */
//		final Long portalId = 1L; // TODO
//		final GetAnalyticsHomeDatasetsRequest request = new GetAnalyticsHomeDatasetsRequest(portalId);
//		final CompletionStage<GetAnalyticsHomeDatasetsResponse> futureResponse = AnalyticsService.getAnalyticsHomeDatasets(request);
//		futureResponse.thenApplyAsync(response -> {
//			response.datasetFilters();
//			return null;
//		});

//		//Total visitas por mes en el año
//		final String json = "[{\"name\": \"Ene\",\"value\": 400},{\"name\": \"Feb\",\"value\": 335},{\"name\": \"Mar\",\"value\": 780},{\"name\": \"Abr\",\"value\": 100},{\"name\": \"May\",\"value\": 1021},{\"name\": \"Jun\",\"value\": 123},{\"name\": \"Jul\",\"value\": 843},{\"name\": \"Ago\",\"value\": 149},{\"name\": \"Sep\",\"value\": 634},{\"name\": \"Oct\",\"value\": 742},{\"name\": \"Nov\",\"value\": 456},{\"name\": \"Dic\",\"value\": 541}]";
//		final JsValue visitsByMonthJson = Json.parse(json);
//
//		//Visitas de Hombres por mes
//		final String json2 = "[{\"name\": \"Ene\",\"value\": 300},{\"name\": \"Feb\",\"value\": 100},{\"name\": \"Mar\",\"value\": 560},{\"name\": \"Abr\",\"value\": 20},{\"name\": \"May\",\"value\": 700},{\"name\": \"Jun\",\"value\": 100},{\"name\": \"Jul\",\"value\": 200},{\"name\": \"Ago\",\"value\": 100},{\"name\": \"Sep\",\"value\": 200},{\"name\": \"Oct\",\"value\": 600},{\"name\": \"Nov\",\"value\": 200},{\"name\": \"Dic\",\"value\": 300}]";
//		final JsValue visitsByPeriodMaleJson = Json.parse(json2);
//
//		//Visitas de Mujeres por mes
//		final String json3 = "[{\"name\": \"Ene\",\"value\": 100},{\"name\": \"Feb\",\"value\": 235},{\"name\": \"Mar\",\"value\": 220},{\"name\": \"Abr\",\"value\": 80},{\"name\": \"May\",\"value\": 321},{\"name\": \"Jun\",\"value\": 23},{\"name\": \"Jul\",\"value\": 643},{\"name\": \"Ago\",\"value\": 49},{\"name\": \"Sep\",\"value\": 434},{\"name\": \"Oct\",\"value\": 142},{\"name\": \"Nov\",\"value\": 256},{\"name\": \"Dic\",\"value\": 241}]";
//		final JsValue visitsByPeriodFemaleJson = Json.parse(json3);

//		//Visitas promedio en el rango horario 0am a 8am por DIA. Le agregué el nombre del dia para guiarme pero después no lo uso
//		final String json4 = "[{\"name\": \"Lunes\",\"value\": 25},{\"name\": \"Martes\",\"value\": 40},{\"name\": \"Miercoles\",\"value\": 30},{\"name\": \"Jueves\",\"value\": 5},{\"name\": \"Viernes\",\"value\": 9},{\"name\": \"Sabado\",\"value\": 80},{\"name\": \"Domingo\",\"value\": 120}]";
//		final JsValue visits0_8Json = Json.parse(json4);
//
//		//Visitas promedio en el rango horario 8am a 11am por DIA.
//		final String json5 = "[{\"name\": \"Lunes\",\"value\": 75},{\"name\": \"Martes\",\"value\": 80},{\"name\": \"Miercoles\",\"value\": 100},{\"name\": \"Jueves\",\"value\": 15},{\"name\": \"Viernes\",\"value\": 80},{\"name\": \"Sabado\",\"value\": 180},{\"name\": \"Domingo\",\"value\": 200}]";
//		final JsValue visits8_11Json = Json.parse(json5);
//
//		//Visitas promedio en el rango horario 11am a 1pm por DIA.
//		final String json6 = "[{\"name\": \"Lunes\",\"value\": 230},{\"name\": \"Martes\",\"value\": 350},{\"name\": \"Miercoles\",\"value\": 280},{\"name\": \"Jueves\",\"value\": 300},{\"name\": \"Viernes\",\"value\": 315},{\"name\": \"Sabado\",\"value\": 410},{\"name\": \"Domingo\",\"value\": 360}]";
//		final JsValue visits11_13Json = Json.parse(json6);
//
//		//Visitas promedio en el rango horario 1pm a 4pm por DIA.
//		final String json7 = "[{\"name\": \"Lunes\",\"value\": 156},{\"name\": \"Martes\",\"value\": 230},{\"name\": \"Miercoles\",\"value\": 136},{\"name\": \"Jueves\",\"value\": 136},{\"name\": \"Viernes\",\"value\": 245},{\"name\": \"Sabado\",\"value\": 146},{\"name\": \"Domingo\",\"value\": 135}]";
//		final JsValue visits13_16Json = Json.parse(json7);
//
//		//Visitas promedio en el rango horario 4pm a 8pm por DIA.
//		final String json8 = "[{\"name\": \"Lunes\",\"value\": 136},{\"name\": \"Martes\",\"value\": 146},{\"name\": \"Miercoles\",\"value\": 80},{\"name\": \"Jueves\",\"value\": 125},{\"name\": \"Viernes\",\"value\": 136},{\"name\": \"Sabado\",\"value\": 178},{\"name\": \"Domingo\",\"value\": 145}]";
//		final JsValue visits16_20Json = Json.parse(json8);
//
//		//Visitas promedio en el rango horario 8pm a 0am por DIA.
//		final String json9 = "[{\"name\": \"Lunes\",\"value\": 136},{\"name\": \"Martes\",\"value\": 236},{\"name\": \"Miercoles\",\"value\": 184},{\"name\": \"Jueves\",\"value\": 297},{\"name\": \"Viernes\",\"value\": 278},{\"name\": \"Sabado\",\"value\": 147},{\"name\": \"Domingo\",\"value\": 185}]";
//		final JsValue jsValue9 = Json.parse(json9);

//		//Visitas con duración menor a 15 minutos de la última semana. Dice "Lunes, Martes, etc" Pero ahí debería ser cada día de la última semana
//		final String json10 = "[{\"name\": \"Lunes\",\"value\": 130},{\"name\": \"Martes\",\"value\": 150},{\"name\": \"Miercoles\",\"value\": 100},{\"name\": \"Jueves\",\"value\": 300},{\"name\": \"Viernes\",\"value\": 123},{\"name\": \"Sabado\",\"value\": 316},{\"name\": \"Domingo\",\"value\": 231}]";
//		final JsValue visits0to15Json = Json.parse(json10);
//
//		//Visitas con duración entre 15 y 30 min
//		final String json11 = "[{\"name\": \"Lunes\",\"value\": 80},{\"name\": \"Martes\",\"value\": 400},{\"name\": \"Miercoles\",\"value\": 165},{\"name\": \"Jueves\",\"value\": 180},{\"name\": \"Viernes\",\"value\": 300},{\"name\": \"Sabado\",\"value\": 97},{\"name\": \"Domingo\",\"value\": 150}]";
//		final JsValue jsValue11 = Json.parse(json11);
//
//		//Visitas con duración entre 30 y 45 min
//		final String json12 = "[{\"name\": \"Lunes\",\"value\": 250},{\"name\": \"Martes\",\"value\": 365},{\"name\": \"Miercoles\",\"value\": 178},{\"name\": \"Jueves\",\"value\": 365},{\"name\": \"Viernes\",\"value\": 400},{\"name\": \"Sabado\",\"value\": 154},{\"name\": \"Domingo\",\"value\": 294}]";
//		final JsValue jsValue12 = Json.parse(json12);
//
//		//Visitas con duración entre 45 y 60 min
//		final String json13 = "[{\"name\": \"Lunes\",\"value\": 400},{\"name\": \"Martes\",\"value\": 147},{\"name\": \"Miercoles\",\"value\": 367},{\"name\": \"Jueves\",\"value\": 153},{\"name\": \"Viernes\",\"value\": 134},{\"name\": \"Sabado\",\"value\": 187},{\"name\": \"Domingo\",\"value\": 316}]";
//		final JsValue jsValue13 = Json.parse(json13);
//
//		//Visitas con duración entre 45 y 60 min
//		final String json14 = "[{\"name\": \"Lunes\",\"value\": 123},{\"name\": \"Martes\",\"value\": 145},{\"name\": \"Miercoles\",\"value\": 331},{\"name\": \"Jueves\",\"value\": 166},{\"name\": \"Viernes\",\"value\": 229},{\"name\": \"Sabado\",\"value\": 326},{\"name\": \"Domingo\",\"value\": 356}]";
//		final JsValue jsValue14 = Json.parse(json14);

		//Visitas de la semana 1
		final String json15 = "[{\"name\": \"Lunes\",\"value\": 236},{\"name\": \"Martes\",\"value\": 365},{\"name\": \"Miercoles\",\"value\": 256},{\"name\": \"Jueves\",\"value\": 456},{\"name\": \"Viernes\",\"value\": 365},{\"name\": \"Sabado\",\"value\": 136},{\"name\": \"Domingo\",\"value\": 229}]";
		final JsValue jsValue15 = Json.parse(json15);

		//Visitas de la semana 2
		final String json16 = "[{\"name\": \"Lunes\",\"value\": 123},{\"name\": \"Martes\",\"value\": 334},{\"name\": \"Miercoles\",\"value\": 154},{\"name\": \"Jueves\",\"value\": 326},{\"name\": \"Viernes\",\"value\": 154},{\"name\": \"Sabado\",\"value\": 265},{\"name\": \"Domingo\",\"value\": 336}]";
		final JsValue jsValue16 = Json.parse(json16);

		//Visitas de la semana 3
		final String json17 = "[{\"name\": \"Lunes\",\"value\": 236},{\"name\": \"Martes\",\"value\": 496},{\"name\": \"Miercoles\",\"value\": 345},{\"name\": \"Jueves\",\"value\": 123},{\"name\": \"Viernes\",\"value\": 423},{\"name\": \"Sabado\",\"value\": 236},{\"name\": \"Domingo\",\"value\": 114}]";
		final JsValue jsValue17 = Json.parse(json17);

		//Visitas de la semana 4
		final String json18 = "[{\"name\": \"Lunes\",\"value\": 364},{\"name\": \"Martes\",\"value\": 147},{\"name\": \"Miercoles\",\"value\": 264},{\"name\": \"Jueves\",\"value\": 164},{\"name\": \"Viernes\",\"value\": 361},{\"name\": \"Sabado\",\"value\": 265},{\"name\": \"Domingo\",\"value\": 423}]";
		final JsValue jsValue18 = Json.parse(json18);

		CommonProfile currentProfile = getCurrentProfile();
		Long portalId = (Long) currentProfile.getAttribute("portal");

		GetAnalyticsDataRequest analyticsRequest = new GetAnalyticsDataRequest(portalId, DateHelper.now());

		GetAnalyticsDataResponse analyticsData = analyticsService.getAnalyticsData(analyticsRequest);

		List<VisitsByPeriod> visitsByMonth = analyticsData.visitsByMonthLastYear();
		final JsValue visitsByMonthJson = JsonHelper.toJson(visitsByMonth);

		VisitsByPeriodByGender visitsByPeriodByGender = analyticsData.visitsByMonthLastYearByGender();
		List<VisitsByPeriod> visitsByPeriodMale = visitsByPeriodByGender.male();
		List<VisitsByPeriod> visitsByPeriodFemale = visitsByPeriodByGender.female();
		JsValue visitsByPeriodMaleJson = JsonHelper.toJson(visitsByPeriodMale);
		JsValue visitsByPeriodFemaleJson = JsonHelper.toJson(visitsByPeriodFemale);

		VisitsByDayByTimeRange visitsByDayByTimeRange = analyticsData.visitsByDayByTimeRange();
		List<VisitsByPeriod> visits0_8 = visitsByDayByTimeRange.visits0_8();
		List<VisitsByPeriod> visits8_11 = visitsByDayByTimeRange.visits8_11();
		List<VisitsByPeriod> visits11_13 = visitsByDayByTimeRange.visits11_13();
		List<VisitsByPeriod> visits13_16 = visitsByDayByTimeRange.visits13_16();
		List<VisitsByPeriod> visits16_20 = visitsByDayByTimeRange.visits16_20();
		List<VisitsByPeriod> visits20_24 = visitsByDayByTimeRange.visits20_24();
		JsValue visits0_8Json = JsonHelper.toJson(visits0_8);
		JsValue visits8_11Json = JsonHelper.toJson(visits8_11);
		JsValue visits11_13Json = JsonHelper.toJson(visits11_13);
		JsValue visits13_16Json = JsonHelper.toJson(visits13_16);
		JsValue visits16_20Json = JsonHelper.toJson(visits16_20);
		JsValue visits20_24Json = JsonHelper.toJson(visits20_24);

		List<VisitsByPeriod> emptyList = new ArrayList<>();
		Map<Tuple2<Integer, Integer>, List<VisitsByPeriod>> visitsByDurationLastWeek = analyticsData.visitsByDurationLastWeek();
		List<VisitsByPeriod> visits0to15 = visitsByDurationLastWeek.getOrDefault(MinutesRange.RANGE_0_TO_15, emptyList);
		List<VisitsByPeriod> visits15to30 = visitsByDurationLastWeek.getOrDefault(MinutesRange.RANGE_15_TO_30, emptyList);
		List<VisitsByPeriod> visits30to45 = visitsByDurationLastWeek.getOrDefault(MinutesRange.RANGE_30_TO_45, emptyList);
		List<VisitsByPeriod> visits45to60 = visitsByDurationLastWeek.getOrDefault(MinutesRange.RANGE_45_TO_60, emptyList);
		List<VisitsByPeriod> visits60toInf = visitsByDurationLastWeek.getOrDefault(MinutesRange.RANGE_60_TO_INF, emptyList);
		JsValue visits0to15Json = JsonHelper.toJson(takeLastWeek(visits0to15));
		JsValue visits15to30Json = JsonHelper.toJson(takeLastWeek(visits15to30));
		JsValue visits30to45Json = JsonHelper.toJson(takeLastWeek(visits30to45));
		JsValue visits45to60Json = JsonHelper.toJson(takeLastWeek(visits45to60));
		JsValue visits60toInfJson = JsonHelper.toJson(takeLastWeek(visits60toInf));

		return ok(views.html.admin.analytics.render(currentProfile,
				visitsByMonthJson,
				visitsByPeriodMaleJson, visitsByPeriodFemaleJson,
				visits0_8Json, visits8_11Json, visits11_13Json, visits13_16Json, visits16_20Json, visits20_24Json,
				visits0to15Json, visits15to30Json, visits30to45Json, visits45to60Json, visits60toInfJson,
				jsValue15, jsValue16, jsValue17, jsValue18));

	}

	@SubjectPresent(handlerKey = "FormClient", forceBeforeAuthCheck = true)
	public Result connections() throws NoProfileFoundException {
		final Form<PortalNetworkConfiguration> form = formFactory.form(PortalNetworkConfiguration.class);
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final List<ConnectedUser> connectedUsers = ConnectionsService.connectedUsers( (Portal) playSessionStore.get(context, "portal"));
		CommonProfile currentProfile = getCurrentProfile();
		return ok(views.html.admin.connections.render(form, connectedUsers, currentProfile));
	}

	public Result portalSettings() {
		return notFound();
	}

	private List<VisitsByPeriod> takeLastWeek(List<VisitsByPeriod> list) {
		int size = list.size();
		return list.subList(size - Math.min(size, 7), size);
	}

	private CommonProfile getCurrentProfile() throws NoProfileFoundException {
		final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
		final ProfileManager<CommonProfile> profileManager = new ProfileManager(context);
		Optional<CommonProfile> currentProfile = profileManager.get(true);
		return currentProfile.orElseThrow(() -> new NoProfileFoundException("No profile in current session logged in. There should be a profile in session at this point."));
	}

	private static class NoProfileFoundException extends Exception {
		NoProfileFoundException(String msg) {
			super(msg);
		}
	}

}
