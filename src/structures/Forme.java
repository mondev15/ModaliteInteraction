/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.awt.geom.Point2D;



/**
 *
 * @author saintsbe
 */
public class Forme {

    private TypeForme monType = TypeForme.INDEFINI;
    private String nom;
    private Point2D position;

    
    private String couleur;

    
    public Forme(TypeForme type) {
       this.monType = type;
    }
   
    public Forme(String _nom, Point2D _position) {
        this.nom = nom;
        this.position = (Point2D) _position.clone();
    }
   
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D _position) {
        this.position = (Point2D) _position.clone();
    }
    
    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
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
