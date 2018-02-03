/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import fr.dgac.ivy.Ivy;

/**
 *
 * @author saintsbe
 */
public interface Actions {
    boolean isComplete();
    void sendToIvy(Ivy busIvy);
}
