package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Survey extends BaseModel {

    @ManyToOne(optional = false)
    private Portal portal;

    private String title;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "survey")
    private List<Field> fields;

    public Survey(Long id, Portal portal, String title, List<Field> fields) {
        this.id = id;
        this.portal = portal;
        this.title = title;
        this.fields = fields;
    }

    @Override
    public String toLogString() {
        return null;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Portal getPortal() {
        return portal;
    }

    public void setPortal(Portal portal) {
        this.portal = portal;
    }
}

