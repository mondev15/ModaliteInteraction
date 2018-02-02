/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recoVocale;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 *
 * @author saintsbe
 */
public class MainProject extends JComponent{
    private int _X = 20;
    private int _Y = 70;
    private int _WIDTH = 150;
    private int _HEIGHT = 150;
    
    private Ivy busIvy;
    /**
     * Creates new form GraphiqueForme
     */
    public MainProject() {
        super();
       
        
         IvyApplicationListener listener = null;
            
            busIvy = new Ivy("RecoVocale","Bonjour 1",listener);
            
        try {
            busIvy.start("127.255.255.255:2010");
           
            //Le send juste apres le start ne fonctionne pas toujours(laisser le temps
            //de se connecter, on peut mettre son premier message dans le constructeur
            //busIvy.sendMsg("");
            
            busIvy.bindMsg("sra5 Text=(.*) Confidence=(.*)", new IvyMessageListener(){
                @Override
                public void receive(IvyClient client, String[] args) {  
                   //for(int i = 0; i < args.length; i++){
                       
                      String Action = args[0];
                      String Reco = args[1];
                      
                      
                      Reco = Reco.split(",")[1];
                      int tauxReco = Integer.parseInt(Reco);
                                           
                      if(tauxReco >79 ){
                        switch(args[0]){
                              case "deplacer en haut":
                                  _Y -= 10;
                                  repaint();
                                  System.out.println("Deplacer en haut");
                                  break;
                              case "deplacer en bas" :
                                  _Y += 10;
                                  repaint();
                                  System.out.println("Deplacer en bas");
                                   break;
                              case "deplacer a droite"  :
                                  _X += 10;
                                  repaint();
                                  System.out.println("Deplacer a droite");
                                   break;
                              case "deplacer a gauche"  :
                                   _X -= 10;
                                  repaint();
                                  System.out.println("Deplacer a gauche");
                                   break;
                              case "deplacer au centre"  :  
                                   _X = 250;
                                   _Y = 250;
                                    repaint();
                                    System.out.println("Deplacer au centre");
                                   break;
                              case "deplacer en diagonale":
                                   break;


                        }
                             
                      }else{
                          System.out.println("Taux de reco insuffisant");
                      }
                }
            
            });
        } catch (IvyException ex) {
            System.out.println("probleme");
            Logger.getLogger(MainProject.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        g2.setPaint(Color.red);
        g2.fillRect(_X, _Y, _WIDTH, _HEIGHT);

     }

}
