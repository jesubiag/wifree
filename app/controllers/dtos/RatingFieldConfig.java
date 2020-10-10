package controllers.dtos;

public class RatingFieldConfig extends FieldConfig {
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
