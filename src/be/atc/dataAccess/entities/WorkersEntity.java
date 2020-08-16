package be.atc.dataAccess.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "workers", schema = "projetsgbd", catalog = "")
@NamedQueries({
        @NamedQuery(name="Workers.findWorkerByLogin",
                    query="SELECT w " +
                            "FROM WorkersEntity w " +
                            "WHERE w.login = :login")

})
public class WorkersEntity {
    private int id;
    private Date birthdate;
    private String firstName;
    private boolean isDeleted;
    private String lastName;
    private String login;
    private String passwordKey;
    private SexeType sexe;
    private RolesEntity rolesByRolesId;
    private TeamsEntity teamsByTeamsId;

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
    @Column(name = "birthdate", nullable = false)
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 255)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "is_deleted", nullable = false)
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 255)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "login", nullable = false, length = 255)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password_key", nullable = false, length = 255)
    public String getPasswordKey() {
        return passwordKey;
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", nullable = false)
    public SexeType getSexe() {
        return sexe;
    }

    public void setSexe(SexeType sexe) {
        this.sexe = sexe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkersEntity that = (WorkersEntity) o;
        return id == that.id &&
                isDeleted == that.isDeleted &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(login, that.login) &&
                Objects.equals(passwordKey, that.passwordKey) &&
                Objects.equals(sexe, that.sexe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, birthdate, firstName, isDeleted, lastName, login, passwordKey, sexe);
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
    @JoinColumn(name = "teams_id", referencedColumnName = "id", nullable = false)
    public TeamsEntity getTeamsByTeamsId() {
        return teamsByTeamsId;
    }

    public void setTeamsByTeamsId(TeamsEntity teamsByTeamsId) {
        this.teamsByTeamsId = teamsByTeamsId;
    }
}
