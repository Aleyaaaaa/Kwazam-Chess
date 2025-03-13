//Batrisya
/** MVC pattern: acts as the Model*/
// Manage the arrangement and movement of chess pieces on the board (state of board),include positions, current player's turn
public class Board {
    private Piece[][] pieces;

    private Piece.PieceColor currentPlayer;

    //create new board with the specified starting player
    public Board(Piece.PieceColor currentPlayer) {
        this.currentPlayer = currentPlayer;
        pieces = new Piece[8][5];
        initializeBoard();
    }

    public Board() {
        // Initialize the 5x8 board
        pieces = new Piece[8][5];
        initializeBoard();
    }

    //Batrisya
    // Set up the initial positions of pieces on the board
    private void initializeBoard() {
        
        pieces[0][0] = new Tor(Piece.PieceColor.RED, "/content/pieces/redTor.png");
        pieces[0][1] = new Biz(Piece.PieceColor.RED, "/content/pieces/redBiz.png");
        pieces[0][2] = new Sau(Piece.PieceColor.RED, "/content/pieces/redSau.png");
        pieces[0][3] = new Biz(Piece.PieceColor.RED, "/content/pieces/redBiz.png");
        pieces[0][4] = new Xor(Piece.PieceColor.RED, "/content/pieces/redXor.png");
        
        pieces[1][0] = new Ram(Piece.PieceColor.RED, "/content/pieces/redRam.png");
        pieces[1][1] = new Ram(Piece.PieceColor.RED, "/content/pieces/redRam.png");
        pieces[1][2] = new Ram(Piece.PieceColor.RED, "/content/pieces/redRam.png");
        pieces[1][3] = new Ram(Piece.PieceColor.RED, "/content/pieces/redRam.png");
        pieces[1][4] = new Ram(Piece.PieceColor.RED, "/content/pieces/redRam.png");

        pieces[7][0] = new Xor(Piece.PieceColor.BLUE, "/content/pieces/blueXor.png");
        pieces[7][1] = new Biz(Piece.PieceColor.BLUE, "/content/pieces/blueBiz.png");
        pieces[7][2] = new Sau(Piece.PieceColor.BLUE, "/content/pieces/blueSau.png");
        pieces[7][3] = new Biz(Piece.PieceColor.BLUE, "/content/pieces/blueBiz.png");
        pieces[7][4] = new Tor(Piece.PieceColor.BLUE, "/content/pieces/blueTor.png");

        pieces[6][0] = new Ram(Piece.PieceColor.BLUE, "/content/pieces/blueRam.png");
        pieces[6][1] = new Ram(Piece.PieceColor.BLUE, "/content/pieces/blueRam.png");
        pieces[6][2] = new Ram(Piece.PieceColor.BLUE, "/content/pieces/blueRam.png");
        pieces[6][3] = new Ram(Piece.PieceColor.BLUE, "/content/pieces/blueRam.png");
        pieces[6][4] = new Ram(Piece.PieceColor.BLUE, "/content/pieces/blueRam.png");
    }

    //Batrisya
    // Get the piece at a specific position on the board
    public Piece getPiece(int row, int col) {
        return pieces[row][col];
    }

    //Batrisya
    // Put the piece at specific location
    public void setPiece(int row, int col, Piece piece) {
        pieces[row][col] = piece;
    }

    //Batrisya
    // Move a chess piece from one position to another
    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece pieceToMove = pieces[fromRow][fromCol];
        pieces[toRow][toCol] = pieceToMove;
        pieces[fromRow][fromCol] = null; // Clear the original position
    }

    //Aleya
    // Check if the path between two positions is clear for moving a piece
    public boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol) {
        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        for (int i = fromRow + rowStep, j = fromCol + colStep; i != toRow || j != toCol; i += rowStep, j += colStep) {
            if (pieces[i][j] != null) {
                return false;
            }
        }
        return true;
    }

    //Aleya
    // Clear current gameplay and initialize board again
    public void resetBoard(){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                pieces[row][col] = null;
            }
        }
        initializeBoard();
    }

    //Lineysha and Aleya
    public void flipBoard() {
        // Create a rotated version of the board
        Piece[][] flippedBoard = new Piece[8][5];
        
        // Invert the board positions
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                flippedBoard[7-row][4-col] = getPiece(row, col); // Flip both rows and columns
            }
        }
        
        // Clear the current board and set the rotated board
        setBoard(flippedBoard);
        
        // Adjust piece icons if necessary
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                Piece piece = pieces[row][col];
                if (piece != null) {
                    if (currentPlayer == Piece.PieceColor.RED) { // Check if the current player is Red before adjusting icons
                        //Adjust icon paths for flipped pieces
                        if (piece.getType() == Piece.PieceType.SAU) {
                            piece.setIconPath(piece.getColor() == Piece.PieceColor.RED
                                ? "/content/pieces/flipredSau.png"
                                : "/content/pieces/flipblueSau.png");
                        } else if (piece.getType() == Piece.PieceType.RAM) {
                            piece.setIconPath(piece.getColor() == Piece.PieceColor.RED
                                ? "/content/pieces/flipredRam.png"
                                : "/content/pieces/flipblueRam.png");
                        }
                    }
                    else { //original
                        if (piece.getType() == Piece.PieceType.SAU) {
                            piece.setIconPath(piece.getColor() == Piece.PieceColor.RED
                                ? "/content/pieces/redSau.png"
                                : "/content/pieces/blueSau.png");
                        } else if (piece.getType() == Piece.PieceType.RAM) {
                            piece.setIconPath(piece.getColor() == Piece.PieceColor.RED
                                ? "/content/pieces/redRam.png"
                                : "/content/pieces/blueRam.png");
                        }
                    }
                }
            }
        }
    }
 
    //Najmi
    //update the board's state with new arrangement of pieces
    public void setBoard(Piece[][] board){
        this.pieces = board;
    }

    //Najmi
    //set the current player for the game at the time
    public void setCurrentPlayer(Piece.PieceColor currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    //Najmi
    //retrieve the color of the current player
    public Piece.PieceColor getCurrentPlayer() {
        return currentPlayer;
    }
    
}
