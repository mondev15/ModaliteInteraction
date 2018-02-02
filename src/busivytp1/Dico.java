/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busivytp1;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mundial
 */
public class Dico implements Serializable {

    private Map<String, Stroke> strokeMap;
    BufferedWriter bw;
    FileWriter fw;
    File dicofile;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    
    public Dico() {
        dicofile = new File("dico");
        strokeMap = new HashMap<String, Stroke>();
        try {
            out = new ObjectOutputStream(new FileOutputStream(dicofile));
        } catch (IOException ex) {
            Logger.getLogger(Dico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addStroke(String name, Stroke stroke) {
        strokeMap.put(name, stroke);
    }

    public void removeStroke(Stroke s) {
        strokeMap.remove(s);
    }

    public Map<String, Stroke> getStrokeMap() {
        return strokeMap;
    }

    public String recognize(Stroke myStroke) {
        if (dicofile.length()>0){
            loadFile();
        }
        String forme = "Geste non reconnu";
        double min = Double.MAX_VALUE;
        double dist = 0.0;

        //---dico
        for (Map.Entry<String, Stroke> entry : strokeMap.entrySet()) {
            dist = 0.0;
            Stroke stroke = entry.getValue();
            String s = entry.getKey();
            //---myStroke and strokes in dico
            for (int i = 0; i < 32; ++i) {
                dist += stroke.getPoint(i).distance(myStroke.getPoint(i));
            }
            System.out.println(" dist " + s + "  :" + dist);
            if (dist < min) {
                min = dist;
                forme = entry.getKey();
            }
        }
        if (forme.equals("Rectangle")) {
            forme = "rectangle";
        }
        if (forme.equals("Ellipse")) {
            forme = "ellipse";
        }
        System.out.println("Forme reconnue : " + forme);

        return forme;
    }

    public void saveToFile() {
        try {
            out.writeObject(strokeMap);
        } catch (IOException ex) {
            Logger.getLogger(Dico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // va relire le fichier dicofile s'il existe déjà
    public void loadFile() {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        
        if (dicofile.length() > 0) {          
            try{
                fis = new FileInputStream(dicofile);
                in = new ObjectInputStream(fis);
                try {
                    strokeMap = (HashMap<String,Stroke>) in.readObject();
                    //System.out.println("Map size: "+strokeMap.size());
                    //System.out.println("Map content: "+strokeMap.entrySet());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Dico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            catch(IOException ex){
            Logger.getLogger(Dico.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
            closeFile(in);
            }
            
        }
    }

    // pour éviter de faire des try catch autour de close
    public void closeFile(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
