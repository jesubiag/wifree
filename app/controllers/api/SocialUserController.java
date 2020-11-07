package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.WiFreeController;
import controllers.api.dto.NetworkUserDTO;
import daos.NetworkUserDAO;
import daos.PortalDAO;
import models.NetworkUser;
import models.Portal;
import models.types.Gender;
import play.mvc.Result;
import utils.DateHelper;

import javax.inject.Inject;
import java.time.Instant;

import static java.util.Optional.ofNullable;

public class SocialUserController extends WiFreeController {

    @Inject
    private NetworkUserDAO networkUserDAO;

    public Result getSocialUser(String email) {
        return ofNullable(networkUserDAO.findByEmail(email))
                .map(networkUser -> ok(NetworkUserDTO.json(networkUser)))
                .orElse(badRequest("User [" + email + "] not found."));
    }

    public Result saveSocialUser() {
        JsonNode bodyJson = request().body().asJson();

        JsonNode names = bodyJson.withArray("names");
        JsonNode nameNode = names.get(0);
        String name = nameNode.findValue("displayName").asText();

        JsonNode genders = bodyJson.withArray("genders");
        JsonNode genderNode = genders.get(0);
        String gender = genderNode.findValue("formattedValue").asText();

        JsonNode birthdays = bodyJson.withArray("birthdays");
        JsonNode birthdayNode = birthdays.get(0);
        JsonNode date = birthdayNode.findValue("date");
        int year = date.findValue("year").asInt();
        int month = date.findValue("month").asInt();
        int day = date.findValue("day").asInt();

        JsonNode emailAddresses = bodyJson.withArray("emailAddresses");
        JsonNode emailAddressNode = emailAddresses.get(0);
        String email = emailAddressNode.findValue("value").asText();

        NetworkUserDAO networkUserDAO = new NetworkUserDAO();
        NetworkUser networkUser = networkUserDAO.findByEmail(email);
        if (networkUser == null) {
            Portal portal = new PortalDAO().get(1L);
            int age = createAge(year, month, day);
            networkUser = new NetworkUser(
                    portal,
                    name,
                    email,
                    null,
                    Instant.now(),
                    true,
                    "hola123",
                    Gender.valueOf(gender),
                    null,
                    null,
                    null,
                    age
            );
//            networkUserDAO.save(networkUser);
        }

        return ok(networkUser.toLogString());
    }

    private int createAge(int year, int month, int day) {
        return (int) DateHelper.yearsBetween(
                Instant.now(),
                Instant.parse(year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day) + "T00:00:00.000Z")
        );
    }

}
