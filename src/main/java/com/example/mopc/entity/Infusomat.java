package com.example.mopc.entity;

import javax.persistence.*;

@Entity
@Table(schema = "mopc", name = "infusomat")
public class Infusomat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String serial;
    private String invert;

    private String working;
    private String comment;

    public Infusomat() {
    }

    public Infusomat(String name, String serialNumber, String invertedNumber, String isWorking, String comment) {
        this.name = name;
        this.serial = serialNumber;
        this.invert = invertedNumber;
        this.working = isWorking;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getInvert() {
        return invert;
    }

    public void setInvert(String invert) {
        this.invert = invert;
    }

    public String getWorking() {
        return working;
    }

    public void setWorking(String working) {
        this.working = working;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
