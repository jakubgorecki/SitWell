/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SitWellFXAPACHE;
import java.awt.AWTException;
import java.util.concurrent.TimeUnit;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/**
 *
 * @author jacob
 */    
public class StaticController {
    
    public StaticTimerModel timerModel=new StaticTimerModel();
    Task<Void> task;
    long pause;
    boolean paused = false;
    boolean running = false;
    private NotificationExample n2 = new NotificationExample();
    @FXML
    private Label timerstatic;
    @FXML
    private ImageView pauseResume;
    @FXML
    private ImageView startStop;
    Image stop= new Image(getClass().getResource("icons8-stop-96.png").toExternalForm());
    Image start= new Image(getClass().getResource("icons8-play-96.png").toExternalForm());
    Image pausedimg= new Image(getClass().getResource("icons8-pause-96.png").toExternalForm());
    Image resume= new Image(getClass().getResource("icons8-resume-button-96.png").toExternalForm());
    Image settings= new Image(getClass().getResource("icons8-settings-96.png").toExternalForm());
    
    public void initialize() throws AWTException{
        n2.initnotif();
        n2.menurun();
        runTimer();
    }
    public void onClickEvent(MouseEvent mouseEvent){
        if(!running){
            task = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException, IOException {
                timerModel.restart();                         
                while(!timerModel.isTimeUp()){ 
                    timerModel.timeTick();
                    Platform.runLater(() -> {
                        startStop.setImage(stop);
                        timerstatic.setText(timerModel.getTimeString()); 
                    });
                    System.out.println(timerModel.getTimeString());         
                    TimeUnit.SECONDS.sleep(1);          
                }
                // define your applescript command
                n2.ShowSystemNotification();
                running=false;
                runTimer();
                startStop.setImage(start);
            return null;
            }
        };  
        running=true;
        new Thread(task).start();
        }
        else if(running){      
            if(running){
            task.cancel(); 
            startStop.setImage(start);
            pauseResume.setImage(pausedimg);
            timerModel.restart();
            timerstatic.setText("00:00:00");
            paused = false;   
            } 
            running = false;
        }
    }
    
    public void onClickResume(MouseEvent mouseEvent){
        if(paused){
        task = new Task<Void>(){
            @Override
            protected Void call() throws InterruptedException, IOException {
                while(!timerModel.isTimeUp()){ 
                    timerModel.timeTick();  
                    Platform.runLater(() -> {
                        pauseResume.setImage(pausedimg);                     
                        timerstatic.setText(timerModel.getTimeString());
                    });
                    System.out.println(timerModel.getTimeString());          
                    TimeUnit.SECONDS.sleep(1);          
                }
                // define your applescript command
                //timerModel.displayNotification();
                n2.ShowSystemNotification();
                running=false;  
                runTimer();
                return null;
            }
        };     
        new Thread(task).start();
        running = true;
        paused = false;
        }
        else if(running){
            pauseResume.setImage(resume);
            pause=timerModel.timeToLong();
            task.cancel();
            timerModel.setTime(pause+1);
            paused = true;
        }
    }
    public void onClickSettings(MouseEvent mouseEvent) throws IOException{
        if(running){
            task.cancel();
        }
        Parent DynamicView=FXMLLoader.load(getClass().getResource("FXML_Settings.fxml"));
        Scene tableViewScene=new Scene(DynamicView);
        //
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.setResizable(false);
        window.show();
        n2.kill();
    }
    
    public void runTimer(){
        if(!running){
            task = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException, IOException {
                timerModel.restart();                         
                while(!timerModel.isTimeUp()){ 
                    timerModel.timeTick();
                    Platform.runLater(() -> {
                        startStop.setImage(stop);
                        timerstatic.setText(timerModel.getTimeString()); 
                    });
                    System.out.println(timerModel.getTimeString());         
                    TimeUnit.SECONDS.sleep(1);          
                }
                // define your applescript command
                n2.ShowSystemNotification();
                running=false;
                startStop.setImage(start);
                runTimer();
            return null;
            }
        };  
        running=true;
        new Thread(task).start();
        }
    }

}




