package com.example.mopc.frontend;

import com.example.mopc.repository.OthersRepository;
import com.vaadin.flow.router.Route;

@Route("others")
public class OthersView extends BaseView {
    public OthersView(OthersRepository baseEntityRepository) {
        super(baseEntityRepository,  "Остальное");
    }
}
