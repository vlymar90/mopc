package com.example.mopc.frontend;

import com.example.mopc.entity.*;
import com.example.mopc.repository.BaseEntityRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseView extends VerticalLayout {
    private final Grid<BaseClassEntity> grid = new Grid<>(BaseClassEntity.class);
    private final BaseEntityRepository baseEntityRepository;
    private TextField searchField;
    private List<BaseClassEntity> apparatusList;
    private Authentication authentication;
    private String nameView;

    BaseView(BaseEntityRepository baseEntityRepository,
             String nameView) {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        this.baseEntityRepository = baseEntityRepository;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        this.nameView = nameView;
        initView();
    }

    private void initView() {
        searchField = new TextField();
        searchField.setHelperText("Введите серийный номер");
        apparatusList = new ArrayList<>();
        add(headerButton());
        add(new Text(nameView));
        add(searchComponent());
        initGrid();
    }

    private HorizontalLayout headerButton() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setAlignItems(Alignment.START);
        Button main = new Button("Главная", item -> {
            UI.getCurrent().navigate("main");
        });
        hl.add(main);
        HorizontalLayout vl = new HorizontalLayout();
        hl.setAlignItems(Alignment.END);
        Button mistake = new Button("Сломанные", item -> {
            grid.setItems(baseEntityRepository.getAllByWorking("Не исправен"));
        });
        vl.add(mistake);
        return new HorizontalLayout(hl, vl);

    }

    private HorizontalLayout searchComponent() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setAlignItems(Alignment.START);
        searchField.focus();
        Button searchButton = new Button("Поиск", item -> {
            if (searchField.getValue().equals("")) {
                Notification.show("Введите номер");
            }
            apparatusList = baseEntityRepository.findAllBySerialContaining(searchField.getValue());
            grid.setItems(apparatusList);
        });
        hl.add(searchField, searchButton);
        return hl;
    }


    private void initGrid() {
        remove(grid);

        if (apparatusList.size() == 0) {
            apparatusList = baseEntityRepository.findAll();
        }
        if (apparatusList != null && apparatusList.size() != 0) {
            grid.setItems(apparatusList);
            grid.setColumns("name", "serial", "invert", "working", "comment");
            if(authentication.getName().equals("admin")) {
                grid.addColumn(new ComponentRenderer<>(item -> {
                    var changeButton = new Button("Удалить", i -> {
                        baseEntityRepository.deleteById(item.getId());
                        UI.getCurrent().getPage().reload();
                    });
                    return new HorizontalLayout(changeButton);
                }));
            }
            grid.setSizeUndefined();
            grid.setSelectionMode(Grid.SelectionMode.NONE);
            ListDataProvider<BaseClassEntity> dataProvider = DataProvider.ofCollection(apparatusList);
            grid.setDataProvider(dataProvider);
            grid.addItemClickListener(event -> showDialog(event.getItem()).open());
            add(grid);
        } else {
            Notification.show("Аппратура не найдена!");
        }
    }

    private Dialog showDialog(BaseClassEntity app) {
        Dialog d = new Dialog();
        if (app != null) {
            Text title = new Text("Устройство SN: " + app.getId());
            VerticalLayout hl = new VerticalLayout();
            ComboBox<String> isWorked = new ComboBox<>();
            isWorked.setItems("Исправен", "Не исправен");
            isWorked.setValue(app.getWorking());
            TextArea comment = new TextArea("Коментарий");
            comment.setValue(app.getComment());
            Button save = new Button("Изменить", item -> {
                Optional optional = baseEntityRepository.findById(app.getId());
                BaseClassEntity classEntity = get(optional);
                classEntity.setWorking(isWorked.getValue());
                classEntity.setComment(comment.getValue());
                baseEntityRepository.save(classEntity);
                UI.getCurrent().getPage().reload();
                d.close();
            });

            hl.add(isWorked, comment, save);
            d.add(title, hl);
            return d;
        }

        return d;
    }

    private BaseClassEntity get(Optional optional) {
        Object ob = optional.get();
        if(ob instanceof Apparatus) {
            return (Apparatus) ob;
        }
        if(ob instanceof Cuveses) {
            return (Cuveses) ob;
        }
        if(ob instanceof Infusomat) {
            return (Infusomat) ob;
        }
        if(ob instanceof Others) {
            return (Others) ob;
        }
        return null;
    }
}
