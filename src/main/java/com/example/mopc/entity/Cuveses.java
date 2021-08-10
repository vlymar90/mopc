package com.example.mopc.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "mopc", name = "cuveses")
public class Cuveses extends BaseClassEntity {
    public Cuveses() {
    }

    public Cuveses(String name, String serialNumber, String inverted, String working, String comment) {
        super(name, serialNumber, inverted, working, comment);
    }
}
