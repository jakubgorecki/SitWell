/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SitWellFXAPACHE;


import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 *
 * @author jacob
 */
public class StartupController1 {

    public void onClickBack(MouseEvent mouseEvent) throws IOException{

        Parent DynamicView=FXMLLoader.load(getClass().getResource("FXML_Startup2.fxml"));
        Scene tableViewScene=new Scene(DynamicView);
        
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.setResizable(false);
        window.show();
        }
        
}

