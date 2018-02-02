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
public class Forme {

    private TypeForme monType = TypeForme.INDEFINI;
    private String nom;
    private Point position;

    
    private Couleur couleur;

    
    public Forme(TypeForme type) {
       this.monType = type;
    }
   
    public Forme(String _nom, Point _position) {
        this.nom = nom;
        this.position = (Point) _position.clone();
    }
   
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point _position) {
        this.position = (Point) _position.clone();
    }
    
    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }
    
    public TypeForme getMonType() {
        return monType;
    }

    public void setMonType(TypeForme monType) {
        this.monType = monType;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
}
