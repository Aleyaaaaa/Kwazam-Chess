//Batrisya
//Represent a Sau chess piece
public class Sau extends Piece {
    public Sau(PieceColor color, String iconPath) {
        super(PieceType.SAU, color, iconPath);
    }

    /**
     * Implements movement rules specific to the Sau piece.
     * Part of the Strategy pattern, where each subclass defines its own
     * movement behavior.
     */
    @Override
    // Check if the movement is valid based on piece's logic
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Calculate the absolute differences in row and column positions
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // Move to any adjacent square (up, down, left, right, or diagonally)
        return rowDiff <= 1 && colDiff <= 1;
    }

    //Lineysha
    //Checks if the move of the Sau piece collides with another piece on the board
    @Override
    public boolean moveCollidesWithPieces(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = board.getPiece(toRow, toCol);

        // If there is a piece at the target position and it's the same color, stop the move
        if (piece != null && piece.getColor() == this.getColor()) {
            return true; // Stop at same-color piece
        }
        return false; // No collision detected for Sau
    }
}
