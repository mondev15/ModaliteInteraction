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

    private boolean isComplete = true;
    private Forme forme;
    private String couleur;
    
    private Point position;
    
    @Override
    public boolean isComplete() {
        return isComplete;
    }

    public Creation(){
        this.forme.setMonType(TypeForme.INDEFINI);
        position = new Point(50,50);
    }
    public Creation(String _couleur, TypeForme _forme) {
        this.couleur = _couleur;
        this.forme = new Forme(_forme);
        position = new Point(50,50);
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
                busIvy.sendMsg("Palette:CreerRectangle x=" + (int)this.position.getX() + ""
                        + " y=" + (int)this.position.getY() + " couleurFond=" + this.couleur);
            } catch (IvyException ex) {
                Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } else if (forme.getMonType() == TypeForme.ELLIPSE){
           
            try {
                busIvy.sendMsg("Palette:CreerEllipse x=" + (int)this.position.getX() + ""
                        + " y=" + (int)this.position.getY() + " couleurFond=" + this.couleur);
            } catch (IvyException ex) {
                Logger.getLogger(Creation.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
    }
    
    
}
