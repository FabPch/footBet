package com.yalafoot.bet.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
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

    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(mappedBy = "admin", fetch=FetchType.EAGER)
    @JsonIgnore
//    @JsonManagedReference(value = "adminCrews")
    private Set<Crew> adminCrews;

    @OneToMany(mappedBy = "gambler", fetch=FetchType.EAGER)
    @JsonIgnore
//    @JsonManagedReference(value = "pronostics")
    private Set<Pronostic> pronostics;

    @OneToMany(mappedBy = "gambler")
    @JsonIgnore
//    @JsonManagedReference(value = "results")
    private Set<Result> results;

    @ManyToMany(mappedBy = "gamblers")
    @JsonIgnore
    private Set<Crew> crews;

    public Set<Crew> getAdminCrews() {
        return adminCrews;
    }

    public void setAdminCrews(Set<Crew> adminCrews) {
        this.adminCrews = adminCrews;
    }

    public Set<Crew> getCrews() {
        return crews;
    }

    public void setCrews(Set<Crew> crews) {
        this.crews = crews;
    }

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }

    public Set<Pronostic> getPronostics() {
        return pronostics;
    }

    public void setPronostics(Set<Pronostic> pronostics) {
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
}
