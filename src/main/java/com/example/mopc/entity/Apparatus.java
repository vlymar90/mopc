package com.example.mopc.entity;

import javax.persistence.*;


@Entity()
@Table(schema = "mopc", name = "apparatus")
public class Apparatus extends BaseClassEntity {
    public Apparatus() {
    }
    public Apparatus(String name, String serialNumber, String inverted, String working, String comment) {
        super(name, serialNumber, inverted, working, comment);
    }
}
