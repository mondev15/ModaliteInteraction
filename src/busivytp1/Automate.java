/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busivytp1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.Timer;


/**
 *
 * @author benjamin.saint-sever
 */
public class Automate {
    
    
    public void genererForme(){
        //INSERER ICI LE CODE DE GENERATION DE FORME
    }
    
    
    enum State{INIT, OPTION, CLIC, POSITION};
    private State state;
    
    enum FORME{RECTANGLE, ELLIPSE};
    private FORME forme;
    
    enum COULEUR{ROUGE, VERT, BLEU, NOIR};
    private COULEUR couleur;
    
    enum VOCALPOS{ICI, LA, ACETTEPOSITION};
    private Point2D position;
    
    private Timer timerOption;
    private Timer timerClicAndPosition;
    private int delayTimer = 2000;
    
    public Automate(){
        state = State.INIT;
        couleur = COULEUR.ROUGE;
        forme = FORME.RECTANGLE;
        position = new Point2D.Double(0,0);
        
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
    
    
    
    /* CONVERTIR LE NOM DE LA FORME EN PARAMETRE ?*/
    public void CreerForme(FORME f){
        switch(state){
            case INIT:
                switch(f){
                    case RECTANGLE:
                        this.forme = FORME.RECTANGLE;
                        break;
                    case ELLIPSE:
                        this.forme = FORME.ELLIPSE;
                        break;
                }
                timerOption.start();
                state = State.OPTION;
                break;
            case OPTION:
                //NE RIEN FAIRE
                break;
            case POSITION:
                //NE RIEN FAIRE
                break;
            case CLIC:
                //NE RIEN FAIRE
                break;
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
