package com.ippon.kata.tetris.gaming.primary.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class BoardController {
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private VBox dataContainer;
    @FXML
    private TableView tableView;

    @FXML
    private void initialize() {
        // search panel
        searchButton.setText("Search");
        searchButton.setStyle("-fx-background-color: #457ecd; -fx-text-fill: #ffffff;");
    }
}
