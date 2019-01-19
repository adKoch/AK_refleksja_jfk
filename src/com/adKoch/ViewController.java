package com.adKoch;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML()
    private Button loadButton;

    @FXML
    private Button evaluateButton;

    @FXML
    private GridPane viewGridPane;

    @FXML
    private ListView methodListView;

    @FXML
    private TextField arg1Field;

    @FXML
    private TextField arg2Field;

    @FXML
    private TextField evaluationField;

    private Invoker invoker;

    private int argCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadButton.setOnAction(this::chooseFile);
        evaluateButton.setOnAction(this::evaluate);
        evaluationField.setDisable(true);
        argCount=0;
        disableNotUsed();
    }

    private void evaluate(ActionEvent actionEvent){
        String arg1 = arg1Field.getText();
        String arg2 = arg2Field.getText();

        int index = methodListView.getSelectionModel().getSelectedIndex();

        String result = null;
        try {
            result = invoker.invoke(index,arg1,arg2);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (result == null) evaluationField.setText("ERROR");
            else evaluationField.setText(result);
        }

    }
    private void chooseFile(ActionEvent actionEvent){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(Paths.get("").toAbsolutePath().toFile());

        Stage stage = (Stage) viewGridPane.getScene().getWindow();
        File f = directoryChooser.showDialog(stage);
        if(null != f){
            invoker = null;
            invoker = new Invoker(f);
            setMethodListView();
        }
    }

    private void setMethodListView(){
        ObservableList<String> items = FXCollections.observableArrayList(invoker.getMethodNames());
        methodListView.setItems(items);
        methodListView.getSelectionModel().clearSelection();

        methodListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                arg1Field.clear();
                arg2Field.clear();
                argCount = invoker.getParamCountForItem(methodListView.getSelectionModel().getSelectedIndex());
                disableNotUsed();
            }
        });
    }

    private void disableNotUsed(){
        if(argCount==0){
            arg1Field.setDisable(true);
            arg2Field.setDisable(true);
            return;
        } else if(argCount==1){
            arg1Field.setDisable(false);
            arg2Field.setDisable(true);
            return;
        }
        arg1Field.setDisable(false);
        arg2Field.setDisable(false);
    }

}

