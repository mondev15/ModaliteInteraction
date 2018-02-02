/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saintsbe
 */
public class Suppression implements Actions {
    
    private boolean isComplete = false;
    private ArrayList<Forme> listeFormeSurPalette = new ArrayList<Forme>();
    
    public Suppression() {
        
    }
   
 
    @Override
    public boolean isComplete() {
        isComplete = (!listeFormeSurPalette.isEmpty()) && (listeFormeSurPalette != null);
        return isComplete;
    }
    
    
    public void sendToIvy(Ivy busIvy){
        try {
            busIvy.sendMsg("Palette:SupprimerObjet nom=" + this.listeFormeSurPalette.get(0).getNom());
        } catch (IvyException ex) {
            Logger.getLogger(Suppression.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //A SUPPRIMER
            System.out.println("Palette:SupprimerObjet nom=" + this.listeFormeSurPalette.get(0).getNom());
    }
    
    
    
    
     public void filter(Point target, Ivy busIvy) {
         
      
        
         
        try {
            
            //ON RECUPERE LE NOM DE LA FORME
            busIvy.bindMsg("Palette:ResultatTesterPoint x=" + target.getX() + " y=" + target.getY() + " nom=(.*)", new IvyMessageListener(){
                @Override
                public void receive(IvyClient client, String[] args) {  
                   
                    try {
                        busIvy.sendMsg("Palette:DemanderInfo nom=" + args[0]);
                    } catch (IvyException ex) {
                        Logger.getLogger(Suppression.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }
            });
                
            //DEMANDE D'INFO SUR LA FORME
            busIvy.bindMsg("Palette:Info nom=(.*) x=(.*) y=(.*) longueur=(.*) "
                    + "hauteur=(.*) couleurFond=(.*) couleurContour=(.*)", new IvyMessageListener(){
                @Override
                public void receive(IvyClient client, String[] args) {  
                 
                    //IDENTIFICATION DE LA FORME
                    //WORK IN PROGRESS
                   
            
            
                }
                

                
            });
            } catch (IvyException ex) {
            Logger.getLogger(Suppression.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

                
}
        
