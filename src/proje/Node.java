/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje;

/**
 *
 * @author omerkerem.adali
 */
public class Node {
    Board board;//data
    Node next;
    Node child;

    public Node(Board board, Node next, Node child) {
        this.board = board;
        this.next = next;
        this.child = child;
    }

    public Node(Board board) {
        this.board = board;
        this.next = null;
        this.child = null;
    }
    
    
}
