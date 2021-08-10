package com.example.mopc.frontend;

import com.example.mopc.repository.BaseEntityRepository;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;

@Route("infusomat")
public class InfusomatView extends BaseView {

    public InfusomatView(BaseEntityRepository baseEntityRepository, Authentication authentication, String nameView) {
        super(baseEntityRepository, authentication, nameView);
    }
}
