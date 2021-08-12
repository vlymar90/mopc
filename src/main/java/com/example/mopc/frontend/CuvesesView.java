package com.example.mopc.frontend;

import com.example.mopc.repository.CuvesesRepository;
import com.vaadin.flow.router.Route;

@Route("cuveses")
public class CuvesesView extends BaseView {
    public CuvesesView(CuvesesRepository baseEntityRepository) {
        super(baseEntityRepository,  "Кувезы и Системы");

    }
}
