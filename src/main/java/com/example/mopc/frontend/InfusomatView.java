package com.example.mopc.frontend;

import com.example.mopc.repository.InfusomatRepository;
import com.vaadin.flow.router.Route;

@Route("infusomat")
public class InfusomatView extends BaseView {

    public InfusomatView(InfusomatRepository baseEntityRepository) {
        super(baseEntityRepository,  "Инфузоматы");

    }
}
