package com.example.demo.data.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Column(name = "created_date", nullable = false)
    protected LocalDateTime createdDate;
    @Column(name = "updated_date", nullable = false)
    protected LocalDateTime updatedDate;
    protected boolean       deleted;

    @PrePersist
    public void prePersist () {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
        deleted = false;
    }

    @PreUpdate
    public void preUpdate () {
        updatedDate = LocalDateTime.now();
    }
}
