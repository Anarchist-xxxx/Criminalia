package app;

import dao.DAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Thread5ch;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FixPanelController implements Initializable {

    DAO dao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = new DAO();
    }

    @FXML
    public void buttonFixThreadEndAction(ActionEvent event) {
        if(!(alertNowClawling())) {
            fixThreadEnd();
            doneAlert();
        }
    }

    private void fixThreadEnd() {
        ArrayList<Thread5ch> threadList = dao.getThreadList();

        for(Thread5ch tmp: threadList) {
            tmp.setEnd(dao.getThreadEnd(tmp));
            dao.insertEnd(tmp);
        }

        /*
            プログレスバーが未実装だよ
         */
    }

    @FXML
    public void buttonFixThreadStartTimeAndEndTimeAction(ActionEvent event) {
        if(!(alertNowClawling())) {
            fixThreadStartTimeAndEndTime();
            doneAlert();
        }
    }

    private void fixThreadStartTimeAndEndTime() {
        ArrayList<Thread5ch> threadList = dao.getThreadList();

        for(Thread5ch tmp: threadList) {
            String key = tmp.getKey();
            dao.updateThreadStartTimeAndEndTime(key, dao.getThreadStartTime(key), dao.getThreadEndTime(key));

            System.out.println(tmp.getTitle());
        }
    }

    private boolean alertNowClawling() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "PandoLiA、Clawliaは終了しましたか？", ButtonType.YES, ButtonType.NO);
        alert.setTitle("注意！");

        ButtonType ans = alert.showAndWait().orElse(ButtonType.NO);

        return ans.getButtonData().isCancelButton();
    }

    private void doneAlert() {
        Alert done = new Alert(Alert.AlertType.NONE, "Done!", ButtonType.APPLY);
        done.setTitle("Done!");

        done.showAndWait().orElse(ButtonType.NO);
    }
}
