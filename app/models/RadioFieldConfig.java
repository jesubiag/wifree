package models;

import java.util.List;

public class RadioFieldConfig extends FieldConfig {
    private List<Option> options;

    public RadioFieldConfig(String key, String label, Integer order, List<Option> options) {
        super(key, label, order);
        this.options = options;
    }

    @Override
    public SurveyAnswer getAnswer() {
        return null; // TODO algo con options
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
