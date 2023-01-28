/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author omerkerem.adali
 */
public class Peg extends JButton {
    Location location;
    Board board;

    public Peg(Location location) {
        this.location = location;
        this.setPreferredSize(new Dimension(30,30));
        this.setBackground(Color.black);
        this.setFocusPainted(false);
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
    
}
