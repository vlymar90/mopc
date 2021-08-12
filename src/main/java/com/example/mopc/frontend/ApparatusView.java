package com.example.mopc.frontend;

import com.example.mopc.repository.ApparatusRepository;
import com.vaadin.flow.router.Route;


@Route("apparatus")
public class ApparatusView extends BaseView {

    public ApparatusView(ApparatusRepository baseEntityRepository) {
        super(baseEntityRepository,  "Аппараты");

    }
}
