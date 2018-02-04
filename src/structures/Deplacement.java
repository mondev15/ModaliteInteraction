/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyException;
import java.awt.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saintsbe
 */
public class Deplacement implements Actions{

    private boolean isComplete = false;
    private ArrayList<Forme> listeFormeSurPalette = new ArrayList<Forme>();
    private Point position;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    
    @Override
    public boolean isComplete() {
        isComplete = (!listeFormeSurPalette.isEmpty()) && (listeFormeSurPalette != null);
        return isComplete;
    }

    @Override
    public void sendToIvy(Ivy busIvy) {
     
        try {
            busIvy.sendMsg("Palette:DeplacerObjetAbsolu nom=" + this.listeFormeSurPalette.get(0).getNom() +" x="+ (int)this.position.getX()+" y="+(int)this.position.getY());
        } catch (IvyException ex) {
            Logger.getLogger(Deplacement.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        //A SUPPRIMER
            System.out.println("Palette:DeplacerObjet nom=" + this.listeFormeSurPalette.get(0).getNom());
    }
    
     public void filter(Ivy busIvy, Point p) {

        try {
            busIvy.bindMsg("Palette:ResultatTesterPoint x=" + (int)p.getX() + " y=" + (int)p.getY() + " nom=(.*)", (client, args) -> {
               
                try {
                    busIvy.sendMsg("Palette:DemanderInfo nom=" + args[0]);
                } catch (IvyException ex) {
                    Logger.getLogger(Deplacement.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
        } catch (IvyException ex) {
            Logger.getLogger(Deplacement.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            busIvy.bindMsg("Palette:Info nom=(.*) x=(.*) y=(.*) longueur=(.*) "
                    + "hauteur=(.*) couleurFond=(.*) couleurContour=(.*)", (client, args) -> {
                        Forme forme = new Forme(args[0],
                        new Point(Integer.parseInt(args[1]),
                                    Integer.parseInt(args[2])));
                        forme.setCouleur(args[5]);
                        
                        if(args[0].startsWith("Rectangle")){
                            forme.setMonType(TypeForme.RECTANGLE);
                        }else if(args[0].startsWith("Ellipse")){
                            forme.setMonType(TypeForme.ELLIPSE);
                        }
                        listeFormeSurPalette.add(forme);
                        
                    });
        } catch (IvyException ex) {
            Logger.getLogger(Deplacement.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            busIvy.sendMsg("Palette:TesterPoint x=" + p.getX() + " y=" + p.getY());
        } catch (IvyException ex) {
            Logger.getLogger(Deplacement.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    public void filter(TypeForme type) {
        Iterator<Forme> it = listeFormeSurPalette.iterator();
        while(it.hasNext()){
            Forme f = it.next();
            if(f.getMonType() != type){
                it.remove();
            }
        }
    }

    public void filter(String color) {
        Iterator<Forme> it = listeFormeSurPalette.iterator();
        while(it.hasNext()){
            Forme f = it.next();
            if(f.getCouleur() != color){
                it.remove();
            }
        }
    }
    
    
}
