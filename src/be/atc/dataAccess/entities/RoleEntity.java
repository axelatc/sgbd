package be.atc.dataAccess.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "roles", schema = "projetsgbd")
@NamedQueries({
        @NamedQuery(name = "Roles.findRoleByLabel",
                query = "SELECT w " +
                        "FROM RoleEntity w " +
                        "WHERE w.label = :label"),

        // CRUD Roles queries
        @NamedQuery(name = "Roles.findAll",
                query = "SELECT w " +
                        "FROM RoleEntity w"),
})
public class RoleEntity {
    private int id;
    private String description;
    private String label;
    private Collection<WorkerEntity> workers;
    private Collection<AuthorityEntity> authorities;

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
    public String getDescription() {
        return description;
    }

    public void setDescription(String descr) {
        this.description = descr;
    }

    @Basic
    @Column(name = "label", nullable = false, length = 255)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @OneToMany(mappedBy = "role")
    public Collection<WorkerEntity> getWorkers() {
        return workers;
    }

    public void setWorkers(Collection<WorkerEntity> workers) {
        this.workers = workers;
    }


    @ManyToMany
    @JoinTable(
            name = "roles_authorities",
            joinColumns = @JoinColumn(name = "roles_id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id"))
    public Collection<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return id == that.id &&
                Objects.equals(description, that.description) &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, label);
    }
}
