/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busivytp1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mundial
 */
public class Dico implements Serializable{

    private Map<String, Stroke> strokeMap;
    BufferedWriter bw;
    FileWriter fw;

    public Dico() {
        strokeMap = new HashMap<String, Stroke>();
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
        String forme = "Geste non reconnu";
        double min = Double.MAX_VALUE;
        double dist = 0.0;

        //---dico
        for (Map.Entry<String, Stroke> entry : strokeMap.entrySet()) {
            dist = 0.0;
            Stroke stroke = entry.getValue();
            String s = entry.getKey();
            System.err.println("key : " + s);
            //---myStroke and strokes in dico
            for (int i = 0; i < 32; ++i) {
                dist += stroke.getPoint(i).distance(myStroke.getPoint(i));
            }
            System.out.println("--------------->dist :" + dist);
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
        File file = new File("dico");
        try {
            bw = new BufferedWriter(new FileWriter(file));
        } catch (IOException ex) {
            Logger.getLogger(Dico.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            bw.write(strokeMap.toString());
        } catch (IOException ex) {
            Logger.getLogger(Dico.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Dico.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
