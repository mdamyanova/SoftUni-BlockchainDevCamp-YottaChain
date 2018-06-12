package com.yottachain.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "blocks")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}