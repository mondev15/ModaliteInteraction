/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyException;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saintsbe
 */
public class Creation implements Actions {

    private boolean isComplete = false;
    private Forme forme;
    private String couleur;
    
    private Point position = new Point(50,50);
    
    @Override
    public boolean isComplete() {
        return isComplete;
    }

    public Creation(){
        this.forme.setMonType(TypeForme.INDEFINI);
    }
    public Creation(String _couleur, TypeForme _forme) {
        this.couleur = _couleur;
        this.forme = new Forme(_forme);
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
   
    
    public void sendToIvy(Ivy busIvy){
        if(forme.getMonType() == TypeForme.RECTANGLE){
           
            try {
                busIvy.sendMsg("Palette:CreerRectangle x=" + this.position.getX() + ""
                        + " y=" + this.position.getY() + " couleurFond=" + this.couleur);
            } catch (IvyException ex) {
                Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            //A SUPPRIMER
                System.out.println("Palette:CreerRectangle x=" + this.position.x + ""
                        + " y=" + this.position.y + " couleurFond=" + this.couleur);
            
        } else if (forme.getMonType() == TypeForme.ELLIPSE){
           
            try {
                busIvy.sendMsg("Palette:CreerEllipse x=" + this.position.getX() + ""
                        + " y=" + this.position.y + " couleurFond=" + this.couleur);
            } catch (IvyException ex) {
                Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
            }
                
               //A SUPPRIMER
               System.out.println("Palette:CreerEllipse x=" + this.position.x + ""
                        + " y=" + this.position.y + " couleurFond=" + this.couleur);
           
        }
    }
    
    
}
