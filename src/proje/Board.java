/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author omerkerem.adali
 */
public class Board extends JPanel{
    Peg peg;
    Location location;

    public Board(Location location) {
        this.location = location;
    }
    
    public void removePeg() {
        if(peg != null) {
            this.remove(peg);
            peg.board = null;
            peg.location = null;
            this.peg = null;
            this.revalidate();
            this.repaint();
        }
        else {
            System.out.println("removePeg Hata");
        }
    }
    
    public void addPeg(Peg newPeg) {
        if(peg == null) {
            this.peg = newPeg;
            newPeg.board = this;
            newPeg.location = this.location;
            this.setLayout(new GridBagLayout());
            this.add(peg);
            this.revalidate();
            this.repaint();
        }
        else {
            System.out.println("addPeg Hata");
        }
        
    }   
}
