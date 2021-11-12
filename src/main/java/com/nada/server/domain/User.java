package com.nada.server.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Entity
@Getter @Setter
public class User implements Persistable<String> {

    @Id
    @Column(name = "user_id")
    private String id;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    // save 전 select문 없애기 위함
    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}

