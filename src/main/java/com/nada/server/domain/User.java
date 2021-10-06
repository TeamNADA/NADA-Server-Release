package com.nada.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Card> cards = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Card> groups = new ArrayList<>();
}

