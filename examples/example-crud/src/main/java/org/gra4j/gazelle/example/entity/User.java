package org.gra4j.gazelle.example.entity;

import javax.persistence.*;

/**
 * @author pipiz
 */
@Entity
@Table(name = "T_USER")
public class User {

    @Id
    private Long id;

    @Column(name = "NAME")
    private String name;

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
