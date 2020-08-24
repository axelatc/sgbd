package be.atc.dataAccess.entities;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "workers", schema = "projetsgbd", catalog = "")
@NamedQueries({
        @NamedQuery(name = "Workers.findWorkerByLogin",
                query = "SELECT w " +
                        "FROM WorkerEntity w " +
                        "WHERE w.username = :login"),
        @NamedQuery(name = "Workers.findAll",
                query = "SELECT w " +
                        "FROM WorkerEntity w"),
        @NamedQuery(name = "Workers.findWorkerById",
                query = "SELECT w " +
                        "FROM WorkerEntity w " +
                        "WHERE w.id = :id")
/*        @NamedQuery(name = "Workers.deleteWorkerById",
                query = "DELETE " +
                        "FROM WorkerEntity w " +
                        "WHERE w.id = :id"),
        @NamedQuery(name = "Workers.updateWorkerById",
                query = "UPDATE WorkerEntity w " +
                        "SET " +
                        "w.role = :rolesId, " +
                        "w.team = :teamsId, " +
                        "w.birthdate = :birthdate, " +
                        "w.firstName = :firstName, " +
                        "w.deleted = :isDeleted, " +
                        "w.lastName = :lastName, " +
                        "w.username = :login, " +
                        "w.password = :passwordKey, " +
                        "w.sexe = :sexe " +
                        "WHERE w.id = :id "*/
})
// JPA doesnt support INSERT JPSQL query so we use a SQL query
@NamedNativeQuery(name = "Workers.createWorker",
        query = "INSERT INTO workers" +
                "(" +
                "roles_id," +
                "teams_id," +
                "birthdate," +
                "first_name," +
                "is_deleted," +
                "last_name," +
                "login," +
                "password_key," +
                "sexe)" +
                "VALUES " +
                "(" +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?);",
        resultClass = WorkerEntity.class)
public class WorkerEntity {
    private int id;
    private LocalDate birthdate;
    private String firstName;
    private boolean isDeleted;
    private String lastName;
    private String username;
    private String password;
    private SexeType sexe;
    private RoleEntity role;
    private TeamEntity team;

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
    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
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

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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
    @Column(name = "username", nullable = false, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    @Basic
    @Column(name = "password_key", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        WorkerEntity that = (WorkerEntity) o;
        return id == that.id &&
                isDeleted == that.isDeleted &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(sexe, that.sexe);
    }

    @Override
    public String toString() {
        return "WorkersEntity{" +
                "id=" + id +
                ", birthdate=" + birthdate +
                ", firstName='" + firstName + '\'' +
                ", isDeleted=" + isDeleted +
                ", lastName='" + lastName + '\'' +
                ", login='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sexe=" + sexe +
                ", role=" + role +
                ", team=" + team +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, birthdate, firstName, isDeleted, lastName, username, password, sexe);
    }

    @ManyToOne
    @JoinColumn(name = "roles_id", referencedColumnName = "id", nullable = false)
    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "teams_id", referencedColumnName = "id", nullable = false)
    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }
}
