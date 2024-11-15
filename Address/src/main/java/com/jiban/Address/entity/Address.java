package com.jiban.Address.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String lane1;

    @Column
    private String lane2;

    @Column
    private String state;

    @Column
    private long zip;
}
