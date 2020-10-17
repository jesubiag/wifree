package models;

public class RatingFieldConfig extends FieldConfig {
    private Integer maximum;

    public RatingFieldConfig(String key, String label, Integer order, Integer maximum) {
        super(key, label, order);
        this.maximum = maximum;
    }

    @Override
    public SurveyAnswer getAnswer() {
        return null; // TODO algo con maximum
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }
}
