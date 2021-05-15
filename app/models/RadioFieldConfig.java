package models;

import java.util.List;

//@Entity
public class RadioFieldConfig extends FieldConfig {
    private List<Option> options;

    public RadioFieldConfig(String key, String label, Integer order, List<Option> options) {
        super(key, label, order);
        this.options = options;
    }

    public List<Option> getOtherOptions() {
        return options;
    }

    public void setOtherOptions(List<Option> otherOptions) {
        this.options = otherOptions;
    }
}
