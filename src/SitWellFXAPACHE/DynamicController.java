package SitWellFXAPACHE;

import java.awt.AWTException;
import javafx.scene.image.Image ;
import java.io.IOException;
import java.util.concurrent.TimeUnit; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DynamicController {

    public TimerModel timerModel=new TimerModel();
    public Record record = new Record();
    public NotificationExample n2=new NotificationExample();
    Task<Void> dTask;
    Task<Void> notifTask=new Task<Void>(){
            @Override
            protected Void call() throws InterruptedException, IOException {
                int time=0;
                while(time<30){ 
                    if(n2.getInf()!=null){
                        time=30;  
                    }
                    TimeUnit.SECONDS.sleep(1);  
                    time++;
                }
        if(n2.getInf()=="yes"){
            timerModel.shortenTime();
        }
        else if(n2.getInf()=="no"){
            timerModel.extendTime();
        }
        else
        {
            n2.close();
            
        }    
        n2.setInf(null);
        runtimer();
        return null;
            }
        };
    long pause=100;
    boolean paused = false;
    boolean running = false;

    @FXML
    private Label timer;
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
        //System.out.println(n2.test());
        //if(n2.test()==0){
        n2.initnotif();
        n2.menurun();
        runtimer();
        //}
        //System.out.println(n2.test());
    }
    public void onClickEvent(MouseEvent mouseEvent){    
        runtimer();
    }
    
    public void onClickResume(MouseEvent mouseEvent){
        if(paused){
        dTask = new Task<Void>(){
            @Override
            protected Void call() throws InterruptedException, IOException {
                while(!timerModel.isTimeUp()){ 
                    timerModel.timeTick();  
                    Platform.runLater(() -> {
                        pauseResume.setImage(pausedimg);
                        timer.setText(timerModel.getTimeString());
                    });
                    System.out.println(timerModel.getTimeString());          
                    TimeUnit.SECONDS.sleep(1);          
                }
                show();
                running=false;
                return null;
            }
        };     
        new Thread(dTask).start();
        running = true;
        paused = false;
        }
        else if(running){
            pauseResume.setImage(resume);
            pause=timerModel.timeToLong();
            dTask.cancel();
            timerModel.setTime(pause+1);
            paused = true;
        }
    }
    public void onClickSettings(MouseEvent mouseEvent) throws IOException{
        if(running)
            dTask.cancel();
        if(notifTask.isRunning())
            notifTask.cancel();
        n2.kill();
        Parent DynamicView=FXMLLoader.load(getClass().getResource("FXML_Settings.fxml"));
        Scene tableViewScene=new Scene(DynamicView);
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.setResizable(false);
        window.show();
    }
    public void runtimer(){
        if(!running){
            dTask = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException, IOException {
                timerModel.restart();    
                while(!timerModel.isTimeUp()){ 
                    timerModel.timeTick();
                    Platform.runLater(() -> {
                        startStop.setImage(stop);
                        timer.setText(timerModel.getTimeString());
                    });
                    System.out.println(timerModel.getTimeString());         
                    TimeUnit.SECONDS.sleep(1);          
                }
            running=false;
            startStop.setImage(start);
            show();
            return null;
            }     
        };   
        running = true;
        new Thread(dTask).start();
        }
        else if(running){      
            dTask.cancel();  
            startStop.setImage(start);
            pauseResume.setImage(pausedimg);
            timer.setText("00:00:00");
            timerModel.restart();
            paused = false;   
            running = false;
        }
    }
        public void show(){
            n2.menunotif();
            n2.ShowSystemNotification();
            notifTask=new Task<Void>(){
            @Override
            protected Void call() throws InterruptedException, IOException {
                int time=0;
                while(time<30){ 
                    if(n2.getInf()!=null){
                        time=30;  
                    }
                    TimeUnit.SECONDS.sleep(1);  
                    time++;
                }
        if(n2.getInf()=="yes"){
            timerModel.shortenTime();
        }
        else if(n2.getInf()=="no"){
            timerModel.extendTime();
        }
        else
        {
            n2.close();
            try{
                n2.initnotif();
            } 
            catch (AWTException ex) {
                Logger.getLogger(DynamicController.class.getName()).log(Level.SEVERE, null, ex);
            }
            n2.menurun();
            
        }    
        n2.setInf(null);
        runtimer();
        return null;
            }
        };
            new Thread(notifTask).start();
}
}