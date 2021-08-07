package com.example.mopc.frontend;

import com.example.mopc.entity.Infusomat;
import com.example.mopc.service.InfusomatService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@Route("infusomat")
public class InfusomatView extends VerticalLayout {
    private final Grid<Infusomat> grid = new Grid<>(Infusomat.class);
    private final InfusomatService infusomatService;
    private TextField searchField;
    private List<Infusomat> apparatusList;
    private Authentication authentication;

    InfusomatView(InfusomatService infusomatService) {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        this.infusomatService = infusomatService;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        searchField = new TextField();
        searchField.setHelperText("Введите серийный номер");
        apparatusList = new ArrayList<>();
        initView();
    }

    private void initView() {
        add(headerButton());
        add(new Text("Инфузоматы"));
        add(searchComponent());
        initGrid();
    }

    private HorizontalLayout headerButton() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setAlignItems(FlexComponent.Alignment.START);
        Button main = new Button("Главная", item -> {
            UI.getCurrent().navigate("main");
        });
        hl.add(main);
        HorizontalLayout vl = new HorizontalLayout();
        hl.setAlignItems(FlexComponent.Alignment.END);
        Button mistake = new Button("Сломанные", item -> {
            grid.setItems(infusomatService.getAllMistake());
        });
        vl.add(mistake);
        return new HorizontalLayout(hl, vl);

    }

    private HorizontalLayout searchComponent() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setAlignItems(FlexComponent.Alignment.START);
        searchField.focus();
        Button searchButton = new Button("Поиск", item -> {
            if (searchField.getValue().equals("")) {
                Notification.show("Введите номер");
            }
            apparatusList = infusomatService.getAllLikeSerialNumber(searchField.getValue());
            grid.setItems(apparatusList);
        });
        hl.add(searchField, searchButton);
        return hl;
    }


    private void initGrid() {
        remove(grid);

        if (apparatusList.size() == 0) {
            apparatusList = infusomatService.getAll();
        }
        if (apparatusList != null && apparatusList.size() != 0) {
            grid.setItems(apparatusList);
            grid.setColumns("name", "serial", "invert", "working", "comment");
            if(authentication.getName().equals("admin")) {
                grid.addColumn(new ComponentRenderer<>(item -> {
                    var changeButton = new Button("Удалить", i -> {
                        infusomatService.remove(item.getId());
                        UI.getCurrent().getPage().reload();
                    });
                    return new HorizontalLayout(changeButton);
                }));
            }
            grid.setSizeUndefined();
            grid.setSelectionMode(Grid.SelectionMode.NONE);
            ListDataProvider<Infusomat> dataProvider = DataProvider.ofCollection(apparatusList);
            grid.setDataProvider(dataProvider);
            grid.addItemClickListener(event -> showDialog(event.getItem()).open());
            add(grid);
        } else {
            Notification.show("Аппратура не найдена!");
        }
    }

    private Dialog showDialog(Infusomat app) {
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
                Infusomat apparatus = infusomatService.getEntity(app.getId());
                apparatus.setWorking(isWorked.getValue());
                apparatus.setComment(comment.getValue());
                infusomatService.save(apparatus);
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
