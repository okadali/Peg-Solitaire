/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.border.Border;


/**
 *
 * @author omerkerem.adali
 */
public class Main extends javax.swing.JFrame {

    int n = 6;
    int pegLeft = 0;
    Peg selectedPeg;
    MultiLinkedList gameMLL;
    public Main() {
        initComponents();
        ((JSpinner.DefaultEditor)spn_gameSize.getEditor()).getTextField().setEditable(false);
        createGame();
        
    }
    
    public void createGame() {
        //Cleaning for the new game
        lbl_pegLeftValue.setText(String.valueOf(pegLeft));
        pnl_game.removeAll();
        selectedPeg = null;
        lbl_selectedPegValue.setText("none");
        gameMLL = new MultiLinkedList(new Board(new Location(0, 0)));
        
        //creating new game
        int counter = 0;
        for(int x = 0 ; x < n ; x++) {
            Panel newPanel = new Panel();
            GridLayout newLayout = new GridLayout(n,0,0,0);
            newPanel.setLayout(newLayout);
            pnl_game.add(newPanel);
            for(int y = 0 ; y < n ; y++) {
                Board inPanel = new Board(new Location(x, y));
                inPanel.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(inPanel.peg == null) {
                            if(selectedPeg != null) {
                                if(inPanel.location.getRow() == selectedPeg.location.getRow() 
                                        && inPanel.location.getColumn() == selectedPeg.location.getColumn()+2 
                                        && gameMLL.isExist(new Board(new Location(((int)inPanel.location.getRow())-65, selectedPeg.location.getColumn()+1)))) {
                                    
                                    Board middleBoard = new Board(new Location(((int)inPanel.location.getRow())-65, selectedPeg.location.getColumn()+1));
                                    arrangements(middleBoard, inPanel);
                                }
                                else if(inPanel.location.getRow() == selectedPeg.location.getRow() 
                                        && inPanel.location.getColumn() == selectedPeg.location.getColumn()-2
                                        && gameMLL.isExist(new Board(new Location(((int)inPanel.location.getRow())-65, selectedPeg.location.getColumn()-1)))) {
                                    
                                    Board middleBoard = new Board(new Location(((int)inPanel.location.getRow())-65, selectedPeg.location.getColumn()-1));
                                    arrangements(middleBoard, inPanel);
                                }
                                else if(inPanel.location.getRow() == (char)(selectedPeg.location.getRow()+2)
                                        && inPanel.location.getColumn() == selectedPeg.location.getColumn()
                                        && gameMLL.isExist(new Board(new Location(((int)inPanel.location.getRow())-66, selectedPeg.location.getColumn()))) ) {
                                    
                                    Board middleBoard = new Board(new Location(((int)inPanel.location.getRow())-66, selectedPeg.location.getColumn()));
                                    arrangements(middleBoard, inPanel);
                                }
                                else if(inPanel.location.getRow() == (char)(selectedPeg.location.getRow()-2)
                                        && inPanel.location.getColumn() == selectedPeg.location.getColumn()
                                        && gameMLL.isExist(new Board(new Location(((int)inPanel.location.getRow())-64, selectedPeg.location.getColumn()))) ) {
                                    
                                    Board middleBoard = new Board(new Location(((int)inPanel.location.getRow())-64, selectedPeg.location.getColumn()));
                                    arrangements(middleBoard, inPanel);
                                }
                            }  
                        } 
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                       
                    }
                });
                
                inPanel.setBackground(counter++ % 2 == 0 ? new Color(181,135,99) : new Color(240,218,181));
                
                GridBagLayout gbl = new GridBagLayout();
                if((x != n/2 || y != n/2) && (x != n/2-1 || y != n/2-1) && (x != n/2-1 || y != n/2) && (x != n/2 || y != n/2-1)) {
                    inPanel.setLayout(gbl);
                    
                    Peg peg = new Peg(new Location(x, y));
                    peg.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            selectedPeg = peg;
                            lbl_selectedPegValue.setText(selectedPeg.location.toString());                            
                        }
                    });
                    
                    inPanel.peg = peg;
                    peg.board = inPanel;
                    inPanel.add(peg,new GridBagConstraints());
                    
                    if(x != 0 || y != 0) {
                        gameMLL.add(inPanel);
                    }
                }
                newPanel.add(inPanel);
            }
            counter++;
        }
        
        pegLeft = gameMLL.pegsLeft();
        lbl_pegLeftValue.setText(String.valueOf(pegLeft));
        //update
        pnl_game.revalidate();
        pnl_game.repaint();
    }
    
    private void arrangements(Board middleBoard, Board inPanel) {

        middleBoard = gameMLL.getBoard(middleBoard.location);
        gameMLL.remove(selectedPeg.board);
        gameMLL.add(inPanel);
        selectedPeg.board.removePeg();
        inPanel.addPeg(selectedPeg);

        middleBoard.removePeg();
        gameMLL.remove(middleBoard);

        pegLeft = gameMLL.pegsLeft();
        lbl_pegLeftValue.setText(String.valueOf(pegLeft));
        lbl_selectedPegValue.setText(String.valueOf(selectedPeg.location.toString()));
        if (gameMLL.isGameFinished()) {
            gameFinished();
        }
    }
    
    public void gameFinished() {
        String msg = "Kalan Peg: "+pegLeft+"\nYeni Bir Oyun Başlatmak İster misiniz?";
        int choice = JOptionPane.showConfirmDialog(rootPane, msg, "Oyunu Tamamladınız", JOptionPane.YES_NO_OPTION);
        if(choice == 0) {
            createGame();
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

        pnl_game = new javax.swing.JPanel();
        spn_gameSize = new javax.swing.JSpinner();
        btn_newGame = new javax.swing.JButton();
        lbl_selectedPeg = new javax.swing.JLabel();
        lbl_selectedPegValue = new javax.swing.JLabel();
        lbl_pegLeft = new javax.swing.JLabel();
        lbl_pegLeftValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Solo Noble");
        setResizable(false);

        pnl_game.setLayout(new java.awt.GridLayout(1, 0));

        spn_gameSize.setModel(new javax.swing.SpinnerNumberModel(6, 6, null, 2));

        btn_newGame.setText("Yeni Oyun");
        btn_newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newGameActionPerformed(evt);
            }
        });

        lbl_selectedPeg.setText("Selected:");

        lbl_selectedPegValue.setText("none");

        lbl_pegLeft.setText("Peg Left:");

        lbl_pegLeftValue.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_game, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(spn_gameSize, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_newGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_selectedPeg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_selectedPegValue))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_pegLeft)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_pegLeftValue)))
                .addGap(91, 91, 91))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spn_gameSize, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_newGame)
                    .addComponent(lbl_selectedPeg)
                    .addComponent(lbl_selectedPegValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_pegLeft)
                    .addComponent(lbl_pegLeftValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(pnl_game, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newGameActionPerformed
        n = Integer.parseInt(spn_gameSize.getValue().toString());
        createGame();
    }//GEN-LAST:event_btn_newGameActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_newGame;
    private javax.swing.JLabel lbl_pegLeft;
    private javax.swing.JLabel lbl_pegLeftValue;
    private javax.swing.JLabel lbl_selectedPeg;
    private javax.swing.JLabel lbl_selectedPegValue;
    private javax.swing.JPanel pnl_game;
    private javax.swing.JSpinner spn_gameSize;
    // End of variables declaration//GEN-END:variables
}
