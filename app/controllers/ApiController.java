package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import daos.NetworkUserDAO;
import daos.PortalDAO;
import models.NetworkUser;
import models.Portal;
import models.types.Gender;
import play.mvc.Result;
import utils.DateHelper;

import java.time.Instant;
import java.util.List;

public class ApiController extends WiFreeController {

    public Result socialUser() {
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
            int age = (int) DateHelper.yearsBetween(
                    Instant.now(),
                    Instant.parse(year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day) + "T00:00:00.000Z")
            );
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

    public Result survey() {
        JsonNode bodyJson = request().body().asJson();
        // TODO
        return ok();
    }

    class Survey {
        private final Long id;
        private final Long portalId;
        private final String title;
        private final List<Field> fields;

        public Survey(Long id, Long portalId, String title, List<Field> fields) {
            this.id = id;
            this.portalId = portalId;
            this.title = title;
            this.fields = fields;
        }
    }

    class Field {
        private final Long id;
        private final String type;
        private final TextboxFieldConfig config;

        public Field(Long id, String type, TextboxFieldConfig config) {
            this.id = id;
            this.type = type;
            this.config = config;
        }
    }

    abstract class FieldConfig {
        protected final String key;
        protected final String label;
        protected final Integer order;


        protected FieldConfig(String key, String label, Integer order) {
            this.key = key;
            this.label = label;
            this.order = order;
        }

        abstract public SurveyAnswer getAnswer();
    }

    abstract class SurveyAnswer {}

    class TextboxFieldConfig extends FieldConfig {
        private final Boolean required;
        private final String value;

        public TextboxFieldConfig(String key, String label, Boolean required, Integer order, String value) {
            super(key, label, order);
            this.required = required;
            this.value = value;
        }

        @Override
        public SurveyAnswer getAnswer() {
            return null; // TODO Algo con required y order
        }
    }

    class RatingFieldConfig extends FieldConfig {
        private final Integer maximum;

        public RatingFieldConfig(String key, String label, Integer order, Integer maximum) {
            super(key, label, order);
            this.maximum = maximum;
        }

        @Override
        public SurveyAnswer getAnswer() {
            return null; // TODO algo con maximum
        }
    }

    class RadioFieldConfig extends FieldConfig {
        private final List<Option> options;

        public RadioFieldConfig(String key, String label, Integer order, List<Option> options) {
            super(key, label, order);
            this.options = options;
        }

        @Override
        public SurveyAnswer getAnswer() {
            return null; // TODO algo con options
        }

        class Option {
            private final String key;

            public Option(String key) {
                this.key = key;
            }
        }
    }

}
