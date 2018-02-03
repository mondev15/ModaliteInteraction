/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reco;

import structures.TypeForme;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import structures.Actions;
import structures.Couleur;
import structures.Creation;
import structures.LanguageVocal;
import structures.Suppression;
import structures.TypeActions;


/**
 *
 * @author benjamin.saint-sever
 */
public class Automate {
    
    
   
    
    enum State{INIT, OPTION, CLIC, POSITION};
    private State state;
    
    private TypeForme forme;
    private Actions action;
    
    private Couleur couleur;
    
    enum VOCALPOS{ICI, LA, ACETTEPOSITION};
    private Point2D position;
    
    private Timer actionTimeOut;
    private Timer timerPointing;
    private int delayTimer = 2000;
    
    private final int TAUXRECOMIN = 79;
    
  
    private int _X = 20;
    private int _Y = 70;
    private int _WIDTH = 150;
    private int _HEIGHT = 150;
    
    private Ivy busIvy;
    
     public void genererForme(){
        // Creation.sendIvy
    }
    
     
   
    
    public Automate(){
        state = State.INIT;
        couleur = Couleur.ROUGE;
        forme = TypeForme.RECTANGLE;
        position = new Point2D.Double(0,0);
        IvyApplicationListener listener = null;
        busIvy = new Ivy("FusionMultimodale","",listener);
        
        actionTimeOut = new javax.swing.Timer(delayTimer, (ActionEvent e) -> {
            genererForme();
            state = State.INIT;
        });
        
        timerPointing = new javax.swing.Timer(delayTimer, (ActionEvent e) -> {
            genererForme();
            state = State.INIT;
        });
        
        actionTimeOut.stop();
        actionTimeOut.stop();
        
        try {
            busIvy.start("127.255.255.255:2010");
            
            busIvy.bindMsg("gesture action=(.*)", (IvyClient client, String[] args) -> {
                //INIT ?
                actions(args[0]);
            });
            
            
            busIvy.bindMsg("sra5 Parsed=couleur:(.*) Confidence=(.*) NP=.*", (client, args) -> {
                String Reco = args[1].split(",")[1];
                int tauxReco = Integer.parseInt(Reco);
                if( tauxReco>TAUXRECOMIN){
                    couleur(args[0]);
                }
            });
            
            busIvy.bindMsg("Palette:Mouse(.*) x=(.*) y=(.*)", (client, args) -> {
                if(args[0].equals("Clicked")){
                    clic(new Point(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                }
            });
            
            
            busIvy.bindMsg("sra5 Parsed=DesignationPosition: Confidence=(.*) NP=.*", (client, args) -> {
                String Reco = args[1].split(",")[1];
                int tauxReco = Integer.parseInt(Reco);
                //APPLIQUER LA METHODE DE PARSING ICI
                if(tauxReco>TAUXRECOMIN){
                    Vocal(LanguageVocal.Position);
                }
            });
            busIvy.bindMsg("sra5 Parsed=DesignationCouleur: Confidence=(.*) NP=.*", (client, args) -> {
                String Reco = args[1].split(",")[1];
                int tauxReco = Integer.parseInt(Reco);
                if(tauxReco>TAUXRECOMIN){
                     Vocal(LanguageVocal.Couleur);
                }
            });
            busIvy.bindMsg("sra5 Parsed=DesignationForme:(.*) Confidence=(.*) NP=.*", (client, args) -> {
                String Reco = args[1].split(",")[1];
                int tauxReco = Integer.parseInt(Reco);
                if(tauxReco>TAUXRECOMIN){
                    if(args[0].equals("cet objet")){
                        Vocal(LanguageVocal.Objet);
                    } else if (args[0].equals("ce rectangle")){
                        Vocal(LanguageVocal.Rectangle);
                    } else if (args[0].equals("cette ellipse")){
                        Vocal(LanguageVocal.Ellipse);
                    }
                    //A SUPPRIMER
                    System.out.println("Designer forme : "+args[0]);
                }
            });
            
        } catch (IvyException ex) {
            Logger.getLogger(Automate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private int parsingTauxReco(String arg){
        String Reco = arg.split(",")[1];
        return Integer.parseInt(Reco);
    }
    
    
    
    public void actions (String arg){
        switch(arg){
            case "Rectangle":
                forme = TypeForme.RECTANGLE;
                action = new Creation(couleur.toString(), forme);
                break;
            case "Ellipse":
                forme = TypeForme.ELLIPSE;
                action = new Creation(couleur.toString(), forme);
                break;
            case "Deplacer":
                //TODO
                break;
            case "Supprimer":
                action = new Suppression();
                break;
        }
        state = State.OPTION;
        actionTimeOut.start();
        timerPointing.stop();  
    }
    
    
    public void couleur(String c){
        switch(state){
            case INIT:
                //NE RIEN FAIRE
                break;
            case OPTION:
                switch(c){
                    case "ROUGE":
                        this.couleur = Couleur.ROUGE;
                        break;
                    case "VERT":
                        this.couleur = Couleur.VERT;
                        break;
                    case "BLEU":
                        this.couleur = Couleur.BLEU;
                        break;
                    case "NOIR":
                        this.couleur = Couleur.NOIR;
                        break;
                }
                
                actionTimeOut.stop();
                actionTimeOut.start();
                state = State.OPTION;
                break;
            case POSITION:
                //NE RIEN FAIRE
                break;
            case CLIC:
                //NE RIEN FAIRE
                break;
        }
    }
    
    
    /*ONCLICK APPELLE CLIC*/
    public void clic(Point2D p){
        switch(state){
            case INIT:
                //NE RIEN FAIRE
                break;
            case OPTION:
                actionTimeOut.stop();
                timerPointing.start();
                state = State.CLIC;
                break;
            case POSITION:
                actionTimeOut.start();
                timerPointing.stop();
                state = State.OPTION;
                break;
            case CLIC:
                //NE RIEN FAIRE
                break;
        }
    }
    
    
    public void vocalHere(VOCALPOS v){
        switch(state){
            case INIT:
                //NE RIEN FAIRE
                break;
            case OPTION:
                if(v.equals(VOCALPOS.ICI) || v.equals(VOCALPOS.LA)||v.equals(VOCALPOS.ACETTEPOSITION)){
                    actionTimeOut.stop();
                    timerPointing.start();
                    state = State.POSITION;
                }else{
                    actionTimeOut.start();
                    timerPointing.stop();
                    state = State.CLIC;
                }
                break;
            case POSITION:
                //NE RIEN FAIRE
                break;
            case CLIC:
                if(v.equals(VOCALPOS.ICI) || v.equals(VOCALPOS.LA)||v.equals(VOCALPOS.ACETTEPOSITION)){
                    actionTimeOut.start();
                    timerPointing.stop();
                    state = State.OPTION;
                }else{
                    actionTimeOut.stop();
                    timerPointing.start();
                    state = State.CLIC;
                }
            break;
               
        }
    }
    
    
    
    
    
}
