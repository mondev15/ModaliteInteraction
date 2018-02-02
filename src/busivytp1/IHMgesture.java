/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busivytp1;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import fr.irit.elipse.enseignement.isia.PaletteGraphique;
import java.awt.geom.Point2D;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mundial
 */
public class IHMgesture extends javax.swing.JFrame {

    Ivy bus;
    PaletteGraphique palette;
    Dico dico;
    String forme = "";

    public IHMgesture() throws IvyException {
        initComponents();
        //---buttons
        jButtonAjouterGeste.setVisible(true);
        jTextFieldForme.setVisible(true);
        jButtonReconnaitre.setVisible(false);

        //---dico
        dico = new Dico();
        //---bus
        bus = new Ivy("agent2", "gesture", null);
        palette = new PaletteGraphique("127.255.255.255:2010", 250, 250, 250, 250);
        try {
            bus.start("127.255.255.255:2010");

            bus.bindMsg("Palette:MousePressed x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    try {
                        dessin.setStroke(new Stroke());
                        dessin.setFinished(false);
                        //System.out.println(" MousePressed x= " + args[0] + " y= " + args[1]);
                        int x = Integer.parseInt(args[0]);
                        int y = Integer.parseInt(args[1]);
                        dessin.getStroke().getPoints().add(new Point2D.Double(x, y));
                        dessin.repaint();
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(IHMgesture.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            bus.bindMsg("Palette:MouseReleased x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    //System.out.println(" MouseReleased x= " + args[0] + " y= " + args[1]);
                    try {
                        dessin.setFinished(true);
                        int x = Integer.parseInt(args[0]);
                        int y = Integer.parseInt(args[1]);
                        dessin.getStroke().getPoints().add(new Point2D.Double(x, y));
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(IHMgesture.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dessin.repaint();
                }
            });

            bus.bindMsg("Palette:MouseDragged x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient client, String[] args) {
                    //System.out.println(" MouseDragged x= " + args[0] + " y= " + args[1]);
                    dessin.setFinished(false);
                    int x = Integer.parseInt(args[0]);
                    int y = Integer.parseInt(args[1]);
                    try {
                        dessin.getStroke().getPoints().add(new Point2D.Double(x, y));
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(IHMgesture.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dessin.repaint();
                }
            });
        } catch (IvyException ex) {
            Logger.getLogger(IHMgesture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup4 = new javax.swing.ButtonGroup();
        dessin = new busivytp1.Dessin();
        jButtonAjouterGeste = new javax.swing.JButton();
        jTextFieldForme = new javax.swing.JTextField();
        jRadioButtonEnregistrement = new javax.swing.JRadioButton();
        jRadioButtonReconnaissance = new javax.swing.JRadioButton();
        jButtonReconnaitre = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("IHM gesture");
        setLocation(new java.awt.Point(600, 300));

        dessin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonAjouterGeste.setText("AjouterGeste");
        jButtonAjouterGeste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAjouterGesteActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButtonEnregistrement);
        jRadioButtonEnregistrement.setSelected(true);
        jRadioButtonEnregistrement.setText("Enregistrement");
        jRadioButtonEnregistrement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonEnregistrementActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButtonReconnaissance);
        jRadioButtonReconnaissance.setText("Reconnaissance");
        jRadioButtonReconnaissance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonReconnaissanceActionPerformed(evt);
            }
        });

        jButtonReconnaitre.setText("Reconnaitre");
        jButtonReconnaitre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReconnaitreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dessin, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRadioButtonEnregistrement)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButtonReconnaissance)
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTextFieldForme, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonReconnaitre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAjouterGeste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonEnregistrement)
                    .addComponent(jRadioButtonReconnaissance))
                .addGap(18, 18, 18)
                .addComponent(dessin, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldForme, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAjouterGeste))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonReconnaitre))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAjouterGesteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAjouterGesteActionPerformed
        // TODO add your handling code here:
        //reconnait le rectangle ou ellipse uniquement

        if (jTextFieldForme.getText().equals("Rectangle") || jTextFieldForme.getText().equals("Ellipse")) {
            try {
                // comment reconnaitre une forme
                dico.addStroke(jTextFieldForme.getText(), dessin.getStroke());
                System.out.println("dico size : " + dico.getStrokeMap().size());
                System.out.println("name : " + jTextFieldForme.getText());
                jTextFieldForme.setText("");
                dessin.setStroke(new Stroke());
                dessin.repaint();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(IHMgesture.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jButtonAjouterGesteActionPerformed

    private void jRadioButtonReconnaissanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonReconnaissanceActionPerformed
        // TODO add your handling code here:
        jButtonReconnaitre.setVisible(true);
        jButtonAjouterGeste.setVisible(false);
        jTextFieldForme.setVisible(false);
        dessin.setStroke(new Stroke());
        dessin.repaint();
    }//GEN-LAST:event_jRadioButtonReconnaissanceActionPerformed

    private void jRadioButtonEnregistrementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonEnregistrementActionPerformed
        // TODO add your handling code here:
        jButtonAjouterGeste.setVisible(true);
        jButtonReconnaitre.setVisible(false);
        jTextFieldForme.setVisible(true);
        dessin.setStroke(new Stroke());
        dessin.repaint();
    }//GEN-LAST:event_jRadioButtonEnregistrementActionPerformed

    private void jButtonReconnaitreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReconnaitreActionPerformed
        // TODO add your handling code here:
        if(dico.getStrokeMap().size() !=0){
            dico.saveToFile();
        }
        Stroke s;
        try {
            //---reconnaissance de la forme
            s = dessin.getStroke();
            forme = dico.recognize(s);
            //---dessinner la bonne forme reconnue sur la palette 
            switch (forme) {
                case "rectangle": {
                    try {
                        //on peut précise la position (x,y) et la couleur
                        bus.sendMsg("Palette:CreerRectangle x=50 y=50 couleurFond=BLACK");
                        //créer la forme et l'enregistrer dans le dictionnaire
                    } catch (IvyException ex) {
                        Logger.getLogger(IHMgesture.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "ellipse": {
                    try {
                        bus.sendMsg("Palette:CreerEllipse x=50 y=50 couleurFond=BLACK");
                    } catch (IvyException ex) {
                        Logger.getLogger(IHMgesture.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;
            }
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(IHMgesture.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButtonReconnaitreActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IHMgesture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IHMgesture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IHMgesture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IHMgesture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new IHMgesture().setVisible(true);
                } catch (IvyException ex) {
                    Logger.getLogger(IHMgesture.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup4;
    private busivytp1.Dessin dessin;
    private javax.swing.JButton jButtonAjouterGeste;
    private javax.swing.JButton jButtonReconnaitre;
    private javax.swing.JRadioButton jRadioButtonEnregistrement;
    private javax.swing.JRadioButton jRadioButtonReconnaissance;
    private javax.swing.JTextField jTextFieldForme;
    // End of variables declaration//GEN-END:variables
}
