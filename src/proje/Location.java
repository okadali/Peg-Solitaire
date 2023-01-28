package proje;

public class Location {
    private char row;
    private int column;

    public Location(int row, int column) {
        this.row = numberToLetter(row);
        this.column = column;
    }
    
    private char numberToLetter(int row) {
        return (char)(65 + row);
    }
    
    public int letterToNumber() {
        return ((int)row)-65;
    }

    @Override
    public String toString() {
        return row+""+column;
    }

    public int getColumn() {
        return column;
    }

    public char getRow() {
        return row;
    } 
}
