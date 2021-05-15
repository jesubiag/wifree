package core.databinders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.*;
import play.data.format.Formatters;
import play.libs.Json;
import utils.StringHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import static models.FieldConfig.FieldConfigTypes.*;

public class FieldConfigDataBinder extends Formatters.SimpleFormatter<FieldConfig> {

    @Override
    public FieldConfig parse(String text, Locale locale) throws ParseException {
        JsonNode fieldConfigJson = Json.toJson(text);
        String key = fieldConfigJson.findValue("key").asText();
        if (StringHelper.isNotEmpty(key)) {
            String label = fieldConfigJson.findValue("label").asText();
            int order = fieldConfigJson.findValue("order").asInt();
            switch (key) {
                case Textbox:
                    return createTextboxFieldConfig(fieldConfigJson, key, label, order);
                case Rating:
                    return createRatingFieldConfig(fieldConfigJson, key, label, order);
                case Radio:
                    return createRadioFieldConfig(fieldConfigJson, key, label, order);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String print(FieldConfig fieldConfig, Locale locale) {
        return Json.prettyPrint(Json.toJson(fieldConfig));
    }

    private TextboxFieldConfig createTextboxFieldConfig(JsonNode fieldConfigJson, String key, String label, int order) {
        boolean required = fieldConfigJson.findValue("required").asBoolean();
        String value = fieldConfigJson.findValue("value").asText();
        return new TextboxFieldConfig(key, label, required, order, value);
    }

    private RatingFieldConfig createRatingFieldConfig(JsonNode fieldConfigJson, String key, String label, int order) {
        int maximum = fieldConfigJson.findValue("maximum").asInt();
        return new RatingFieldConfig(key, label, order, maximum);
    }

    private RadioFieldConfig createRadioFieldConfig(JsonNode fieldConfigJson, String key, String label, int order) {
        JsonNode optionsNode = fieldConfigJson.findValue("options");
        ArrayList<Option> options = new ArrayList<>();
        if (optionsNode.isArray()) {
            ArrayNode optionsArray = (ArrayNode) optionsNode;
            for (int i = 0; i < optionsArray.size(); i++) {
                Integer optionIndex = optionsArray.get(i).findValue("index").asInt();
                String optionKey = optionsArray.get(i).findValue("key").asText();
                options.add(new Option(optionIndex, optionKey));
            }
        }
        return new RadioFieldConfig(key, label, order, options);
    }

}
