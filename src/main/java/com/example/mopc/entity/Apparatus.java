package com.example.mopc.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity()
@Table(schema = "mopc", name = "apparatus")

public class Apparatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String serial;
    private String invert;

    private String working;
    private String comment;

    public Apparatus() {
    }

    public Apparatus( String name,
                     String serialNumber, String inverted,
                     String working, String comment) {
        this.name = name;
        this.serial = serialNumber;
        this.invert = inverted;
        this.working = working;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSerial() {
        return serial;
    }

    public String getInvert() {
        return invert;
    }

    public String getWorking() {
        return working;
    }

    public String getComment() {
        return comment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setInvert(String invert) {
        this.invert = invert;
    }

    public void setWorking(String working) {
        this.working = working;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
