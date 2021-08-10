package com.example.mopc.entity;

import javax.persistence.*;

@Entity
@Table(schema = "mopc", name = "infusomat")
public class Infusomat extends BaseClassEntity {
    public Infusomat() {
    }

    public Infusomat(String name, String serialNumber, String inverted, String working, String comment) {
        super(name, serialNumber, inverted, working, comment);
    }
}
