import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Batrisya and Lineysha
/**
 * the MVC pattern: Acts as the View.
 * manage GUI for main menu, display buttons for actions like
 */
// Represent the GUI for the main menu
public class MenuView {
    private JFrame frame;
    private JButton startButton;
    private JButton loadButton;
    private JButton howToPlayButton;

    public MenuView() {
        initializeGUI();
    }

    //Najmi
    // Set up the main frame and initialize GUI
    private void initializeGUI() {
        frame = new JFrame("Kwazam Chess");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        // Set the background image
        ImageIcon backgroundImg = new ImageIcon(getClass().getResource("/content/chesspic2.png"));


        // Resize the image directly using ImageIcon
        Image resizedImg = backgroundImg.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
        backgroundImg = new ImageIcon(resizedImg);

        // Set the resized image to the JLabel
        JLabel backgroundLabel = new JLabel(backgroundImg);
        backgroundLabel.setLayout(new BorderLayout());

        // Add the panel to the background
        JPanel panel = new JPanel();
        panel.setLayout(null);
        backgroundLabel.add(panel, BorderLayout.CENTER);
        panel.setOpaque(false);
        frame.getContentPane().add(backgroundLabel, BorderLayout.CENTER);

        // Create buttons using the helper method
        howToPlayButton = createButton("How to Play", 100, 350, 150, 50);
        panel.add(howToPlayButton);

        startButton = createButton("Start New Game", 300, 350, 170, 50);
        panel.add(startButton);

        loadButton = createButton("Continue Game", 520, 350, 160, 50);
        panel.add(loadButton);

        frame.getContentPane().add(backgroundLabel,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    //Lineysha
    // Helper method for creating buttons
    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Courier", Font.BOLD, 17));
        button.setBackground(new Color(65, 105, 225)); 
        button.setForeground(Color.WHITE);
        button.setOpaque(true); 
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7));
        button.setFocusPainted(false);
        button.setBounds(x, y, width, height); // Position and size
        addHoverEffect(button); // Add hover effect
        return button;
    }

    //Batrisya
    //trigger specific action when clicked
    public void addButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
        loadButton.addActionListener(listener);
        howToPlayButton.addActionListener(listener);
    }

    //Batrisya
    //Getter for startButton
    public JButton getStartButton() {
        return startButton;
    }
    //Getter for loadButton
    public JButton getLoadButton() {
        return loadButton;
    }
    public JButton getHowToPlayButton() {
        return howToPlayButton;
    }    
    // Dispose of the menu's frame
    public void dispose() {
        frame.dispose();
    }

    //Najmi
    //hover effect on button
    private void addHoverEffect(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(85, 130, 255)); // Lighter blue
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10)); // Thicker border
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225)); // Original blue
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7)); // Original border
            }
        });
    }
}

