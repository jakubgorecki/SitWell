/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SitWellFXAPACHE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author wirel
 */
public class StaticTimerModel {
    private int hours, minutes, seconds;
  
    public StaticTimerModel(){
        restart();
    }
    public void stop(){
        seconds=0;
        minutes=0;
        seconds=0;
    }

    public String getTimeString(){
	String time;
	if(hours < 10)
            time = "0" + hours;
	else
            time = String.valueOf(hours);
        
	if(minutes < 10)
            time += ":" + "0" + minutes;
	else
            time += ":" + minutes;
        
	if(seconds < 10)
            time += ":" + "0" + seconds;
	else
            time += ":" + seconds;
		
	return time;
    }
      
    public void reset(){
        hours = 0;
        minutes = 30;
        seconds = 0;
    }
    
    public void restart(){
        setTime(loadTimeValue());
    }
    
    public long timeToLong(){
        return  hours * 3600 + minutes * 60 + seconds;
    }
    public void setTime(long t){
        hours = (int)(t / 3600);
        minutes = (int)((t % 3600)/60);       
        seconds = (int)((t % 3600)%60);
        
    }
    public void setTime(String time){
        hours = Integer.parseInt(time.substring(0, 2));
	minutes = Integer.parseInt(time.substring(3, 5));
	seconds = Integer.parseInt(time.substring(6, 8));
				
    }
    public void saveTime(long t){
        hours = (int)(t / 3600);
        minutes = (int)((t % 3600)/60);       
        seconds = (int)((t % 3600)%60);
        
        storeTimeValue(getTimeString());
    }
    public void saveTime(String time){
        hours = Integer.parseInt(time.substring(0, 2));
	minutes = Integer.parseInt(time.substring(3, 5));
	seconds = Integer.parseInt(time.substring(6, 8));
		
	storeTimeValue(time);		
    }
    
    private void storeTimeValue(String time) {		
	try{
            BufferedWriter fileWriter = new BufferedWriter( new FileWriter("static.txt"));
            fileWriter.write(time);
            fileWriter.newLine();
            fileWriter.close();
        } 
	catch (IOException e){
            e.printStackTrace();
	}	
    }
	
    private String loadTimeValue(){
        String result = "00:15:00";
        try{
            BufferedReader fileReader = new BufferedReader( new FileReader("static.txt"));
            result = fileReader.readLine();
            fileReader.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } 
        catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean isTimeUp(){
	if (hours == 0 && minutes == 0 && seconds == 0)
            return true;
	else
            return false;
    }
    
    public void timeTick(){
        if(!isTimeUp()){
            if(seconds != 0)
                seconds--;
            else if (minutes != 0){
		minutes--;
		seconds = 59;
            }
            else{
                hours--;
                minutes = 59;
                seconds = 59;
            }
        }
    }    
}
