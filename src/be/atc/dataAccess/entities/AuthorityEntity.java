package be.atc.dataAccess.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "authorities", schema = "projetsgbd")
@NamedQueries({
        @NamedQuery(name = "Authorities.findAuthorityByLabel",
                query = "SELECT w " +
                        "FROM AuthorityEntity w " +
                        "WHERE w.label = :label"),

        // CRUD Authorities queries
        @NamedQuery(name = "Authorities.findAll",
                query = "SELECT w " +
                        "FROM AuthorityEntity w"),
})
public class AuthorityEntity {
    private int id;
    private String descr;
    private String label;
    private Collection<RoleEntity> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "descr", nullable = false, length = 2000)
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

    @ManyToMany
    @JoinTable(
            name = "roles_authorities",
            joinColumns = @JoinColumn(name = "authorities_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityEntity that = (AuthorityEntity) o;
        return id == that.id &&
                Objects.equals(descr, that.descr) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descr, label);
    }
}
