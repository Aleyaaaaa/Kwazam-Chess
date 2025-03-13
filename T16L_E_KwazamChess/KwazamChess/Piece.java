import javax.swing.*;
/**
 * part of the MVC pattern: acts as the Model.
 * base class for chess pieces with shared attributes like color, type, n icon
 * use Strategy pattern for movement rules in subclasses
 * Template Method pattern for shared behavior like collision checking
 */
// The abstract base class for chess pieces
//Aleya
public abstract class Piece {
    private PieceType type;
    private PieceColor color;
    private ImageIcon icon;
    private String iconPath;

    //Constructs a new chess piece
    public Piece(PieceType type, PieceColor color, String iconPath) {
        this.type = type;
        this.color = color;
        this.icon = new ImageIcon(iconPath);
        this.iconPath = iconPath;
        this.icon = new ImageIcon(getClass().getResource(iconPath));
    }

    // Check if a move from one position to another is valid (Subclasses needs to implement)
    public abstract boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol);

    //Lineysha
    /** template method
     * shared implementation to check if a move collides with other pieces
     * Subclasses can override this method if they require this collision behavior
     */
    // default implementation: no collision detection
    public boolean moveCollidesWithPieces(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        return false;
    }
    
    // Getters
    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public String getIconPath() {
        return iconPath;
    }

    //Lineysha
    //updates the file path for the piece's icon and reloads the icon image
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
        this.icon = new ImageIcon(getClass().getResource(iconPath));
    }
    

    //Aleya
    // To represent a fixed set of named values
    public enum PieceType {
        RAM, BIZ, TOR, XOR, SAU
    }

    public enum PieceColor {
        BLUE, RED
    }
}

