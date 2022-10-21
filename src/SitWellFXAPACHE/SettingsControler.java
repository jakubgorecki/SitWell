/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SitWellFXAPACHE;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author jacob
 */
public class SettingsControler {
    @FXML
    private ImageView button;
    @FXML
    private TextField textfield;
    @FXML
    private ImageView minutes;
    Image staticb= new Image(getClass().getResource("button_switch-to-static-timer.png").toExternalForm());
    Image dynamicb= new Image(getClass().getResource("button_switch-to-dynamic-timer.png").toExternalForm());
    public NotificationExample n=new NotificationExample();
    public void initialize() throws AWTException{
        initslider();
        textfield();
        n.initnotif();
        n.menurun();
    }
       
    public void isStatic(MouseEvent mouseEvent){
        
        if(button.getImage().equals(dynamicb)){
            try{
                BufferedWriter fileWriter = new BufferedWriter( new FileWriter("type.txt"));
                fileWriter.write("dynamic");
                fileWriter.newLine();
                fileWriter.close();
            } 
            catch (IOException e){
                e.printStackTrace();
            } 
            minutes.setVisible(false);
            textfield.setVisible(false);
            button.setImage(staticb);
        }
        else if(button.getImage().equals(staticb)){
            try{
                BufferedWriter fileWriter = new BufferedWriter( new FileWriter("type.txt"));
                fileWriter.write("static");
                fileWriter.newLine();
                fileWriter.close();
            } 
            catch (IOException e){
                e.printStackTrace();
            } 
            minutes.setVisible(true);
            textfield.setVisible(true);
            button.setImage(dynamicb);
        }
        
    }
    
    public void onResetDynamic(MouseEvent mouseEvent) throws IOException{
        try{
                BufferedWriter fileWriter = new BufferedWriter( new FileWriter("dynamic.txt"));
                fileWriter.write("00:15:00");
                fileWriter.newLine();
                fileWriter.close();
            } 
            catch (IOException e){
                e.printStackTrace();
            } 
        System.out.println("Reset performed.");
    }
    
    public void onClickBack(MouseEvent mouseEvent) throws IOException{
        n.kill();
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
        if(result.equals("static")){
        Parent DynamicView=FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene tableViewScene=new Scene(DynamicView);
        //
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.setResizable(false);
        window.show();
        }
        else if(result.equals("dynamic")){
        Parent DynamicView=FXMLLoader.load(getClass().getResource("FXML_Dynamic.fxml"));
        Scene tableViewScene=new Scene(DynamicView);
        //
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.setResizable(false);
        window.show(); 
        }      
}
        public void textfield(){
        try{
            BufferedReader fileReader = new BufferedReader( new FileReader("static.txt"));
            String input = fileReader.readLine();
            fileReader.close();
            int output = 60 * Integer.parseInt(input.substring(0, 2)) + Integer.parseInt(input.substring(3, 5));
            textfield.setPromptText(Integer.toString(output));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } 
        catch (IOException e){
            e.printStackTrace();
        }    
        
        textfield.setTextFormatter(new TextFormatter<>((Change change) -> {
        String newText = change.getControlNewText();
        if (newText.length()>3) {
            return null ;
        } 
        else if (change.getText().matches("[a-zA-Z]+")){
            return null ;
        }
        else{
            //default wartosc co sie wyswietla na szaro; bedzie to trzeba loadowac
            String result="00:15:00";
            
            switch (newText.length()) { 
                
                case 1:{
                    result="00:0"+newText+":00";
                    try{
                        BufferedWriter fileWriter = new BufferedWriter( new FileWriter("static.txt"));
                        fileWriter.write(result);
                        fileWriter.newLine();
                        fileWriter.close();
                    } 
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println(result);
                    break;
                }   
                
                case 2:{   
                    int minutes=Integer.parseInt(newText);
                    if (minutes>59){
                        minutes = minutes%60;
                        if (minutes<10){
                        result="01:0"+minutes+":00";
                        }
                        else if (minutes>9){
                        result="01:"+minutes+":00";
                        }
                    }
                    else if (minutes<60){
                        result="00:"+newText+":00";                       
                    }
                    try{
                        BufferedWriter fileWriter = new BufferedWriter( new FileWriter("static.txt"));
                        fileWriter.write(result);
                        fileWriter.newLine();
                        fileWriter.close();
                    } 
                    catch (IOException e){
                            e.printStackTrace();
                    } 
                    System.out.println(result);
                    break;
                }
                
                case 3:{
                    int minutes=Integer.parseInt(newText);
                    if (minutes>60){
                        int hours = minutes/60;
                        minutes = minutes%60;
                        if (hours>9){
                            if (minutes<10){
                            result=hours+":0"+minutes+":00";
                            }
                            else if (minutes>9){
                            result=hours+":"+minutes+":00";
                            }
                        }
                        else if (hours<10){
                            if (minutes<10) {
                            result="0"+hours+":0"+minutes+":00";
                            }
                            else if (minutes>9){
                            result="0"+hours+":"+minutes+":00";
                            }
                        }
                    }   
                    try{
                        BufferedWriter fileWriter = new BufferedWriter( new FileWriter("static.txt"));
                        fileWriter.write(result);
                        fileWriter.newLine();
                        fileWriter.close();
                    } 
                    catch (IOException e){
                        e.printStackTrace();
                    }   
                }

                default:
                    break;
            }
            return change;         
        }       
        }
        ));  
        
        }
        
        public void initslider(){
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
            if(result.equals("static")){
                button.setImage(dynamicb);
                textfield.setVisible(true);
                minutes.setVisible(true);
            }
            else if(result.equals("dynamic")){
                button.setImage(staticb);
                textfield.setVisible(false);
                minutes.setVisible(false);
            }
        }
}
