package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authorities", schema = "projetsgbd", catalog = "")
public class AuthoritiesEntity {
    private int id;
    private String descr;
    private String label;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "descr", nullable = true, length = 2000)
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Basic
    @Column(name = "label", nullable = false, length = 255)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthoritiesEntity that = (AuthoritiesEntity) o;
        return id == that.id &&
                Objects.equals(descr, that.descr) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descr, label);
    }
}
