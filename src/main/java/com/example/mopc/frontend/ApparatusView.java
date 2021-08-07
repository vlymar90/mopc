package com.example.mopc.frontend;

import com.example.mopc.entity.Apparatus;
import com.example.mopc.service.ApparatusService;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import org.springframework.security.core.Authentication;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@Route("apparatus")
public class ApparatusView extends VerticalLayout {
    private final Grid<Apparatus> grid = new Grid<>(Apparatus.class);
    private final ApparatusService apparatusService;
    private TextField searchField;
    private List<Apparatus> apparatusList;
    private Authentication authentication;

    ApparatusView(ApparatusService apparatusService) {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        this.apparatusService = apparatusService;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        searchField = new TextField();
        searchField.setHelperText("Введите серийный номер");
        apparatusList = new ArrayList<>();
        initView();
    }

    private void initView() {
        add(headerButton());
        add(new Text("Аппараты ИВЛ и SiPap"));
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
           grid.setItems(apparatusService.getAllMistake());
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
            apparatusList = apparatusService.getAllLikeSerialNumber(searchField.getValue());
            grid.setItems(apparatusList);
        });
        hl.add(searchField, searchButton);
        return hl;
    }


    private void initGrid() {
        remove(grid);

        if (apparatusList.size() == 0) {
            apparatusList = apparatusService.getAll();
        }
        if (apparatusList != null && apparatusList.size() != 0) {
            grid.setItems(apparatusList);
            grid.setColumns("name", "serial", "invert", "working", "comment");
            if(authentication.getName().equals("admin")) {
                grid.addColumn(new ComponentRenderer<>(item -> {
                    var changeButton = new Button("Удалить", i -> {
                        apparatusService.remove(item.getId());
                        UI.getCurrent().getPage().reload();
                    });
                    return new HorizontalLayout(changeButton);
                }));
            }
            grid.setSizeUndefined();
            grid.setSelectionMode(Grid.SelectionMode.NONE);
            ListDataProvider<Apparatus> dataProvider = DataProvider.ofCollection(apparatusList);
            grid.setDataProvider(dataProvider);
            grid.addItemClickListener(event -> showDialog(event.getItem()).open());
            add(grid);
        } else {
            Notification.show("Аппратура не найдена!");
        }
    }

    private Dialog showDialog(Apparatus app) {
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
                Apparatus apparatus = apparatusService.getEntity(app.getId());
                apparatus.setWorking(isWorked.getValue());
                apparatus.setComment(comment.getValue());
                apparatusService.save(apparatus);
                UI.getCurrent().getPage().reload();
                d.close();
            });

            hl.add(isWorked, comment, save);
            d.add(title, hl);
            return d;
        }

        return d;
    }
}
