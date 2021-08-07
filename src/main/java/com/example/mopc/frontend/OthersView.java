package com.example.mopc.frontend;

import com.example.mopc.entity.Others;
import com.example.mopc.service.OthersService;
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

@Route("others")
public class OthersView extends VerticalLayout{
    private final Grid<Others> grid = new Grid<>(Others.class);
    private final OthersService othersService;
    private TextField searchField;
    private List<Others> apparatusList;
    private Authentication authentication;

    OthersView(OthersService othersService ) {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        this.othersService = othersService;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        searchField = new TextField();
        searchField.setHelperText("Введите серийный номер");
        apparatusList = new ArrayList<>();
        initView();
    }

    private void initView() {
        add(headerButton());
        add(new Text("Всё остальное"));
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
            grid.setItems(othersService.getAllMistake());
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
            apparatusList = othersService.getAllLikeSerialNumber(searchField.getValue());
            grid.setItems(apparatusList);
        });
        hl.add(searchField, searchButton);
        return hl;
    }


    private void initGrid() {
        remove(grid);

        if (apparatusList.size() == 0) {
            apparatusList = othersService.getAll();
        }
        if (apparatusList != null && apparatusList.size() != 0) {
            grid.setItems(apparatusList);
            grid.setColumns("name", "serial", "invert", "working", "comment");
            if(authentication.getName().equals("admin")) {
                grid.addColumn(new ComponentRenderer<>(item -> {
                    var changeButton = new Button("Удалить", i -> {
                        othersService.remove(item.getId());
                        UI.getCurrent().getPage().reload();
                    });
                    return new HorizontalLayout(changeButton);
                }));
            }
            grid.setSizeUndefined();
            grid.setSelectionMode(Grid.SelectionMode.NONE);
            ListDataProvider<Others> dataProvider = DataProvider.ofCollection(apparatusList);
            grid.setDataProvider(dataProvider);
            grid.addItemClickListener(event -> showDialog(event.getItem()).open());
            add(grid);
        } else {
            Notification.show("Аппратура не найдена!");
        }
    }

    private Dialog showDialog(Others app) {
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
                Others apparatus = othersService.getEntity(app.getId());
                apparatus.setWorking(isWorked.getValue());
                apparatus.setComment(comment.getValue());
                othersService.save(apparatus);
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
