package fr.arthb.motherrussia.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table
public class Gambler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String login;

    @Column
    @NotNull
    private String password;

    @Column
    private byte[] photo;

    @Column
    private int gain;

//    @ElementCollection(fetch = FetchType.EAGER)
//    List<Role> roles;
//
//    public List<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(List<Role> roles) {
//        this.roles = roles;
//    }

    @OneToMany(mappedBy = "admin", fetch=FetchType.EAGER)
    @JsonIgnore
//    @JsonManagedReference(value = "adminCrews")
    private Set<fr.arthb.motherrussia.model.Crew> adminCrews;

    @OneToMany(mappedBy = "gambler", fetch=FetchType.EAGER)
    @JsonIgnore
//    @JsonManagedReference(value = "pronostics")
    private Set<fr.arthb.motherrussia.model.Pronostic> pronostics;

    @OneToMany(mappedBy = "gambler")
    @JsonIgnore
//    @JsonManagedReference(value = "results")
    private Set<fr.arthb.motherrussia.model.Result> results;

    @ManyToMany(mappedBy = "gamblers")
    @JsonIgnore
    private Set<fr.arthb.motherrussia.model.Crew> crews;

    public Set<fr.arthb.motherrussia.model.Crew> getAdminCrews() {
        return adminCrews;
    }

    public void setAdminCrews(Set<fr.arthb.motherrussia.model.Crew> adminCrews) {
        this.adminCrews = adminCrews;
    }

    public Set<fr.arthb.motherrussia.model.Crew> getCrews() {
        return crews;
    }

    public void setCrews(Set<fr.arthb.motherrussia.model.Crew> crews) {
        this.crews = crews;
    }

    public Set<fr.arthb.motherrussia.model.Result> getResults() {
        return results;
    }

    public void setResults(Set<fr.arthb.motherrussia.model.Result> results) {
        this.results = results;
    }

    public Set<fr.arthb.motherrussia.model.Pronostic> getPronostics() {
        return pronostics;
    }

    public void setPronostics(Set<fr.arthb.motherrussia.model.Pronostic> pronostics) {
        this.pronostics = pronostics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getGain() {
        return gain;
    }

    public void setGain(int gain) {
        this.gain = gain;
    }
}
