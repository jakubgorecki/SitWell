/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SitWellFXAPACHE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 *
 */
public class SitWellFXGUI extends Application {
    @FXML Label label;
    @Override
    public void start(Stage stage) throws Exception {
        
        try{
            BufferedReader fileReader = new BufferedReader( new FileReader("launched.txt"));
            String temp = fileReader.readLine();
            fileReader.close();
        }
        catch (FileNotFoundException e){
            Parent root = FXMLLoader.load(getClass().getResource("FXML_Startup1.fxml"));
        
            Scene scene = new Scene(root);
        
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        String result="";
        try{
            BufferedReader fileReader = new BufferedReader( new FileReader("type.txt"));
            result = fileReader.readLine();
            fileReader.close();
            System.out.println(result);
            
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } 
        catch (IOException e){
            e.printStackTrace();
        }
        if(result.equals("static"))
        {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        }
        else if(result.equals("dynamic"))
        {
        Parent root = FXMLLoader.load(getClass().getResource("FXML_Dynamic.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();  
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

}