package SitWellFXAPACHE;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NotificationExample {
        Object inf;
        PopupMenu popupMenu = new PopupMenu();
        MenuItem Menu = new MenuItem("Menu");
        MenuItem exit = new MenuItem("Exit");
        MenuItem Yes = new MenuItem("Yes");
        MenuItem No = new MenuItem("No");
        SystemTray tray = SystemTray.getSystemTray();
        private Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));
        private TrayIcon trayIcon= new TrayIcon(image, "Tray Demo");

    public void ShowSystemNotification() {
                    trayIcon.displayMessage("Time's up!", "Correct your posture!",
                    TrayIcon.MessageType.INFO); 
    }
    public int test(){
       int s=tray.getTrayIcons().length;
        return s;
    }
 
    public void initnotif() throws AWTException {
        if (!SystemTray.isSupported())
            return;
        
        tray.add(trayIcon);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Sitwell");
        System.out.println("initnotifdone");
    }
    public void menunotif(){ 
        popupMenu.add(Yes);
        popupMenu.add(No);
        popupMenu.addSeparator();
        popupMenu.add(Menu);
        popupMenu.add(exit);
        
        exit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        Yes.addActionListener((ActionEvent e) -> {
            inf="yes";
            menurun();
        });
        Menu.addActionListener((ActionEvent e) -> {
            try {
                Parent DynamicView=FXMLLoader.load(getClass().getResource("FXML_Settings.fxml"));
                Scene tableViewScene=new Scene(DynamicView);
                Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.setResizable(false);
                window.show();
                System.out.println("menu");
            } catch (IOException ex) {
                Logger.getLogger(NotificationExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        No.addActionListener((ActionEvent e) -> {
            inf="no";
            menurun();
        });        
    }
    public void menurun(){
        if(trayIcon.getPopupMenu()!=null){
        trayIcon.getPopupMenu().removeAll();
        }    
        popupMenu = new PopupMenu();
        popupMenu.add(Menu);
        popupMenu.addSeparator();
        popupMenu.add(exit);
        
        exit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        Menu.addActionListener((ActionEvent e) -> {
            try {
                Parent DynamicView=FXMLLoader.load(getClass().getResource("FXML_Settings.fxml"));
                Scene tableViewScene=new Scene(DynamicView);
                Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.setResizable(false);
                window.show();
                System.out.println("menu");
            } catch (IOException ex) {
                Logger.getLogger(NotificationExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        trayIcon.setPopupMenu(popupMenu);        
    }
    
    public Object getInf(){
        return inf;
    }
    public void setInf(Object inf){
        this.inf=inf;
    }

    public void close() {    
           if (trayIcon != null) {
                System.out.println(trayIcon);
                System.out.println("tt");
                tray.remove(trayIcon);
    }
           else{
               System.out.println(trayIcon);
               System.out.println("null jet");
    }
    }
    
    public void kill(){
        tray.remove(trayIcon);
    }
        
}