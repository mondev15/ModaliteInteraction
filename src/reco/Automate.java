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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import structures.Actions;
import structures.Couleur;
import structures.Creation;
import structures.Deplacement;
import structures.LanguageVocal;
import structures.Suppression;
import structures.TypeActions;


/**
 *
 * @author benjamin.saint-sever
 */
public class Automate {
     public static void main(String args[]) {
        Automate a = new Automate();
     }
     
    enum State{INIT, OPTION, CLIC, POSITION};
    private State state;
    
    LanguageVocal mots;
    
    private TypeForme forme;
    private Actions action;
    
    private Couleur couleur;
    
 
    private Point position;
    private Point positionTmp;
    
    private Timer timerOut;
    private Timer timerOption;
    private int delayTimer = 5000;
    
    private final int TAUXRECOMIN = 79;
    
  
    private int _X = 20;
    private int _Y = 70;
    private int _WIDTH = 150;
    private int _HEIGHT = 150;
    
    private Ivy busIvy;
    

    private void initAttribute(){
        couleur = Couleur.RED;
        forme = TypeForme.RECTANGLE;
        position = new Point(0,0);
        positionTmp = new Point(0,0);
        action = null;
        mots = null;
    }
    
    public Automate(){
        state = State.INIT;
        initAttribute();

        IvyApplicationListener listener = null;
        busIvy = new Ivy("FusionMultimodale","",listener);
        
        timerOut = new javax.swing.Timer(delayTimer, (ActionEvent e) -> {
            timerOut();
        });
        
        timerOption = new javax.swing.Timer(delayTimer, (ActionEvent e) -> {
            timerOut();
        });
        
        timerOut.stop();
        timerOption.stop();
        
        try {
            busIvy.start("127.255.255.255:2010");
            
            busIvy.bindMsg("gesture action=(.*)", (IvyClient client, String[] args) -> {
                initAttribute();
                actions(args[0]);
            });
            
            
            busIvy.bindMsg("sra5 Parsed=couleur:(.*) Confidence=(.*) NP=.*", (client, args) -> {
                String Reco = args[1].split(",")[1];
                int tauxReco = Integer.parseInt(Reco);
                if( tauxReco>TAUXRECOMIN){
                    color(args[0]);
                    System.out.println("Reconnaissance Couleur ok : "+args[0] );
                }
                 
            });
            
            busIvy.bindMsg("Palette:Mouse(.*) x=(.*) y=(.*)", (client, args) -> {
                if(args[0].equals("Clicked")){
                    clic(new Point(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                    System.out.println("Reconnaissance Click ok : x="+Integer.parseInt(args[1])+" y="+args[2] );
                }
            });
           
            
            busIvy.bindMsg("sra5 Parsed=DesignationPosition:.* Confidence=(.*) NP=.*", (client, args) -> {
                String Reco = args[0].split(",")[1];
                int tauxReco = Integer.parseInt(Reco);
                //APPLIQUER LA METHODE DE PARSING ICI
                if(tauxReco>TAUXRECOMIN){
                    System.out.println("Reconnaissance Position ok" );
                    Vocal(LanguageVocal.Position);
                }else{
                    System.out.println("Reconnaissance Position NOTok :"+ args[0] );
                }
            });
            busIvy.bindMsg("sra5 Parsed=DesignationCouleur:.* Confidence=(.*) NP=.*", (client, args) -> {
                String Reco = args[0].split(",")[1];
                int tauxReco = Integer.parseInt(Reco);
                if(tauxReco>TAUXRECOMIN){
                    System.out.println("Designation couleur OK");
                     Vocal(LanguageVocal.Couleur);
                }else{
                    System.out.println("Designation couleur NOTOK");
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
                action = new Deplacement();
                break;
            case "Supprimer":
                action = new Suppression();
                break;
        }
        retourOption();
    }
    
    
    public void color(String c){
        switch(state){
            case INIT:
                //NE RIEN FAIRE
                break;
            case OPTION:
                System.out.println("Appel a fonction color : "+ c);
                //PArsingCouleur
                switch(c){
                    case "rouge":
                        this.couleur = Couleur.RED;
                        break;
                    case "vert":
                        this.couleur = Couleur.GREEN;
                        break;
                    case "bleu":
                        this.couleur = Couleur.BLUE;
                        break;
                    case "noir":
                        this.couleur = Couleur.BLACK;
                        break;
                }
               
               
                updateStructure();
                
                retourOption();
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
    public void clic(Point p){
        switch(state){
            case INIT:
                //NE RIEN FAIRE
                break;
            case OPTION:
                positionTmp = p;
                position = p;
                startTimerOption();
                state = State.CLIC;
                break;
            case POSITION:
                if(mots == LanguageVocal.Couleur){
                    getColor(p);
                }
                
                position = p;
                updateStructure();
                retourOption();
                break;
            case CLIC:
                //NE RIEN FAIRE
                break;
        }
    }
    
    public void Vocal(LanguageVocal l){
         switch(state){
            case INIT:
                //NE RIEN FAIRE
                break;
            case OPTION:
                
                //A MODIFIER
               if(l == LanguageVocal.Couleur){
                   System.out.println("Vocal+OPTION");
                    mots = l;
                    startTimerOption();
                    retourOption();
               }
               else{
                    state = State.POSITION;
                    startTimerOption();
               }
                break;
            case POSITION:
                //NE RIEN FAIRE
                break;
            case CLIC:
                System.out.println("Vocal+CLIC");
                if(l == LanguageVocal.Couleur){
                    getColor(this.position);
                    position = positionTmp;
                }
                if(l == LanguageVocal.Position){
                    position = positionTmp;
                }
                mots = l;
                updateStructure();
                retourOption();
                break;
          
         }
                
    }
    
    public void timerOut(){
        switch(state){
            case INIT:
                //NE RIEN FAIRE
                break;
            case OPTION:
                //if(action.isComplete())
                    action.sendToIvy(busIvy);

                timerOption.stop();
                    //stopAllTimer();
                initAttribute();
                state = State.INIT;
                break;
            case POSITION:
                retourOption();
                break;
            case CLIC:
                retourOption();
                break; 
        }
    }
    
    private void startTimerOption(){
        stopAllTimer();
        timerOut.stop();
        timerOption.start();
    }
    private void startTimerOut(){
        stopAllTimer();
        timerOut.start();
        timerOut.start();
    }
    
    private void stopAllTimer(){
        timerOption.stop();
        timerOut.stop();
    }
    
    private void retourOption(){
        startTimerOut();
        state = State.OPTION;
        System.out.println("Retour Etat Option");
    }
    
    private void updateStructure(){
         System.out.println("Update action");;
        if(action instanceof Creation){
            System.out.println("Update action est une instance de CreATION");
            if(this.position != null){
                System.out.println("Update : X="+(int)position.getX()+" Y="+ (int)position.getY());
                ((Creation)action).setPosition((Point) this.position);                
            }
            if(this.couleur != null){
                ((Creation)action).setCouleur(this.couleur.toString());
            }
            //todo designerCouleur
        }else if(action instanceof Deplacement){
            if(action != null && mots !=null){
                if(mots == LanguageVocal.Position){
                    ((Deplacement)action).setPosition(this.position);
                }else if(mots == LanguageVocal.Ellipse){
                    ((Deplacement)action).filter(busIvy,position);
                    ((Deplacement)action).filter(TypeForme.ELLIPSE);
                }else if(mots == LanguageVocal.Rectangle){
                    ((Deplacement)action).filter(busIvy,position);
                    ((Deplacement)action).filter(TypeForme.RECTANGLE);
                }else if(mots == LanguageVocal.Objet){
                    ((Deplacement)action).filter(busIvy,position);
                }
            }
            if(couleur != null){
                ((Deplacement)action).filter(couleur.toString());
            }
        }else if(action instanceof Suppression){
            if(this.position != null && mots !=null){
                if(mots == LanguageVocal.Ellipse){
                    System.out.println("Ellipse detecté, a supprimer");
                    ((Suppression)action).filter(busIvy, position);
                    ((Suppression)action).filter(TypeForme.ELLIPSE);
                }else if(mots == LanguageVocal.Rectangle){
                    ((Suppression)action).filter(busIvy,position);
                    ((Suppression)action).filter(TypeForme.RECTANGLE);
                }else if(mots == LanguageVocal.Objet){
                    ((Suppression)action).filter(busIvy,position);
                }
            }
            if(couleur != null){
                ((Suppression)action).filter(couleur.toString());
            }
        }
    }
            

    private void getColor(Point p){
        try {
            System.out.println("GETCOLOR");
            busIvy.bindMsg("Palette:ResultatTesterPoint x="+ (int)p.getX() +" y="+ (int)p.getY()+" nom=(.*)", (client, args) -> {
               
                try {
                    
                    try {
                        //A MODIFIER POUR SUPPRIMER L'AFFICHAGE
                        busIvy.bindMsg("Palette:Info nom="+args[0]+" x=(.*) y=(.*) longueur=(.*) hauteur=(.*) couleurFond=(.*) couleurContour=(.*)", (client1, args1) -> {
                            switch(args1[4]){
                                case "green":
                                    this.couleur = Couleur.GREEN;
                                    
                                    break;
                                case "blue":
                                    this.couleur = Couleur.BLUE;
                                    break;
                                case "red":
                                    this.couleur = Couleur.RED;
                                    break;
                                case "black":
                                    this.couleur = Couleur.BLACK;
                                    break;
                            }
                            updateStructure();
                        });
                    } catch (IvyException ex) {
                        Logger.getLogger(Automate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //SUPPRIMER AFFICHAGE
                    busIvy.sendMsg("Palette:DemanderInfo nom="+args[0]);
                    System.out.println("sent demanderInfo over "+args[0]);
                } catch (IvyException ex) {
                    Logger.getLogger(Automate.class.getName()).log(Level.SEVERE, null, ex);
                }
                }); 
        } catch (IvyException ex) {
            Logger.getLogger(Automate.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
            
         
    }
    
}
