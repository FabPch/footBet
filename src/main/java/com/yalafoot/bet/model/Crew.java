package com.yalafoot.bet.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uid;

    @Column
    private String name;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "admin")
    private Gambler admin;

    @ManyToMany
    @JoinTable(name = "crewjoin",
            joinColumns = @JoinColumn(name = "crew_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "gambler_id", referencedColumnName = "id"))
    private Set<Gambler> gamblers;

    public void addGamblers(Set<Gambler> gamblers){
        this.gamblers.addAll(gamblers);
    }

    public Gambler getAdmin() {
        return admin;
    }

    public void setAdmin(Gambler admin) {
        this.admin = admin;
    }

    public Set<Gambler> getGamblers() {
        return gamblers;
    }

    public void setGamblers(Set<Gambler> gamblers) {
        this.gamblers = gamblers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
