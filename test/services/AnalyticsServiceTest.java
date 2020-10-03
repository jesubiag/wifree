package services;

import daos.AdminDAO;
import daos.NetworkUserConnectionLogDAO;
import daos.NetworkUserDAO;
import daos.PortalDAO;
import models.Admin;
import models.NetworkUser;
import models.NetworkUserConnectionLog;
import models.Portal;
import models.types.AccountType;
import operations.requests.GetAnalyticsDataRequest;
import operations.requests.GetDashboardDataRequest;
import operations.responses.GetAnalyticsDataResponse;
import operations.responses.GetDashboardDataResponse;
import operations.responses.VisitsByDayByTimeRange;
import operations.responses.VisitsByPeriod;
import org.junit.Before;
import org.junit.Test;
import scala.Tuple2;
import services.core.MinutesRange;
import utils.WiFreeSuite;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.time.Instant.parse;
import static models.types.Gender.*;
import static org.assertj.core.api.Assertions.assertThat;
import static services.core.AgeRange.RANGE_31_TO_40;
import static services.core.AgeRange.RANGE_51_TO_150;


public class AnalyticsServiceTest extends WiFreeSuite {

    private AnalyticsService analyticsService;
    private GetDashboardDataResponse dashboardDataResponse;
    private GetAnalyticsDataResponse analyticsDataResponse;
    private Portal portal;
    private NetworkUser networkUser1;
    private NetworkUser networkUser2;
    private NetworkUser networkUser3;
    private Instant now;
    private Long portalId;
    private Long oneMinute = 60L;

    @Before
    public void setup() {
        analyticsService = new AnalyticsService();

        // Now: Monday 18/11/2019 00:00:00.000
        now = parse("2019-11-18T08:00:00.00Z");

        // Admin
        Admin admin = new Admin(null, "Don Admin", "donadmin@mail.com", "", null, false);
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.save(admin);

        // Portal
        portal = new Portal("Test Portal", "Portal de pruebas", AccountType.Basic, admin, null, null, null, null, null);
        PortalDAO portalDAO = new PortalDAO();
        portalDAO.save(portal);
        portalId = portal.getId();

        // Network user
        networkUser1 = new NetworkUser(portal, "User 1", "user1@mail.com", "", null, false, "", Other, null, null, null, 33);
        networkUser2 = new NetworkUser(portal, "User 2", "user2@mail.com", "", null, false, "", Male, null, null, null, 25);
        networkUser3 = new NetworkUser(portal, "User 3", "user3@mail.com", "", null, false, "", Female, null, null, null, 55);
        NetworkUserDAO networkUserDAO = new NetworkUserDAO();
        networkUserDAO.save(networkUser1);
        networkUserDAO.save(networkUser2);
        networkUserDAO.save(networkUser3);

        setupConnectionLogs();
    }

    @Test
    public void getTheDataRequiredForTheDashboard() {
        GetDashboardDataRequest getDashboardDataRequest = givenAGetDashboardDataRequest();

        whenDashboardDataIsAsked(getDashboardDataRequest);

        thenItsUsersConnectedLastWeekShouldBe();
        thenItsUsersByAgeLastWeekShouldBe();
        thenItsNewUsersLastWeekShouldBe();
        thenItsUsersByGenderLastWeekShouldBe();
        thenItsBusiestDayLastWeekShouldBe();
        thenItsBusiestTimeLastWeekShouldBe();
        thenItsUsersLoyaltyShouldBe();
        thenItsUsersOnlineShouldBe();
    }

    @Test
    public void getTheDataRequiredForAnalytics() {
        GetAnalyticsDataRequest request = givenAGetAnalyticsDataRequest();

        whenAnalyticsDataIsAsked(request);

        thenItsLastYearVisitsByMonthShouldBe();
        thenItsLastYearVisitsByMonthByGenderShouldBe();
        thenItsVisitsByDayByTimeRangeShouldBe();
        thenItsVisitsByDurationLastWeekShouldBe();
    }

    private void setupConnectionLogs() {
        Instant lessOneMonth = parse("2019-10-18T00:00:00.000Z");
        Instant lessOneMonthPlusThreeDays = parse("2019-10-21T00:00:00.000Z");
        Instant lessTwoWeeks = parse("2019-11-04T16:45:12.000Z"); //mon_18_11_19_00_00_00.plusSeconds(-oneWeek * 2);
        Instant lessOneWeekLessOneDay = parse("2019-11-10T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(-oneWeek - oneDay);
        Instant plusOneDay = parse("2019-11-19T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(oneDay);
        Instant plusTwoDays = parse("2019-11-20T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(twoDays);
        Instant plusThreeDays = parse("2019-11-21T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(threeDays);
        Instant plusOneWeekPlusOneHour = parse("2019-11-25T01:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(oneWeek + oneHour);
        Instant plusOneWeekPlusOneDay = parse("2019-11-26T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(oneWeek + oneDay);
        Instant plusOneWeekPlusTwoDays = parse("2019-11-27T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(oneWeek + twoDays);

        NetworkUserConnectionLog connectionLog11 = new NetworkUserConnectionLog(portal, networkUser1, now, now.plusSeconds(oneMinute * 8), null);
        NetworkUserConnectionLog connectionLog12 = new NetworkUserConnectionLog(portal, networkUser1, now, now.plusSeconds(oneMinute * 80), null);
        NetworkUserConnectionLog connectionLog13 = new NetworkUserConnectionLog(portal, networkUser1, plusOneDay, plusOneDay.plusSeconds(oneMinute * 33), null);

        NetworkUserConnectionLog connectionLog21 = new NetworkUserConnectionLog(portal, networkUser2, lessTwoWeeks, lessTwoWeeks.plusSeconds(oneMinute * 22), null);
        NetworkUserConnectionLog connectionLog22 = new NetworkUserConnectionLog(portal, networkUser2, plusOneWeekPlusOneDay, plusOneWeekPlusOneDay.plusSeconds(oneMinute * 5), null);
        NetworkUserConnectionLog connectionLog23 = new NetworkUserConnectionLog(portal, networkUser2, lessOneMonth, lessOneMonth.plusSeconds(oneMinute * 52), null);

        NetworkUserConnectionLog connectionLog31 = new NetworkUserConnectionLog(portal, networkUser3, lessOneWeekLessOneDay, lessOneWeekLessOneDay.plusSeconds(oneMinute * 10), null);
        NetworkUserConnectionLog connectionLog32 = new NetworkUserConnectionLog(portal, networkUser3, plusThreeDays, plusThreeDays.plusSeconds(oneMinute * 17), null);
        NetworkUserConnectionLog connectionLog33 = new NetworkUserConnectionLog(portal, networkUser3, now, now.plusSeconds(oneMinute * 40), null);

        NetworkUserConnectionLogDAO connectionLogDAO = new NetworkUserConnectionLogDAO();
        connectionLogDAO.saveAll(
                connectionLog11, connectionLog12, connectionLog13,
                connectionLog21, connectionLog22, connectionLog23,
                connectionLog31, connectionLog32, connectionLog33
        );
    }


    // #### Given ####
    private GetDashboardDataRequest givenAGetDashboardDataRequest() {
        return new GetDashboardDataRequest(portalId, now);
    }

    private GetAnalyticsDataRequest givenAGetAnalyticsDataRequest() {
        return new GetAnalyticsDataRequest(portalId, now);
    }


    // #### When ####
    private void whenDashboardDataIsAsked(GetDashboardDataRequest getDashboardDataRequest) {
        dashboardDataResponse = analyticsService.getDashboardData(getDashboardDataRequest);
    }

    private void whenAnalyticsDataIsAsked(GetAnalyticsDataRequest request) {
        analyticsDataResponse = analyticsService.getAnalyticsData(request);
    }


    // #### Then ####
    private void thenItsBusiestDayLastWeekShouldBe() {
        assertThat(dashboardDataResponse.busiestDayLastWeek())
                .isEqualTo("2019-11-18");
    }

    private void thenItsUsersByGenderLastWeekShouldBe() {
        assertThat(dashboardDataResponse.usersByGenderLastWeek().keySet())
                .containsExactlyInAnyOrder(Female, Other);
    }

    private void thenItsNewUsersLastWeekShouldBe() {
        assertThat(dashboardDataResponse.newUsersLastWeek())
                .isEqualTo(1L);
    }

    private void thenItsUsersByAgeLastWeekShouldBe() {
        assertThat(dashboardDataResponse.usersByAgeLastWeek().keySet())
                .hasSize(2);
        assertThat(dashboardDataResponse.usersByAgeLastWeek().get(RANGE_31_TO_40))
                .isEqualTo(1L);
        assertThat(dashboardDataResponse.usersByAgeLastWeek().get(RANGE_51_TO_150))
                .isEqualTo(1L);
    }

    private void thenItsUsersConnectedLastWeekShouldBe() {
        assertThat(dashboardDataResponse.usersConnectedLastWeek())
                .isEqualTo(2L);
    }

    private void thenItsBusiestTimeLastWeekShouldBe() {
        assertThat(dashboardDataResponse.busiestTimeLastWeek())
                .isEqualTo("08:00");
    }

    private void thenItsUsersLoyaltyShouldBe() {
        assertThat(dashboardDataResponse.usersLoyalty())
                .isEqualTo(0.5);
    }

    private void thenItsUsersOnlineShouldBe() {
        assertThat(dashboardDataResponse.usersOnline())
                .isEqualTo(2);
    }

    private void thenItsLastYearVisitsByMonthShouldBe() {
        ArrayList<VisitsByPeriod> visitsByMonth = new ArrayList<>();
        visitsByMonth.add(new VisitsByPeriod("2019-10", 1));
        visitsByMonth.add(new VisitsByPeriod("2019-11", 5));

        assertThat(analyticsDataResponse.visitsByMonthLastYear())
                .isEqualTo(visitsByMonth);
    }

    private void thenItsLastYearVisitsByMonthByGenderShouldBe() {
        ArrayList<VisitsByPeriod> expectedMaleVisits = new ArrayList<>();
        expectedMaleVisits.add(new VisitsByPeriod("2019-10", 1));
        expectedMaleVisits.add(new VisitsByPeriod("2019-11", 1));
        ArrayList<VisitsByPeriod> expectedFemaleVisits = new ArrayList<>();
        expectedFemaleVisits.add(new VisitsByPeriod("2019-11", 2));

        List<VisitsByPeriod> actualMaleVisits = analyticsDataResponse.visitsByMonthLastYearByGender().male();
        List<VisitsByPeriod> actualFemaleVisits = analyticsDataResponse.visitsByMonthLastYearByGender().female();

        assertThat(actualMaleVisits)
                .isEqualTo(expectedMaleVisits);
        assertThat(actualFemaleVisits)
                .isEqualTo(expectedFemaleVisits);
    }

    private void thenItsVisitsByDayByTimeRangeShouldBe() {
        ArrayList<VisitsByPeriod> expectedVisits0_8 = new ArrayList<>();
        expectedVisits0_8.add(new VisitsByPeriod("SUNDAY", 1));
        expectedVisits0_8.add(new VisitsByPeriod("FRIDAY", 1));
        ArrayList<VisitsByPeriod> expectedVisits8_11 = new ArrayList<>();
        expectedVisits8_11.add(new VisitsByPeriod("MONDAY", 3));
        ArrayList<VisitsByPeriod> expectedVisits11_13 = new ArrayList<>();
        ArrayList<VisitsByPeriod> expectedVisits13_16 = new ArrayList<>();
        ArrayList<VisitsByPeriod> expectedVisits16_20 = new ArrayList<>();
        expectedVisits16_20.add(new VisitsByPeriod("MONDAY", 1));
        ArrayList<VisitsByPeriod> expectedVisits20_24 = new ArrayList<>();

        VisitsByDayByTimeRange visitsByDayByTimeRange = analyticsDataResponse.visitsByDayByTimeRange();
        List<VisitsByPeriod> actualVisits0_8 = visitsByDayByTimeRange.visits0_8();
        List<VisitsByPeriod> actualVisits8_11 = visitsByDayByTimeRange.visits8_11();
        List<VisitsByPeriod> actualVisits11_13 = visitsByDayByTimeRange.visits11_13();
        List<VisitsByPeriod> actualVisits13_16 = visitsByDayByTimeRange.visits13_16();
        List<VisitsByPeriod> actualVisits16_20 = visitsByDayByTimeRange.visits16_20();
        List<VisitsByPeriod> actualVisits20_24 = visitsByDayByTimeRange.visits20_24();

        assertThat(actualVisits0_8)
                .containsAll(expectedVisits0_8);
        assertThat(actualVisits8_11)
                .containsAll(expectedVisits8_11);
        assertThat(actualVisits11_13)
                .containsAll(expectedVisits11_13);
        assertThat(actualVisits13_16)
                .containsAll(expectedVisits13_16);
        assertThat(actualVisits16_20)
                .containsAll(expectedVisits16_20);
        assertThat(actualVisits20_24)
                .containsAll(expectedVisits20_24);
    }

    private void thenItsVisitsByDurationLastWeekShouldBe() {
        Map<Tuple2<Integer, Integer>, List<VisitsByPeriod>> actualVisitsByDurationLastWeek = analyticsDataResponse.visitsByDurationLastWeek();

        assertThat(actualVisitsByDurationLastWeek.size())
                .isEqualTo(3);
        assertThat(actualVisitsByDurationLastWeek.keySet())
                .containsExactlyInAnyOrder(
                        MinutesRange.RANGE_0_TO_15,
                        MinutesRange.RANGE_30_TO_45,
                        MinutesRange.RANGE_60_TO_INF
                );
        List<VisitsByPeriod> _0_15_visits = actualVisitsByDurationLastWeek.get(MinutesRange.RANGE_0_TO_15);
        List<VisitsByPeriod> _30_45_visits = actualVisitsByDurationLastWeek.get(MinutesRange.RANGE_30_TO_45);
        List<VisitsByPeriod> _60_inf_visits = actualVisitsByDurationLastWeek.get(MinutesRange.RANGE_60_TO_INF);
        assertThat(_0_15_visits.size())
                .isEqualTo(1);
        assertThat(_30_45_visits.size())
                .isEqualTo(1);
        assertThat(_60_inf_visits.size())
                .isEqualTo(1);
        assertThat(_0_15_visits)
                .containsExactly(new VisitsByPeriod("2019-11-18", 1L));
        assertThat(_30_45_visits)
                .containsExactly(new VisitsByPeriod("2019-11-18", 1L));
        assertThat(_60_inf_visits)
                .containsExactly(new VisitsByPeriod("2019-11-18", 1L));
    }
}
