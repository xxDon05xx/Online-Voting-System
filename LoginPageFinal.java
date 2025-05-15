import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPageFinal extends JFrame {
    public LoginPageFinal() {
        // Set up the frame
        setTitle("Voting System Login");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create the left panel for the greeting message
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(69, 110, 102)); // Dark teal color
        leftPanel.setLayout(null);
        leftPanel.setPreferredSize(new Dimension(400, 500));

        JLabel loginLabel = new JLabel("LOG IN");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 32));
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setBounds(150, 50, 200, 50);
        leftPanel.add(loginLabel);

        JLabel welcomeMessage = new JLabel("<html>\"We're glad you're here—<br> let's get your voice heard again!\"</html>");
        welcomeMessage.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeMessage.setForeground(Color.WHITE);
        welcomeMessage.setBounds(80, 150, 300, 100);
        leftPanel.add(welcomeMessage);

        JLabel signUpLabel = new JLabel("Don't have an account? ");
        signUpLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        signUpLabel.setForeground(Color.WHITE);
        signUpLabel.setBounds(80, 300, 300, 30);
        leftPanel.add(signUpLabel);

        JLabel signUpLink = new JLabel("Sign up");
        signUpLink.setFont(new Font("Arial", Font.PLAIN, 14));
        signUpLink.setForeground(Color.CYAN);
        signUpLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLink.setBounds(230, 300, 100, 30);
        leftPanel.add(signUpLink);

        // Add left panel to the frame
        add(leftPanel, BorderLayout.WEST);

        // Create the right panel for the buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(new Color(240, 240, 240)); // Light gray background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton adminButton = new JButton("ADMIN");
        adminButton.setPreferredSize(new Dimension(200, 50));
        adminButton.setFont(new Font("Arial", Font.BOLD, 20));
        adminButton.setBackground(new Color(69, 110, 102));
        adminButton.setForeground(Color.WHITE);
        adminButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(adminButton, gbc);

        JButton voterButton = new JButton("VOTER");
        voterButton.setPreferredSize(new Dimension(200, 50));
        voterButton.setFont(new Font("Arial", Font.BOLD, 20));
        voterButton.setBackground(new Color(69, 110, 102));
        voterButton.setForeground(Color.WHITE);
        voterButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        rightPanel.add(voterButton, gbc);

        // Add action listeners for buttons (for demonstration purposes)
        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(LoginPageFinal.this, "Admin login selected.");
            }
        });

        voterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(LoginPageFinal.this, "Voter login selected.");
            }
        });

        // Add right panel to the frame
        add(rightPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPageFinal LoginPageFinal = new LoginPageFinal();
            LoginPageFinal.setVisible(true);
        });
    }
}

