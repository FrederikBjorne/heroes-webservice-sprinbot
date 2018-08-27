package com.example.demo.core;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/*
 This Pojo base class handles id's for entities residing in a database. Primary key generation strategies to be AUTO
 (persistence provider should pick an appropriate strategy for the particular database).
 */
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    protected BaseEntity() {
        this.id = null;
    }

    /**
     * Returns the auto-generated database id.
     * @return A database id identifying the deriving entity.
     */
    public Long getId() {
        return id;
    }
}
