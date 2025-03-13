//Najmi
// Represent a TOR chess piece
public class Tor extends Piece {
    public Tor(PieceColor color, String iconPath) {
        super(PieceType.TOR, color, iconPath);
    }

    /**
     * Implements movement rules specific to the Tor piece.
     * Part of the Strategy pattern, where each subclass defines its own
     * movement behavior.
     */

    @Override
    // Check if the movement is valid based on piece's logic
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Move either vertically or horizontally, but not diagonally
        if (fromRow == toRow || fromCol == toCol) {
            // Prevent highlighting the current position
            return fromRow != toRow || fromCol != toCol;
        }
        return false;
    }

    //Lineysha
    //Checks if the move of the Tor piece collides with another piece on the board
    @Override
    public boolean moveCollidesWithPieces(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // Left (horizontal)
        if (fromCol > toCol) {
            for (int c = fromCol - 1; c >= toCol; c--) {
                Piece piece = board.getPiece(fromRow, c);
                if (piece != null) {
                    if (piece.getColor() == this.getColor()) {
                        return true; // Stop at same-color piece
                    }
                    else if (c == toCol) {
                        return false; // Stop at the opponent piece
                    }
                    return true;
                }
            }
        }

        // Right (horizontal)
        if (fromCol < toCol) {
            for (int c = fromCol + 1; c <= toCol; c++) {
                Piece piece = board.getPiece(fromRow, c);
                if (piece != null) {
                    if (piece.getColor() == this.getColor()) {
                        return true; // Stop at same-color piece
                    }
                    else if (c == toCol) {
                        return false; // Stop at the opponent piece
                    }
                    return true;
                }
            }
        }

        // Up (vertical)
        if (fromRow > toRow) {
            for (int r = fromRow - 1; r >= toRow; r--) {
                Piece piece = board.getPiece(r, fromCol);
                if (piece != null) {
                    if (piece.getColor() == this.getColor()) {
                        return true; // Stop at same-color piece
                    }
                    else if (r == toRow) {
                        return false; // Stop at the opponent piece
                    }
                    return true;
                }
            }
        }

        // Down (vertical)
        if (fromRow < toRow) {
            for (int r = fromRow + 1; r <= toRow; r++) {
                Piece piece = board.getPiece(r, fromCol);
                if (piece != null) {
                    if (piece.getColor() == this.getColor()) {
                        return true; // Stop at same-color piece
                    }
                    else if (r == toRow) {
                        return false; // Stop at the opponent piece
                    }
                    return true;
                }
            }
        }

        return false; // No collision
    }

}
