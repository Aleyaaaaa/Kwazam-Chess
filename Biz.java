//Aleya
//Represent an Biz chess piece
public class Biz extends Piece {
    public Biz(PieceColor color, String iconPath) {
        super(PieceType.BIZ, color, iconPath);
    }

    /**
     * Implements movement rules specific to the Biz piece
     * Part of the Strategy pattern, where each subclass defines its own
     * movement behavior.
     */
    @Override
    // Check if the movement is valid based on piece's logic
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        // Check if the move is either 2 rows and 1 column or 1 row and 2 columns
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

    //Lineysha
    @Override
    //Checks if the move of the Biz piece collides with another piece on the board
    public boolean moveCollidesWithPieces(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = board.getPiece(toRow, toCol);

        // If there is a piece at the target position and it's the same color, stop the move
        if (piece != null && piece.getColor() == this.getColor()) {
            return true; // Stop at same-color piece
        }

        return false; // No collision detected for Biz
    }
}
