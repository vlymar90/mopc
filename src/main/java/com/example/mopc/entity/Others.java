package com.example.mopc.entity;

import javax.persistence.*;

@Entity
@Table(schema = "mopc", name = "others")
public class Others extends BaseClassEntity   {
    public Others() {
    }

    public Others(String name, String serialNumber, String inverted, String working, String comment) {
        super(name, serialNumber, inverted, working, comment);
    }
}
