package models;


public class TextboxFieldConfig extends FieldConfig {

    private Boolean required;
    private String value;

    public TextboxFieldConfig(String key, String label, Boolean required, Integer order, String value) {
        super(key, label, order);
        this.required = required;
        this.value = value;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
