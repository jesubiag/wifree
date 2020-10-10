package controllers.dtos;

public abstract class FieldConfig {
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
