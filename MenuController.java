import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

//Aleya and Najmi
/** Part of the MVC pattern: act as the Controller*/
// Handle actions and events related to the main menu
public class MenuController {
    private MenuView view;

    public MenuController(MenuView view) {
        this.view = view;
        addListeners();
    }

    /**
     * implement the Command Pattern
     * links buttons in the menu view to specific actions (commands), like start a new game,
     * load a saved game, or show howtoplay instructions
     */
    //Najmi
    private void addListeners() {

        /** connect menu buttons to their respective commands using ActionListeners.
            demonstrate the Command Pattern by delegate actions to specific methods */
        
        // Add action listeners to the buttons in MenuView (startButton and loadButton)
        view.addButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == view.getStartButton()) {
                    startGame();
                } else if (e.getSource() == view.getLoadButton()) {
                    loadGame();
                }
            }
        });
        view.getHowToPlayButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                howToPlay();
            }
        });
    }

    /**
     * Executes the "Start New Game" command
     * close the current menu view and initializes the game with a new board
     */
    //start new game
    //Aleya
    private void startGame() {
        // Dispose of the main menu view
        view.dispose();
        
        Board board = new Board();
        BoardView boardView = new BoardView(board);
        new Controller(board, boardView, "");
    }
    /**
     * Executes the "Load Game" command
     * close the current menu view and loads a saved game state.
     */
    //load game button function
    //Aleya
    private void loadGame() {
        // Dispose of the main menu view
        view.dispose();
        
        Board board = new Board();
        BoardView boardView = new BoardView(board);
        new Controller(board, boardView);
    }

    /**
     * Executes the "How to Play" command.
     * Displays a popup window with instructions on how to play the game.
     */
    //how to play
    //Najmi
    private void howToPlay() { 
        // Create a popup window
        JFrame howtoPlay = new JFrame("Help");
        howtoPlay.setSize(600, 300); // Adjust width for side-by-side layout
        howtoPlay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        howtoPlay.setLocationRelativeTo(null);
        howtoPlay.setResizable(false);
    
        // Add content to the popup
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout()); // for side-by-side placement
        howtoPlay.add(content);
    
        // vid
        JLabel vid = new JLabel(new ImageIcon(getClass().getResource("/content/vid.gif")));

        vid.setHorizontalAlignment(JLabel.CENTER); 
        content.add(vid, BorderLayout.WEST); // vid to the left side
    
        //  text
        JTextArea textArea = new JTextArea();
        textArea.setText("""
            Welcome to Kwazam Chess! 

            The game begins with the BLUE Player 
            making the first move. Players can 
            only select their own pieces and move 
            them to valid tiles highlighted in green. 
            
            The video beside shows the valid moves 
            for each piece. Invalid tiles cannot be 
            selected, and players can deselect a piece 
            by clicking it again.

            After the BLUE Player completes their turn, 
            the RED Player follows the same rules. 
            The game ends when:

            1.The opponent's Sau Piece is captured.
            2.A player decides to resign.
            """);
        
        textArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for readability
        textArea.setEditable(false); // Make text non-editable
    
        // Wrap the text area in a scroll pane
        JScrollPane roll = new JScrollPane(textArea);
        content.add(roll, BorderLayout.CENTER); // Add text to the center
    
        // Display the window
        howtoPlay.setVisible(true);
    }
    

}

