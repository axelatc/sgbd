package be.atc.dataAccess.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles_authorities", schema = "projetsgbd", catalog = "")
public class RolesAuthoritiesEntity {
    private int id;
    private RolesEntity rolesByRolesId;
    private AuthoritiesEntity authoritiesByAuthoritiesId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesAuthoritiesEntity that = (RolesAuthoritiesEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "roles_id", referencedColumnName = "id", nullable = false)
    public RolesEntity getRolesByRolesId() {
        return rolesByRolesId;
    }

    public void setRolesByRolesId(RolesEntity rolesByRolesId) {
        this.rolesByRolesId = rolesByRolesId;
    }

    @ManyToOne
    @JoinColumn(name = "authorities_id", referencedColumnName = "id", nullable = false)
    public AuthoritiesEntity getAuthoritiesByAuthoritiesId() {
        return authoritiesByAuthoritiesId;
    }

    public void setAuthoritiesByAuthoritiesId(AuthoritiesEntity authoritiesByAuthoritiesId) {
        this.authoritiesByAuthoritiesId = authoritiesByAuthoritiesId;
    }
}
