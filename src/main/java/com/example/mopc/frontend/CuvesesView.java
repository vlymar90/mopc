package com.example.mopc.frontend;

import com.example.mopc.repository.BaseEntityRepository;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;

@Route("cuveses")
public class CuvesesView extends BaseView {
    public CuvesesView(BaseEntityRepository baseEntityRepository, Authentication authentication, String nameView) {
        super(baseEntityRepository, authentication, nameView);
    }
}
