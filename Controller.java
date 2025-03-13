import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.sound.sampled.*;

//Aleya
/**
 * Part of the MVC pattern: Acts as the Controller.
 * handles user input, updates the model (Game), and refreshes the view (BoardView).
 */
// Handle user input and update the game state accordingly
public class Controller {
    private BoardView boardView;
    private Game game; 
    private Clip song; // to play  song
    private boolean isPlaying = false; // track song state

    // Constructor takes an instance of the board and board view
    public Controller(Board board, BoardView boardView) {
        this.boardView = boardView;
        this.game = new Game(board, boardView); // Initialize the Game class
        initializeAudio();
        initializeButtonListeners();
        menuBarListener();
    }

    // Constructor takes an instance of the board, board view, and a string parameter
    public Controller(Board board, BoardView boardView, String x) {
        this.boardView = boardView;
        this.game = new Game(board); // Initialize the Game class
        initializeAudio();
        initializeButtonListeners();
        menuBarListener();
    }

    // Initialize button listeners for the board
    //Najmi
    private void initializeButtonListeners() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                boardView.addButtonListener(row, col, new ButtonClickListener(row, col));
            }
        }
        addSongButtonListener();
    }

    // Menu bar listener
    //Batrisya
    private void menuBarListener(){
        boardView.addNewGameListener(new NewGameListener());
        boardView.addExitGameListener(new ExitGameListener());
        boardView.addResignListener(new ResignListener()); 
        boardView.addHomeListener(new HomeListener());     
    }

    //Batrisya
    // ActionListener implementation for the board buttons
    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        // Constructor for the ButtonClickListener
        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // Handle the button click event
        @Override
        public void actionPerformed(ActionEvent e) {
            // Assign the button click handling to the Game class
            game.handleButtonClick(row, col);

            // Update the GUI
            updateView();
        }
    }

    //Batrisya
    private class NewGameListener implements ActionListener{
        // Handle the "New Game" menu item click event
        public void actionPerformed(ActionEvent e){
            game.newGame();

            // Update GUI
            boardView.updateGUI();
        }
    }

   //Batrisya
    private class ExitGameListener implements ActionListener{
        // Handle the "Exit" menu item click event and exit the program
        public void actionPerformed(ActionEvent e){
            game.saveGame(); 
            System.exit(0);
        }
    }

    // ResignListener
    //Aleya
    private class ResignListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String loser = game.getCurrentPlayer() == Piece.PieceColor.RED ? "RED" : "BLUE";
            String winner = game.getCurrentPlayer() == Piece.PieceColor.RED ? "BLUE" : "RED";

            game.clearGameStateFile(); // Call the public method from Game.java
            // Print the surrender message to the console
            System.out.println(loser + " surrendered! " + winner + " wins!");

            // Display a fun message
            JOptionPane.showMessageDialog(
                null,
                loser + " player has surrendered! Guess they needed a snack break. " + winner + " wins!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
            );
            // Go back to the home screen
            boardView.dispose(); // Close the current game window
            MenuView menuView = new MenuView(); // Open the main menu
            new MenuController(menuView); // Attach the menu controller
        }
    }

    // HomeListener
    //Lineysha
    private class HomeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boardView.dispose(); // Close the current game window
            MenuView menuView = new MenuView(); // Open the main menu
            new MenuController(menuView); // Attach the controller for menu functionality
        }
    }

    /**
     * Demonstrates the Observer pattern.
     * Notifies the view (BoardView) to update based on changes in the model (Game)
     */
    // Update the GUI based on the current state of the board
    //Aleya
    private void updateView() {
        boardView.updateGUI();
        boardView.updateTurnPlayerLabel(game.getCurrentPlayer().name());
    }

    //Najmi
    private void initializeAudio() {
        try {
            // load the audio file
            AudioInputStream inputSong = AudioSystem.getAudioInputStream(getClass().getResource("/content/mingle.wav"));
            song = AudioSystem.getClip();
            song.open(inputSong);
        } catch (Exception e) {
            System.out.println("Error loading audio: " + e.getMessage());
        }
    }
    
    //Najmi
    // listener for the song button
    private void addSongButtonListener() {
        boardView.getSongButton().addActionListener(e -> {
            System.out.println("Song button clicked: " + e.getActionCommand());
            toggleSong();
        });
    }

    //Najmi
    //  to toggle song playback
    private void toggleSong() {
        if (song == null) {
            System.out.println("Audio clip not initialized.");
            return;
        }

        else if (isPlaying) {
            song.stop(); // Stop the song
            isPlaying = false;
            boardView.getSongButton().setText("Play Song");
        } else {
            song.setFramePosition(0); // Reset to the start
            song.start(); // Play the song
            isPlaying = true;
            boardView.getSongButton().setText("Stop Song");
        }
}
}

