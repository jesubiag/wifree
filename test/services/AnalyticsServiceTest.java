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
import models.types.Gender;
import operations.requests.GetAnalyticsDataRequest;
import operations.requests.GetDashboardDataRequest;
import operations.responses.GetAnalyticsDataResponse;
import operations.responses.GetDashboardDataResponse;
import operations.responses.VisitsByPeriod;
import org.junit.Before;
import org.junit.Test;
import utils.WiFreeSuite;

import java.time.Instant;
import java.util.ArrayList;

import static java.time.Instant.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static services.core.AgeRange.*;


public class AnalyticsServiceTest extends WiFreeSuite {

    private AnalyticsService analyticsService;
    private GetDashboardDataResponse dashboardDataResponse;
    private GetAnalyticsDataResponse analyticsDataResponse;
    private Portal portal;
    private NetworkUser networkUser1;
    private NetworkUser networkUser2;
    private NetworkUser networkUser3;
    private Instant now;

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

        // Network user
        networkUser1 = new NetworkUser(portal, "User 1", "user1@mail.com", "", null, false, "", Gender.Other, null, null, null, 33);
        networkUser2 = new NetworkUser(portal, "User 2", "user2@mail.com", "", null, false, "", Gender.Male, null, null, null, 25);
        networkUser3 = new NetworkUser(portal, "User 3", "user3@mail.com", "", null, false, "", Gender.Female, null, null, null, 55);
        NetworkUserDAO networkUserDAO = new NetworkUserDAO();
        networkUserDAO.save(networkUser1);
        networkUserDAO.save(networkUser2);
        networkUserDAO.save(networkUser3);

        setupConnectionLogs();
    }

    @Test
    public void getTheDataRequiredForTheDashboard() {
        Long portalId = this.portal.getId();
        GetDashboardDataRequest getDashboardDataRequest = new GetDashboardDataRequest(portalId, now);

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
        Long portalId = this.portal.getId();
        GetAnalyticsDataRequest request = new GetAnalyticsDataRequest(portalId, now);

        whenAnalyticsDataIsAsked(request);

        thenItsLastYearVisitsByMonthShouldBe();
    }

    private void thenItsLastYearVisitsByMonthShouldBe() {
        ArrayList<VisitsByPeriod> visitsByMonth = new ArrayList<>();
        visitsByMonth.add(new VisitsByPeriod("2019-10", 1));
        visitsByMonth.add(new VisitsByPeriod("2019-11", 5));

        assertThat(analyticsDataResponse.visitsByMonth())
                .isEqualTo(visitsByMonth);
    }

    private void whenAnalyticsDataIsAsked(GetAnalyticsDataRequest request) {
        analyticsDataResponse = analyticsService.getAnalyticsData(request);
    }

    private void setupConnectionLogs() {
        Instant lessOneMonth = parse("2019-10-18T00:00:00.000Z");
        Instant lessOneMonthPlusThreeDays = parse("2019-10-21T00:00:00.000Z");
        Instant lessTwoWeeks = parse("2019-11-04T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(-oneWeek * 2);
        Instant lessOneWeekLessOneDay = parse("2019-11-10T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(-oneWeek - oneDay);
        Instant plusOneDay = parse("2019-11-19T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(oneDay);
        Instant plusTwoDays = parse("2019-11-20T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(twoDays);
        Instant plusThreeDays = parse("2019-11-21T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(threeDays);
        Instant plusOneWeekPlusOneHour = parse("2019-11-25T01:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(oneWeek + oneHour);
        Instant plusOneWeekPlusOneDay = parse("2019-11-26T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(oneWeek + oneDay);
        Instant plusOneWeekPlusTwoDays = parse("2019-11-27T00:00:00.000Z"); //mon_18_11_19_00_00_00.plusSeconds(oneWeek + twoDays);

        NetworkUserConnectionLog connectionLog11 = new NetworkUserConnectionLog(portal, networkUser1, now, plusTwoDays, null);
        NetworkUserConnectionLog connectionLog12 = new NetworkUserConnectionLog(portal, networkUser1, now, plusThreeDays, null);
        NetworkUserConnectionLog connectionLog13 = new NetworkUserConnectionLog(portal, networkUser1, plusOneDay, plusThreeDays, null);

        NetworkUserConnectionLog connectionLog21 = new NetworkUserConnectionLog(portal, networkUser2, lessTwoWeeks, lessOneWeekLessOneDay, null);
        NetworkUserConnectionLog connectionLog22 = new NetworkUserConnectionLog(portal, networkUser2, plusOneWeekPlusOneDay, plusOneWeekPlusTwoDays, null);
        NetworkUserConnectionLog connectionLog23 = new NetworkUserConnectionLog(portal, networkUser2, lessOneMonth, lessOneMonthPlusThreeDays, null);

        NetworkUserConnectionLog connectionLog31 = new NetworkUserConnectionLog(portal, networkUser3, lessOneWeekLessOneDay, now, null);
        NetworkUserConnectionLog connectionLog32 = new NetworkUserConnectionLog(portal, networkUser3, plusThreeDays, plusOneWeekPlusOneHour, null);
        NetworkUserConnectionLog connectionLog33 = new NetworkUserConnectionLog(portal, networkUser3, now, plusOneDay, null);

        NetworkUserConnectionLogDAO connectionLogDAO = new NetworkUserConnectionLogDAO();
        connectionLogDAO.saveAll(
                connectionLog11, connectionLog12, connectionLog13,
                connectionLog21, connectionLog22, connectionLog23,
                connectionLog31, connectionLog32, connectionLog33
        );
    }

    private void whenDashboardDataIsAsked(GetDashboardDataRequest getDashboardDataRequest) {
        dashboardDataResponse = analyticsService.getDashboardData(getDashboardDataRequest);
    }

    private void thenItsBusiestDayLastWeekShouldBe() {
        assertThat(dashboardDataResponse.busiestDayLastWeek())
                .isEqualTo("2019-11-18");
    }

    private void thenItsUsersByGenderLastWeekShouldBe() {
        assertThat(dashboardDataResponse.usersByGenderLastWeek().keySet())
                .containsExactlyInAnyOrder(Gender.Female, Gender.Other);
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
}
