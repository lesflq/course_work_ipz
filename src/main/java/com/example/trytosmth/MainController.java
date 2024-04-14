package com.example.trytosmth;

import com.example.trytosmth.model.FireExtinguisherData;
import com.example.trytosmth.service.FireExtinguisherService;
import javafx.scene.control.Button;


public class MainController {
    private final Button checkButton = new Button("Перевірити термін придатності");
    private final Button replaceButton = new Button("Замінити");
    private final Button addButton = new Button("Додати");
    private FireExtinguisherService model;
    private Main view;

    public MainController(FireExtinguisherService model, Main view) {
        this.model = model;
        this.view = view;
    }

}