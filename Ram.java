//Batrisya
// Represent a Ram chess piece
public class Ram extends Piece {
   private boolean hasMovedInReverse;

   public Ram(PieceColor color, String iconPath) {
       super(PieceType.RAM, color, iconPath);
   }

    /**
     * Implements movement rules specific to the Ram piece.
     * Part of the Strategy pattern, where each subclass defines its own
     * movement behavior.
     */
   @Override
   // Check if the movement is valid based on piece's logic
   public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
       int rowDiff = toRow - fromRow;
       int colDiff = Math.abs(toCol - fromCol);

       // Forward movement
       if (!hasMovedInReverse && rowDiff == -1 && colDiff == 0) { // Only 1 step forward
           return true;
       }
       // Reverse movement when reaching the other end
       if (fromRow == 0 && rowDiff == 1 && colDiff == 0) {
           hasMovedInReverse = true;
           return true;
       }
       // No other movements allowed when in reverse
       return hasMovedInReverse && rowDiff == 1 && colDiff == 0; // Only 1 step backward
   }

    //Lineysha
    //Checks if the move of the ram piece collides with another piece on the board
   @Override
   public boolean moveCollidesWithPieces(Board board, int fromRow, int fromCol, int toRow, int toCol) {
       Piece piece = board.getPiece(toRow, toCol);

       // If there is a piece at the target position and it's the same color, stop the move
       if (piece != null && piece.getColor() == this.getColor()) {
           return true; // Stop at same-color piece
       }
       return false; // No collision detected for Ram
   }
}
