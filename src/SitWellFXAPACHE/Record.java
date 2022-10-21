/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SitWellFXAPACHE;

/**
 *
 * @author wirel
 */
public class Record {
    private int total, totalYes, totalNo, yesStreak, noStreak;
    
    public Record(){
        total = 0;
        totalYes = 0;
        totalNo = 0;
        yesStreak = 0;
        noStreak = 0;
    }
    
    public int getTotal(){
        return total;
    }
    public int getTotalYes(){
        return totalYes;
    }
    public int getTotalNo(){
        return totalNo;
    }
    public int getYesStreak(){
        return yesStreak;
    }
    public int getNoStreak(){
        return noStreak;
    }
    
    public void addYes(){
        totalYes += 1;
        total += 1;
        yesStreak += 1;
        if (noStreak != 0){noStreak = 0;}
    }
    public void addNo(){
        totalNo += 1;
        total += 1;
        noStreak += 1;
        if (yesStreak != 0){yesStreak = 0;}
    }
    
    public void reset(){
        total = 0;
        totalYes = 0;
        totalNo = 0;
        yesStreak = 0;
        noStreak = 0;
    }
    
    public String toString(){
        String output = "";
        output += "Total       = "+total+"\n"
                + "Yes         = "+totalYes+"\n"
                + "No          = "+totalNo+"\n";
        if (yesStreak != 0)
            output += 
                  "Yes Streak  = "+yesStreak;
        else
            output += 
                  "No Streak   = "+noStreak;
        return output;
    }
}
