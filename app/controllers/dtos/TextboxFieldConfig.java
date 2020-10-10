package controllers.dtos;

public class TextboxFieldConfig extends FieldConfig {

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
