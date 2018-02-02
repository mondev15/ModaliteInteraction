/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reco;

import structures.Forme;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;


/**
 *
 * @author benjamin.saint-sever
 */
public class Automate {
    
    
   
    
    enum State{INIT, OPTION, CLIC, POSITION};
    private State state;
    
    private Forme forme;
    
    enum COULEUR{ROUGE, VERT, BLEU, NOIR};
    private COULEUR couleur;
    
    enum VOCALPOS{ICI, LA, ACETTEPOSITION};
    private Point2D position;
    
    private Timer timerOption;
    private Timer timerClicAndPosition;
    private int delayTimer = 2000;
    
    
    private int _X = 20;
    private int _Y = 70;
    private int _WIDTH = 150;
    private int _HEIGHT = 150;
    
    private Ivy busIvy;
    
     public void genererForme(){
        //INSERER ICI LE CODE DE GENERATION DE FORME
        switch(forme){
            case RECTANGLE:
                try {
                        //on peut précise la position (x,y) et la couleur
                        busIvy.sendMsg("Palette:CreerRectangle x="+_X+" y="+_Y+" couleurFond="+couleur);
                        //créer la forme et l'enregistrer dans le dictionnaire
                    } catch (IvyException ex) {
                        Logger.getLogger(IHMgesture.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
            case ELLIPSE:
                try {
                        //on peut précise la position (x,y) et la couleur
                        busIvy.sendMsg("Palette:CreerEllipse x="+_X+" y="+_Y+" couleurFond="+couleur);
                        //créer la forme et l'enregistrer dans le dictionnaire
                    } catch (IvyException ex) {
                        Logger.getLogger(IHMgesture.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
        }
    }
    
    
    public Automate(){
        state = State.INIT;
        couleur = COULEUR.ROUGE;
        forme = Forme.RECTANGLE;
        position = new Point2D.Double(0,0);
        IvyApplicationListener listener = null;
        busIvy = new Ivy("RecoVocale","Bonjour 1",listener);
        
        
        try {
            busIvy.start("127.255.255.255:2010");
        } catch (IvyException ex) {
            Logger.getLogger(Automate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        timerOption = new javax.swing.Timer(delayTimer, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        genererForme();
                        state = State.INIT;
                    }
                });
        
        timerClicAndPosition = new javax.swing.Timer(delayTimer, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        genererForme();
                        state = State.INIT;
                    }
                });
    }
    
    
    
    /*
    * NOTE : SEUL L'AUTOMATE DE CREATION FORME EST PRESENT POUR L'INSTANT AFIN DE TESTER
    */
    
    /* CONVERTIR LE NOM DE LA FORME EN PARAMETRE ?*/
    public void CreerForme(Forme f){
        switch(state){
            case INIT:
                switch(f){
                    case RECTANGLE:
                        this.forme = Forme.RECTANGLE;
                        break;
                    case ELLIPSE:
                        this.forme = Forme.ELLIPSE;
                        break;
                }

                timerOption.start();      
        
                try {
                    busIvy.bindMsg("sra5 couleur:(.*) Confidence=(.*) NP=.*", new IvyMessageListener(){
                        @Override
                        public void receive(IvyClient client, String[] args) {
                            String Action = args[0];
                            String Reco = args[1];

                            Reco = Reco.split(",")[1];
                            int tauxReco = Integer.parseInt(Reco);       

                            if(tauxReco >79 ){
                                switch(args[0]){
                                    case "rouge":
                                        couleur = COULEUR.ROUGE;
                                        System.out.println("Rouge");
                                        break;
                                    case "bleu":
                                        couleur = COULEUR.BLEU;
                                        System.out.println("Bleu");
                                        break;
                                    case "vert":
                                        couleur = COULEUR.VERT;
                                        System.out.println("Vert");
                                        break;
                                    case "noir":
                                        couleur = COULEUR.NOIR;
                                        System.out.println("Noir");
                                        break;
                                }
                            }
                        }
                    });
                } catch (IvyException ex) {
                    Logger.getLogger(Automate.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
    
    public void couleur(COULEUR c){
        switch(state){
            case INIT:
                //NE RIEN FAIRE
                break;
            case OPTION:
                switch(c){
                    case ROUGE:
                        this.couleur = COULEUR.ROUGE;
                        break;
                    case VERT:
                        this.couleur = COULEUR.VERT;
                        break;
                    case BLEU:
                        this.couleur = COULEUR.BLEU;
                        break;
                    case NOIR:
                        this.couleur = COULEUR.NOIR;
                        break;
                }
                
                timerOption.stop();
                timerOption.start();
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
                timerOption.stop();
                timerClicAndPosition.start();
                state = State.CLIC;
                break;
            case POSITION:
                timerOption.start();
                timerClicAndPosition.stop();
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
                    timerOption.stop();
                    timerClicAndPosition.start();
                    state = State.POSITION;
                }else{
                    timerOption.start();
                    timerClicAndPosition.stop();
                    state = State.CLIC;
                }
                break;
            case POSITION:
                //NE RIEN FAIRE
                break;
            case CLIC:
                if(v.equals(VOCALPOS.ICI) || v.equals(VOCALPOS.LA)||v.equals(VOCALPOS.ACETTEPOSITION)){
                    timerOption.start();
                    timerClicAndPosition.stop();
                    state = State.OPTION;
                }else{
                    timerOption.stop();
                    timerClicAndPosition.start();
                    state = State.CLIC;
                }
            break;
               
        }
    }
    
    
    
    
    
}
