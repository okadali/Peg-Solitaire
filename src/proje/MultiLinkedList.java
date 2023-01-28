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
public class MultiLinkedList {
    Node head;

    public MultiLinkedList(Board board) {
        head = new Node(board, null, null);
    }
    
    public void add(Board newBoard) {
        Node temp = head;
        if(head != null) {
            if(isNewHeader(newBoard)) {
                if(head.board.location.letterToNumber() > newBoard.location.letterToNumber()) {
                    Node newNode = new Node(newBoard, null, null);
                    newNode.next = head;
                    head = newNode;
                }
                else {
                    Node newNode = new Node(newBoard, null, null);
                    while(true) {
                        if(temp.next != null) {
                            if(temp.next.board.location.letterToNumber() < newBoard.location.letterToNumber()) {
                                temp = temp.next;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    newNode.next = temp.next;
                    temp.next = newNode;
                }
            }
            else {
                //Finding Column
                Node newNode = new Node(newBoard, null, null);
                Node tempPrev = head;
                int x = 0;
                while (temp.board.location.getRow() != newNode.board.location.getRow()) {
                    temp = temp.next;
                    if(x++ != 0) {
                        tempPrev = tempPrev.next;
                    }
                }
                if(temp.board.location.getColumn()> newNode.board.location.getColumn()) {
                    newNode.child = temp;
                    newNode.next = temp.next;
                    temp.next = null;
                    if(x != 0) {
                        tempPrev.next = newNode;
                    }
                    else {
                        head = newNode;
                    }
                }
                else if (temp.board.location.getColumn() < newNode.board.location.getColumn()) {
                    while(true) {
                        if(temp.child != null) {
                            if(temp.child.board.location.getColumn()< newBoard.location.getColumn()) {
                                temp = temp.child;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    newNode.child = temp.child;
                    temp.child = newNode;
                }
                else {
                    System.out.println("MLL Satır: 87");
                }
            }
        }
        else {
            head = new Node(newBoard, null, null);
        }
    }
    
    public void remove(Board board) {
        Node temp = head;
        Node tempPrev = head;
        
        boolean isHeader = true;
        
        int x = 0;
        while (!temp.board.location.toString().equals(board.location.toString())) {
            if(temp.next != null) {
               temp = temp.next; 
            }
            else {
                isHeader = false;
                break;
            }
            if(x++ != 0) {
                tempPrev = tempPrev.next;
            }
        }
        
        
        if(isHeader) {
            //başta ve boş
            if(x == 0 && temp.child == null) {
                if(temp.next !=  null) {
                    head = temp.next;
                }
                else {
                    head = null;
                }
            }
            //başta ve dolu
            else if(x == 0 && temp.child != null) {
                if(temp.next != null) {
                    temp.child.next = temp.next;
                }
                head = temp.child;
            }
            //başta değil ve boş
            else if(temp.child == null) {
                tempPrev.next = temp.next;
            }
            //başta değil ve dolu
            else {
                temp.child.next = temp.next;
                tempPrev.next = temp.child;
            }
        }
        else {
            temp = head;
            tempPrev = head;
            
            x = 0;
            
            while(temp.board.location.getRow() != board.location.getRow()) {
                temp = temp.next;
                tempPrev = tempPrev.next;
            }
            while(temp.board.location.getColumn() != board.location.getColumn()) {
                temp = temp.child;
                if(x++ != 0) {
                    tempPrev = tempPrev.child;
                }
            }
            tempPrev.child = temp.child;
        }
    }
    
    public boolean isExist(Board board) {
        if(!isNewHeader(board)) {
            Node temp = head;
            while(board.location.getRow() != temp.board.location.getRow()) {
                if(temp.next != null) {
                    temp = temp.next;
                }
                else {
                    return false;
                }
            }
            while(board.location.getColumn() != temp.board.location.getColumn()) {
                if(temp.child != null) {
                    temp = temp.child;
                }
                else {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }  
    }
    
    public Board getBoard(Location location) {
        Board board = new Board(location);
        if(!isNewHeader(board)) {
            Node temp = head;
            while(board.location.getRow() != temp.board.location.getRow()) {
                if(temp.next != null) {
                    temp = temp.next;
                }
                else {
                    return null;
                }
            }
            while(board.location.getColumn() != temp.board.location.getColumn()) {
                if(temp.child != null) {
                    temp = temp.child;
                }
                else {
                    return null;
                }
            }
            return temp.board;
        }
        else {
            return null;
        }  
    }
    
    public boolean isGameFinished() {
        Node tempHead = head;
        Node tempBody = head;
        if(head != null) {
            while(tempHead != null) {
                tempBody = tempHead;
                while(tempBody != null) {
                    if(this.isExist(new Board(new Location(((int)tempBody.board.location.getRow())-65, tempBody.board.location.getColumn()+1)))
                       && !this.isExist(new Board(new Location(((int)tempBody.board.location.getRow())-65, tempBody.board.location.getColumn()+2)))) {
                        return false;
                    }
                    else if(this.isExist(new Board(new Location(((int)tempBody.board.location.getRow())-65, tempBody.board.location.getColumn()-1)))
                       && !this.isExist(new Board(new Location(((int)tempBody.board.location.getRow())-65, tempBody.board.location.getColumn()-2)))) {
                        return false;
                    }
                    else if(this.isExist(new Board(new Location(((int)tempBody.board.location.getRow())-66, tempBody.board.location.getColumn())))
                       && !this.isExist(new Board(new Location(((int)tempBody.board.location.getRow())-67, tempBody.board.location.getColumn())))) {
                        return false;
                    }
                    else if(this.isExist(new Board(new Location(((int)tempBody.board.location.getRow())-64, tempBody.board.location.getColumn())))
                       && !this.isExist(new Board(new Location(((int)tempBody.board.location.getRow())-63, tempBody.board.location.getColumn())))) {
                        return false;
                    }
                    
                    tempBody = tempBody.child;
                }
                tempHead = tempHead.next;
            }
        }
        return true;
    }
    
    public int pegsLeft() {
        int leftPeg = 0;
        Node tempHead = head;
        Node tempBody = head;
        if(head != null) {
            while(tempHead != null) {
                tempBody = tempHead;
                while(tempBody != null) {
                    leftPeg++;
                    tempBody = tempBody.child;
                }
                tempHead = tempHead.next;
            }
        }
        return leftPeg;
    }
    
    private boolean isNewHeader(Board board) {
        Node temp = head;
        while(temp.board.location.letterToNumber() != board.location.letterToNumber()) {
           if(temp.next != null) {
               temp = temp.next;
           }
           else{
               return true;
           }
        }
        return false;
    }
     
}
