package controllers.dtos;

import java.util.List;

public class Survey {
    private final Long id;
    private final Long portalId;
    private final String title;
    private final List<Field> fields;

    public Survey(Long id, Long portalId, String title, List<Field> fields) {
        this.id = id;
        this.portalId = portalId;
        this.title = title;
        this.fields = fields;
    }
}

