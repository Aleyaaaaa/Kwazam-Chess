import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

//Najmi and Lineysha
/** the MVC pattern: Acts as the View.*/
// Display the chessboard, visually represent the current state of the game
public class BoardView extends JFrame implements ComponentListener {
    private Board board;
    private JButton[][] buttons;
    private JMenuBar menuBar;
    private JLabel turnPlayerLabel;
    private JButton songButton;
    JMenu menuList;
    JMenuItem newGameItem;
    JMenuItem exitItem;
    JMenuItem resignItem; 
    JMenuItem homeItem; 

    private int iconSize = 64;
    private Piece clickedPiece = null; // Track the selected piece
    private int selectedRow = -1, selectedCol = -1; // Track the clicked piece's position

    public BoardView(Board board) {
        this.board = board;
        initializeGUI();
        addComponentListener(this);
    }

    //Najmi
    // Intialize the board with GUI
    private void initializeGUI() {
        // Set up GUI
        setLayout(new GridBagLayout());
        GridBagConstraints boardGrid = new GridBagConstraints();

        //create chessboard panel
        JPanel boardPanel = new JPanel(new GridLayout(8,5)){
            //paint board
            @Override
            protected void paintComponent(Graphics canvas) {
                super.paintComponent(canvas);
                Graphics2D colorTiles = (Graphics2D) canvas;
        
                for(int row = 0; row < 8; row++) {
                    for(int col = 0; col < 5; col++) {
                        colorTiles.setColor((col+row) % 2 == 0? new Color(255, 253, 208) : new Color(173, 216, 230));
                        int tileWidth = getWidth()/5;
                        int tileHeight = getHeight()/8;
                        colorTiles.fillRect(col*tileWidth, row*tileHeight, tileWidth, tileHeight);
                    }
                }
                
                // Highlight valid moves for pices
                if (clickedPiece != null) {
                    if (clickedPiece.getColor() == board.getCurrentPlayer()) {
                        int tileWidth = getWidth() / 5;
                        int tileHeight = getHeight() / 8;

                        for (int row = 0; row < 8; row++) {
                            for (int col = 0; col < 5; col++) {
                                if (clickedPiece.isValidMove(selectedRow, selectedCol, row, col)) {
                                    if (!clickedPiece.moveCollidesWithPieces(board, selectedRow, selectedCol, row, col)) { //check collides pieces
                                        colorTiles.setColor(new Color(68, 180, 57, 190)); // Semi-transparent green
                                        colorTiles.fillRect(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
                                    }
                                }
                            }
                        }           
                    }
                }       
            }
        };
         
        buttons = new JButton[8][5];
        
        // Create menu bar
        menuBar = new JMenuBar(); 

        // Create the menu button in menu bar
        menuList = new JMenu("MENU");
        menuList.setFont(new Font("Courier", Font.BOLD, 16));
        menuList.setForeground(Color.WHITE); // Text color
        menuList.setBackground(new Color(128, 0, 128)); 
        menuList.setOpaque(true); // Make the background visible

        // Add padding around the text
        menuList.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        menuBar.add(menuList);

        // Create the drop-down menu item
        homeItem = new JMenuItem("Home");
        newGameItem = new JMenuItem("New Game");
        exitItem = new JMenuItem("Save and Exit");
        resignItem = new JMenuItem("Resign"); 
        
        // Add the drop-down menu item to menu
        menuList.add(newGameItem);
        menuList.add(exitItem);
        menuList.add(resignItem); // Add Resign option
        menuList.add(homeItem);   // Add Home option

        //song button
        songButton = new JButton("Play Song");
        songButton.setFont(new Font("Courier", Font.BOLD, 16));
        songButton.setForeground(Color.WHITE); // Text color
        songButton.setBackground(new Color(189, 79, 0)); // orange button
        songButton.setOpaque(true);
        songButton.setFocusPainted(false);

        menuBar.add(songButton);

        
        //player turn text
        turnPlayerLabel = new JLabel("Player Turn: BLUE"); 
        turnPlayerLabel.setFont(new Font("Courier", Font.BOLD, 17)); 
        turnPlayerLabel.setForeground(new Color(6, 105, 212)); 
        turnPlayerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Padding from the right
        menuBar.add(Box.createHorizontalGlue()); // Push to the right
        menuBar.add(turnPlayerLabel);

        // Create buttons for each position on the chessboard
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setOpaque(false);
                buttons[row][col].setContentAreaFilled(false);
                buttons[row][col].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 3));
                boardPanel.add(buttons[row][col]);

                // Set the button's icon based on the piece at this position
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    buttons[row][col].setIcon(resizeIcon(piece.getIcon(), 50, 50));
                }
            }
        }

        // Set the grid constraints for the chessboard panel
        boardGrid.gridx = 0;
        boardGrid.gridy = 0;
        boardGrid.weightx=1.0;
        boardGrid.weighty=1.0;
        boardGrid.fill = GridBagConstraints.NONE;
        boardGrid.anchor = GridBagConstraints.CENTER;
        add(boardPanel, boardGrid);
        
        //main window properties
        setTitle("Kwazam Chess");
        pack();
        setSize(700, 600); // Adjust the width and height to suit your screen
        setResizable(true); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setJMenuBar(menuBar);
    }
    
    //Najmi
    public void addButtonListener(int row, int col, ActionListener listener) {
        buttons[row][col].addActionListener(e -> {
            highlightValidMoves(row, col); // Highlight valid moves
            listener.actionPerformed(e); //  ActionListener for all rows/cols
            
        });
    }
    
    //Najmi
    public void addNewGameListener(ActionListener NewGameListener){
        newGameItem.addActionListener(NewGameListener); //  ActionListener for new game 
    }
    public void addExitGameListener(ActionListener ExitGameListener){
        exitItem.addActionListener(ExitGameListener); //  ActionListener for exit game
    }
    public void addResignListener(ActionListener resignListener) {
        resignItem.addActionListener(resignListener); //  Resign ActionListener
    }
    public void addHomeListener(ActionListener homeListener) {
        homeItem.addActionListener(homeListener); //  Home ActionListener
    }
    
    /**
     * Demonstrates the Observer pattern.
     * This method updates the GUI to reflect changes in the game state
     * its called by the Controller whenever the model changes
     */
    // Update the icons on the buttons based on the current state of the board (after movements)
    //Aleya
    public void updateGUI() {
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    buttons[row][col].setIcon(resizeIcon(piece.getIcon(), iconSize, iconSize));
                } else {
                    buttons[row][col].setIcon(null); // Clear the icon if there is no piece
                }
            }
        }
        // Repaint the GUI to reflect the changes
        revalidate();
        repaint();
    }

    //Aleya
    private ImageIcon resizeIcon(ImageIcon icon, int buttonWidth, int buttonHeight) {
        // Resize the given icon to the specified width and height
        Image resizedImage = icon.getImage().getScaledInstance(buttonWidth - 15, buttonHeight - 15, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    
    //Aleya
    @Override
    public void componentResized(ComponentEvent e){
        // Update the icon size based on the frame size
        iconSize = Math.min(getWidth() / 5, getHeight() / 8);
        // Update all the button images with the new icon size
        updateGUI();
    }

    //Najmi
    public void highlightValidMoves(int row, int col) {
        Piece piece = board.getPiece(row, col);   
        // If a piece is clicked, store it for highlighting
        if (piece != null) {
            clickedPiece = piece;
            selectedRow = row;
            selectedCol = col;
        } else {
            clickedPiece = null; // Clear selection if no piece is clicked
            selectedRow = -1;
            selectedCol = -1;
        }
        repaint(); // Trigger repaint to show highlights
    } 

    //Batrisya
    public void updateTurnPlayerLabel(String playerColor) {
        turnPlayerLabel.setText("Player Turn: " + playerColor.toUpperCase());
        turnPlayerLabel.setForeground(playerColor.equalsIgnoreCase("BLUE") ? new Color(6, 105, 212) : new Color(219, 11, 32));
    }

    //Najmi
    public JButton getSongButton() {
        return songButton;
    }
    
    //not used in this implementation
    @Override
    public void componentMoved(ComponentEvent e){
    }

    public void componentShown(ComponentEvent e){
    }

    public void componentHidden(ComponentEvent e){
    }
}
