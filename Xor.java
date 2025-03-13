//Najmi
// Represent a XOR chess piece 
public class Xor extends Piece {
    public Xor(PieceColor color, String iconPath) {
        super(PieceType.XOR, color, iconPath);
    }

    /**
     * Implements movement rules specific to the Xor piece.
     * Part of the Strategy pattern, where each subclass defines its own
     * movement behavior.
     */
    @Override
    // Check if the movement is valid based on piece's logic
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Calculate the absolute differences in row and column positions
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // Move diagonally (equal row and column differences)
        return rowDiff == colDiff && (fromRow != toRow || fromCol != toCol);
    }
    
    //Lineysha
    //Checks if the move of the XOR piece collides with another piece on the board
    @Override
    public boolean moveCollidesWithPieces(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // Up-left
        if (fromCol > toCol && fromRow > toRow) {
            for (int i = 1; i <= Math.abs(fromCol - toCol); i++) {
                Piece piece = board.getPiece(fromRow - i, fromCol - i);
                if (piece != null) {
                    if (piece.getColor() == this.getColor()) {
                        return true; // Stop at same-color piece
                    }
                    else if (fromRow - i == toRow && fromCol - i == toCol) {
                        return false; // Allow move to opponent piece
                    }
                    return true; // Stop after opponent piece
                }
            }
        }
        // Up-right
        if (fromCol < toCol && fromRow > toRow) {
            for (int i = 1; i <= Math.abs(fromCol - toCol); i++) {
                Piece piece = board.getPiece(fromRow - i, fromCol + i);
                if (piece != null) {
                    if (piece.getColor() == this.getColor()) {
                        return true; // Stop at same-color piece
                    }
                    else if (fromRow - i == toRow && fromCol + i == toCol) {
                        return false; // Allow move to opponent piece
                    }
                    return true; // Stop after opponent piece
                }
            }
        }
        // Down-left
        if (fromCol > toCol && fromRow < toRow) {
            for (int i = 1; i <= Math.abs(fromCol - toCol); i++) {
                Piece piece = board.getPiece(fromRow + i, fromCol - i);
                if (piece != null) {
                    if (piece.getColor() == this.getColor()) {
                        return true; // Stop at same-color piece
                    }
                    else if (fromRow + i == toRow && fromCol - i == toCol) {
                        return false; // Allow move to opponent piece
                    }
                    return true; // Stop after opponent piece
                }
            }
        }
        // Down-right
        if (fromCol < toCol && fromRow < toRow) {
            for (int i = 1; i <= Math.abs(fromCol - toCol); i++) {
                Piece piece = board.getPiece(fromRow + i, fromCol + i);
                if (piece != null) {
                    if (piece.getColor() == this.getColor()) {
                        return true; // Stop at same-color piece
                    }
                    else if (fromRow + i == toRow && fromCol + i == toCol) {
                        return false; // Allow move to opponent piece
                    }
                    return true; // Stop after opponent piece
                }
            }
        }
        return false; // No collision
    }
    
}
