/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busivytp1;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mundial
 */
public class BusIVYTP1 {

    private Ivy bus; 
    
    /**
     * 
     * @param nom nom de l'agent
     * @param premierMessage premier message envoyé par l'agent lorqu'il  est connecté
     * @param listener permet de prendre connaissance des autres agents présents sur le bus
     */
    public BusIVYTP1(String nom, String premierMessage, IvyApplicationListener listener){   
            bus = new Ivy(nom, premierMessage,listener);
    }
    
    //---connection
    public void start(String adresse){
        try {
            bus.start(adresse);
        } catch (IvyException ex) {
            Logger.getLogger(BusIVYTP1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //--- envoyer un message au bus
    public void sendMessage(String message){
        
        try {
            bus.sendMsg(message);
        } catch (IvyException ex) {
            Logger.getLogger(BusIVYTP1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void receiveMessage(){
    }
    
    // abonnement au type de message
    public void bindMessage(String message,IvyMessageListener listener ){
        try {
            bus.bindAsyncMsg(message, listener);
        } catch (IvyException ex) {
            Logger.getLogger(BusIVYTP1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
