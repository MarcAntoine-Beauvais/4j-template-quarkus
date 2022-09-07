package com.me.my_application.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "my_table")
public class MyEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID uuid;

    @Column(nullable = true)
    public String name;

    @Column(nullable = true)
    @Convert(converter = Status.StatusJpaEnumConverter.class)
    public Status status;

}
