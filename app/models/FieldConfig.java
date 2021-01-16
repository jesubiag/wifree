package models;

public abstract class FieldConfig {

    protected String key;
    protected String label;
    protected Integer order;

    protected FieldConfig(String key, String label, Integer order) {
        this.key = key;
        this.label = label;
        this.order = order;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
