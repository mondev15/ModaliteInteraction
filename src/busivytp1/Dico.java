/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busivytp1;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
    File dicofile;

    public Dico() {
        strokeMap = new HashMap<String, Stroke>();
       File currentDir = new File(System.getProperty("user.dir"));
        String[] fileList = currentDir.list();
        Boolean dicoExists = false;
        // on vérifie si le fichier dico existe
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].contains("dico")) {
                dicoExists = true;
            }
        }

        // si le fichier dico n'existe pas, il le crée
        if (dicoExists == false) {
            dicofile = new File("dico");
        } 
        else{
        dicofile = new File(currentDir+"\\dico");
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
        if (dicofile.length() > 0) {
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
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos =  new FileOutputStream(dicofile);
            out = new ObjectOutputStream(fos);
            out.writeObject(strokeMap);
        } catch (Exception ex) {
            Logger.getLogger(Dico.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
        closeFile(out);
        }
    }

    // va relire le fichier dicofile s'il existe déjà
    public void loadFile() {
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try {
            // on récupère le fichier existant
            dicofile = new File (System.getProperty("user.dir")+"\\dico");
            fis = new FileInputStream(dicofile);
            in = new ObjectInputStream(fis);
            strokeMap = (HashMap<String,Stroke>) in.readObject();
        } catch (Exception ex) {
            Logger.getLogger(Dico.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
        closeFile(in);
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
