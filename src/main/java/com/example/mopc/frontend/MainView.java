package com.example.mopc.frontend;

import com.example.mopc.entity.Apparatus;
import com.example.mopc.entity.Cuveses;
import com.example.mopc.entity.Infusomat;
import com.example.mopc.entity.Others;
import com.example.mopc.repository.ApparatusRepository;
import com.example.mopc.repository.CuvesesRepository;
import com.example.mopc.repository.InfusomatRepository;
import com.example.mopc.repository.OthersRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("main")
public class MainView extends VerticalLayout {
    private final FormLayout fl = new FormLayout();
    private Text headName;
    private ComboBox<String> labelComboBox;
    private TextField name;
    private TextField serialNumber;
    private TextField invertedNumber;
    private ComboBox<String> isWorked;
    private TextArea comment;
    private ApparatusRepository apparatusService;
    private CuvesesRepository cuvesesService;
    private InfusomatRepository infusomatService;
    private OthersRepository othersService;

    private Authentication authentication;
    MainView(ApparatusRepository apparatusService, CuvesesRepository cuvesesService,
             InfusomatRepository infusomatService, OthersRepository othersService) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        this.apparatusService = apparatusService;
        this.cuvesesService = cuvesesService;
        this.infusomatService = infusomatService;
        this.othersService = othersService;

        headName = new Text("Аппаратура ОРИТН");
        labelComboBox = new ComboBox<>();
        labelComboBox.setItems("Дыхательная аппаратура", "Системы и Кювезы", "Инфузоматы", "Остальное");
        name = new TextField();
        serialNumber = new TextField();
        invertedNumber = new TextField();
        isWorked = new ComboBox<>();
        isWorked.setItems("Исправен", "Не исправен");
        comment = new TextArea("Комментарии");
        comment.setMinWidth(310f, Unit.PIXELS);
        comment.setMinHeight(150f, Unit.PIXELS);
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        initView();
    }

    private void initView() {
        add(headName);
        add(initLink());
        if(authentication.getName().equals("admin")) {
            initFormLayout();
            add(initSaveButtonAndComment());
        }
    }

    private void initFormLayout() {
        fl.addFormItem(labelComboBox, "Тип аппаратуры");
        fl.addFormItem(name, "Наименование аппаратуры");
        fl.addFormItem(serialNumber, "Серийный номер");
        fl.addFormItem(invertedNumber, "Инвертарный номер");
        fl.addFormItem(isWorked, "Состояние");
        add(fl);
    }

    private HorizontalLayout initLink() {
        HorizontalLayout hl = new HorizontalLayout();
        Button buttonApparatus = new Button("Аппараты ИВЛ и SiPap", item -> {
            new ApparatusView(apparatusService, authentication, "Аппараты ИВЛ и SiPap");
            UI.getCurrent().navigate("apparatus");
        });

        Button buttonCuveses = new Button("Кувезы и Системы", item -> {
            new CuvesesView(cuvesesService,authentication, "Кувезы и Системы");
            UI.getCurrent().navigate("cuveses");
        });

        Button buttonInfus = new Button("Инфузоматы", item -> {
            new InfusomatView(infusomatService, authentication, "Инфузоматы");
            UI.getCurrent().navigate("infusomat");
        });

        Button buttonOthers = new Button("Другое", item -> {
            new OthersView(othersService, authentication, "Остальное");
            UI.getCurrent().navigate("others");
        });

        hl.add(buttonApparatus, buttonCuveses, buttonInfus, buttonOthers);

        return hl;
    }

    private VerticalLayout initSaveButtonAndComment() {
        VerticalLayout hl = new VerticalLayout();
        hl.setDefaultHorizontalComponentAlignment(Alignment.START);
        hl.setJustifyContentMode(JustifyContentMode.START);
        Button saveButton =  new Button("Сохранить", item -> {
            if(labelComboBox.getValue() == null) {
                Notification.show("Выбирите тип аппаратуры!");
                return;
            }
            if(serialNumber.isEmpty()) {
                Notification.show("Введите серийный номер");
                return;
            }

            switch (labelComboBox.getValue()) {
                case "Дыхательная аппаратура" : apparatusService.save(new Apparatus(name.getValue(), serialNumber.getValue(),
                        invertedNumber.getValue(), isWorked.getValue(), comment.getEmptyValue())); break;
                case "Системы и Кювезы" : cuvesesService.save(new Cuveses(name.getValue(), serialNumber.getValue(),
                        invertedNumber.getValue(), isWorked.getValue(), comment.getEmptyValue())); break;
                case "Инфузоматы" : infusomatService.save(new Infusomat(name.getValue(), serialNumber.getValue(),
                        invertedNumber.getValue(), isWorked.getValue(), comment.getEmptyValue())); break;
                case "стальное" : othersService.save(new Others(name.getValue(), serialNumber.getValue(),
                        invertedNumber.getValue(), isWorked.getValue(), comment.getEmptyValue())); break;
            }

            clearField();
            Notification.show("Аппратура добавлена");
        });
        hl.add(comment, saveButton);
        return hl;
    }

    private void clearField() {
        name.clear();
        serialNumber.clear();
        invertedNumber.clear();
        comment.clear();
        labelComboBox.clear();
        isWorked.clear();
    }
}
