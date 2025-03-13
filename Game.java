import java.io.*;
import javax.swing.JOptionPane;

//Lineysha and Aleya
/**
 * MVC pattern: Acts as the Model.
 * this class handles the core game logic, including piece movement, game state, 
 * turn switching, and saving/loading game states
 */
// Represent the game logic and state
public class Game {
    private Board board;
    private Piece.PieceColor currentPlayer = Piece.PieceColor.BLUE;
    private int firstClickRow = -1;
    private int firstClickCol = -1;
    private int moveCounter = 0;

    // Constructor takes an instance of the board
    public Game(Board board) {
        this.board = board;
        board.setCurrentPlayer(currentPlayer);
        System.out.println("\nIt's now " + currentPlayer + "'s turn.");
    }

    //Constructor takes instances of the board and board view
    public Game(Board board, BoardView boardView) {
        this.board = board;
        loadGame();
        boardView.updateGUI();
        System.out.println("\nIt's now " + currentPlayer + "'s turn.");
    }

    //Method to handle button clicks on the board (Can differentiate 1st click & 2nd click)
    //Lineysha
    public void handleButtonClick(int row, int col) {
        if (firstClickRow == -1 && firstClickCol == -1) {
            handleFirstClick(row, col);
        } else {
            handleSecondClick(row, col);
        }
        saveGame();
    }

    //Lineysha
    //Handle the first click on the board
    private void handleFirstClick(int row, int col) {
        Piece clickedPiece = board.getPiece(row, col);

        // Check if the clicked piece is not null and belongs to the current player's turn
        if (clickedPiece != null && clickedPiece.getColor() == currentPlayer) {
            firstClickRow = row;
            firstClickCol = col;
        } else {
            System.out.println("Invalid click. Please select a valid piece.");
        }
    }

    //Lineysha
    // Handle the second click on the board
    private void handleSecondClick(int row, int col) {
        // Check if the second click is a cancellation of the first click
        if (row == firstClickRow && col == firstClickCol) {
            System.out.println("Cancelled selection.");
        } else {
            Piece selectedPiece = board.getPiece(firstClickRow, firstClickCol);

            // Check if the move is valid based on piece rules and the board state
            if (isValidMove(selectedPiece, firstClickRow, firstClickCol, row, col)) {
                Piece targetPiece = board.getPiece(row, col);

                // Check if the target position is empty or has an opponent's piece
                if (targetPiece == null || targetPiece.getColor() != currentPlayer) {
                    // If the target piece exists and belongs to the opponent, it's captured
                    if (targetPiece != null) {
                        System.out.println(targetPiece.getType() + " of " + targetPiece.getColor() + " has been captured at (" + row + ", " + col + ").");
                    }
                    board.movePiece(firstClickRow, firstClickCol, row, col);
                    switchPlayer(); // Switch the player turn after a successful move
                } else {
                    System.out.println("Invalid move! Cannot capture your own piece.");
                }
            } else {
                System.out.println("Invalid move! Please select a valid target.");
            }
        }

        // Reset the first click position after handling the move
        firstClickRow = -1;
        firstClickCol = -1;
    }

    //Najmi
    //Switch the player turn between BLUE and RED, flip the game board, and check for winning condition
    private void switchPlayer() {
        moveCounter++;
        // Print the message at the correct turn
        if (moveCounter % 4 == 0) { // After RED's second turn
        System.out.println("\nXOR and TOR pieces have been switched!\n");
        }

        currentPlayer = (currentPlayer == Piece.PieceColor.BLUE) ? Piece.PieceColor.RED : Piece.PieceColor.BLUE;
        board.setCurrentPlayer(currentPlayer); // Notify the board of the current player
        board.flipBoard(); // Flip the board after switching the player turn
        System.out.println("It's now " + currentPlayer + "'s turn.");

        // Check if the winning condition has been fulfilled
        checkForWinningCondition();
        

        // Check for total moves (To switch xor and tor)
        checkMoves();
    }

    //Aleya
    // Check if the move is valid based on the piece's rules and the board state
    private boolean isValidMove(Piece piece, int fromRow, int fromCol, int toRow, int toCol) {
        Piece clickedPiece = board.getPiece(firstClickRow, firstClickCol);

        // Special rule for Biz (Can skip pieces)
        if (clickedPiece.getType() == Piece.PieceType.BIZ) {
            return piece != null && piece.isValidMove(fromRow, fromCol, toRow, toCol);
        } else {
            return piece != null && piece.isValidMove(fromRow, fromCol, toRow, toCol) &&
                    board.isPathClear(fromRow, fromCol, toRow, toCol);
        }
    }

    //Batrisya
    // Start a new game
    public void newGame() {
        clearGameStateFile();
        board.resetBoard(); // Reset the board to initial state
        moveCounter = 0;
        currentPlayer = Piece.PieceColor.BLUE;
        board.setCurrentPlayer(currentPlayer);

        firstClickRow = -1;
        firstClickCol = -1;

        System.out.println("\nYou started a new game!");
        System.out.println("\nIt's now " + currentPlayer + "'s turn.");
    }

    //Batrisya
    // Save the game state to a file
    public void saveGame() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("gameState.txt", false))) {
            // Save the current player information and turn counter
            writer.write("Current Player: " + currentPlayer.name());
            writer.newLine();
            writer.write("Move Counter: " + moveCounter);
            writer.newLine();
            writer.write("Board State:");
            writer.newLine();

            // Save the board state in a human-readable format
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 5; col++) {
                    Piece piece = board.getPiece(row, col);
                    writer.write("(" + row + ", " + col + ")  ");
                    if (piece != null) {
                        writer.write(piece.getType().name() + " " + piece.getColor().name() + " " + piece.getIconPath());
                    } else {
                        writer.write("EMPTY");
                    }
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    //Batrisya
    /**
     * demonstrates the Factory pattern
     * acts as a factory for creating chess pieces based on their
     * type, color, and icon path when loading the game state
     */
    // Load the game state from a file
    public void loadGame() {
        //for empty file checking
        try (BufferedReader reader = new BufferedReader(new FileReader("gameState.txt"))) {
            if (reader.readLine() == null) { // Check if the file is empty
                System.out.println("No saved file found. Starting a new game...\n");
                newGame(); // Start a new game if the file is empty
                return;
            }
            
            // Rewind to the beginning of the file
            reader.close();
        } catch (IOException e) {
            System.out.println("No saved file found. Starting a new game...\n");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("gameState.txt"))) {
            // Load the current player and move counter
            String currentPlayerInfo = reader.readLine();
            String moveCounterInfo = reader.readLine();

            if (currentPlayerInfo != null && moveCounterInfo != null) {
                currentPlayer = Piece.PieceColor.valueOf(currentPlayerInfo.split(": ")[1]);
                moveCounter = Integer.parseInt(moveCounterInfo.split(": ")[1]);
                board.setCurrentPlayer(currentPlayer); // Update board's current player
            }

            // Skip the "Board State:" header
            reader.readLine();

            // Load the board state
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Remove any extra spaces
                if (!line.isEmpty()) {
                    String[] parts = line.split("  "); // Split into position and piece data
                    String[] position = parts[0].replace("(", "").replace(")", "").split(", ");
                    int row = Integer.parseInt(position[0]);
                    int col = Integer.parseInt(position[1]);

                    if (parts[1].equals("EMPTY")) {
                        board.setPiece(row, col, null); // Set empty square
                    } else {
                        // Parse piece details
                        String[] pieceDetails = parts[1].split(" ");
                        Piece.PieceType type = Piece.PieceType.valueOf(pieceDetails[0]);
                        Piece.PieceColor color = Piece.PieceColor.valueOf(pieceDetails[1]);
                        String iconPath = pieceDetails[2];

                        // Create the piece
                        Piece piece;
                        switch (type) {
                            case BIZ:
                                piece = new Biz(color, iconPath);
                                break;
                            case TOR:
                                piece = new Tor(color, iconPath);
                                break;
                            case RAM:
                                piece = new Ram(color, iconPath);
                                break;
                            case XOR:
                                piece = new Xor(color, iconPath);
                                break;
                            case SAU:
                                piece = new Sau(color, iconPath);
                                break;
                            default:
                                throw new IllegalArgumentException("Unknown PieceType: " + type);
                        }
                        board.setPiece(row, col, piece);
                    }
                }
            }
            System.out.println("\nSaved file found. Loading the previous game...");
        } catch (IOException e) {
            System.out.println("No saved file found. Starting a new game...\n");
        } catch (Exception e) {
            System.out.println("Error loading game state: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //Aleya
    //Check for the winning condition
    private void checkForWinningCondition() {
        if (!isSauPieceOnBoard(Piece.PieceColor.BLUE)) {
            System.out.println("\nRED wins! The Sau piece has been captured.");
            clearGameStateFile();
            displayWinner("RED");

        } else if (!isSauPieceOnBoard(Piece.PieceColor.RED)) {
            System.out.println("\nBLUE wins! The Sau piece has been captured.");
            clearGameStateFile();
            displayWinner("BLUE");
        }
    }

    //Aleya
    // Method to display the winner and options to start a new game or quit
    private void displayWinner(String winner) {
        String message = winner + " wins! The Sau piece has been captured.";
        Object[] options = {"Restart", "Quit"};
        int choice = JOptionPane.showOptionDialog(null, message, "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == JOptionPane.YES_OPTION) {
            newGame();
        } else {
            System.exit(0);
        }
    }
    
    //Aleya
    // Check if the Sau piece of the specified color is still on the board (Winning rule)
    private boolean isSauPieceOnBoard(Piece.PieceColor color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && piece.getType() == Piece.PieceType.SAU && piece.getColor() == color) {
                    return true; // Sau piece found, game ends
                }
            }
        }
        return false; // Sau piece not found, game continue
    }

    //Batrisya
    // Clear the text file (Saved game)
    public void clearGameStateFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("gameState.txt"))) {
            writer.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Najmi
    //  Switch TOR and XOR pieces
    private void switchPieces() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                Piece piece = board.getPiece(row, col);
    
                if (piece != null) {
                    if (piece.getType() == Piece.PieceType.XOR) {
                        convertXorPiece(piece, row, col);
                    } else if (piece.getType() == Piece.PieceType.TOR) {
                        convertTorPiece(piece, row, col);
                    }
                }
            }
        }
    }
    
    //Najmi
    // Convert XOR piece to TOR piece
    private void convertXorPiece(Piece xorPiece, int row, int col) {
        Piece.PieceColor color = xorPiece.getColor();
        String torIconPath = (color == Piece.PieceColor.RED) ? "/content/pieces/redTor.png" : "/content/pieces/blueTor.png";
        board.setPiece(row, col, new Tor(color, torIconPath));
    }
    
    //Najmi
    // Convert TOR piece to XOR piece
    private void convertTorPiece(Piece torPiece, int row, int col) {
        Piece.PieceColor color = torPiece.getColor();
        String xorIconPath = (color == Piece.PieceColor.RED) ? "/content/pieces/redXor.png" : "content/pieces/blueXor.png";
        board.setPiece(row, col, new Xor(color, xorIconPath));
    }    

    //Najmi
    // Check if the total moves has fulfilled the specified condition
    private void checkMoves() {
        // Check if four moves have passed
        if (moveCounter % 4 == 0) {
            switchPieces();
            
        }
    }    

    //Najmi
    public Piece.PieceColor getCurrentPlayer() {
        return board.getCurrentPlayer(); // Fetch the current player from the Board class
    }
    
}
