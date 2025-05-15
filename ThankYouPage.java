import javax.swing.*;
import java.awt.*;

public class ThankYouPage extends JFrame {
    public ThankYouPage() {
        // Set up the frame
        setTitle("Thank You");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Background color
        getContentPane().setBackground(new Color(240, 235, 220)); // Light beige color

        // Main panel to hold the message and icon
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(158, 201, 191)); // Teal color
        messagePanel.setBounds(150, 100, 450, 300);
        messagePanel.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("THANK YOU!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(63, 88, 92)); // Dark teal color
        messagePanel.add(titleLabel, BorderLayout.NORTH);

        // Message label
        JLabel messageLabel = new JLabel("Your vote has been successfully registered.", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        messageLabel.setForeground(new Color(63, 88, 92));
        messagePanel.add(messageLabel, BorderLayout.CENTER);

        // Icon label
        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setIcon(new ImageIcon("path/to/handshake_icon.png")); // Update with the path to your icon image
        messagePanel.add(iconLabel, BorderLayout.SOUTH);

        // Add main panel to the frame
        add(messagePanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThankYouPage thankYouPage = new ThankYouPage();
            thankYouPage.setVisible(true);
        });
    }
}
