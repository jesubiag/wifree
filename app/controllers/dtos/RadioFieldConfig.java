package controllers.dtos;

import java.util.List;

public class RadioFieldConfig extends FieldConfig {
    private final List<Option> options;

    public RadioFieldConfig(String key, String label, Integer order, List<Option> options) {
        super(key, label, order);
        this.options = options;
    }

    @Override
    public SurveyAnswer getAnswer() {
        return null; // TODO algo con options
    }

}
