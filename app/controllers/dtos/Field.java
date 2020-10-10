package controllers.dtos;

public class Field {
    private final Long id;
    private final String type;
    private final FieldConfig config;

    public Field(Long id, String type, FieldConfig config) {
        this.id = id;
        this.type = type;
        this.config = config;
    }
}
