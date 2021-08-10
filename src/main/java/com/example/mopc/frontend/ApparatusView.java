package com.example.mopc.frontend;

import com.example.mopc.repository.ApparatusRepository;
import com.example.mopc.repository.BaseEntityRepository;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;

@Route("apparatus")
public class ApparatusView extends BaseView {

    public ApparatusView(BaseEntityRepository baseEntityRepository, Authentication authentication, String nameView) {
        super(baseEntityRepository, authentication, nameView);

    }
}
