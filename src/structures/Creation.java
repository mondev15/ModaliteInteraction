/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.awt.Point;

/**
 *
 * @author saintsbe
 */
public class Creation implements Actions {

    private boolean isComplete = false;
    private Forme forme = Forme.INDEFINI;
    
    
    private Point position = new Point(50,50);
    
    @Override
    public boolean isComplete() {
        return isComplete;
    }
    
    
    
}
