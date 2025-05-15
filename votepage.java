import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class CustomPanel extends JPanel {
    private Image backgroundImage;

    public CustomPanel(Image backgroundImage) {
        
    }
}

public class  votepage extends JFrame {
    private JButton aboutUsButton, howItWorksButton, signOutButton, hostElectionButton, resultsButton, tutoButton;
    private JLabel greetingLabel;
    private Connection connection;
    private String voterid; 
    //_____________________________________________________________________

public class RoundedTextField extends JTextField {
    private int arcSize = 20; // Adjust the roundness of corners

    public RoundedTextField() {
        setOpaque(false); // Make background transparent
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside text field
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Border
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcSize, arcSize);
        
        g2.dispose();
    }
}

//______________________________________________________________________________

//___________________________________________________________________

public class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        
    }
}
private Image backgroundImage;
private void drawBackground(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    if (backgroundImage != null) {
        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    } else {
        g2.setColor(new Color(0xe0e0e0));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
    g2.setColor(new Color(0xa4dfd8));
    g2.fillRect(0, 0, getWidth(), 75);
    
}
//______________________________________________________________________________________________________
    public votepage(Connection connection1, String ID) {
        connection=connection1;
        voterid=ID;
        //-----------------------------

                String userName = "User"; // Default name

        try {
            String query = "SELECT name FROM voters WHERE voter_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, voterid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or show a dialog box if needed
        }

        //--------------------------------------

        // Set up the frame
        setTitle("Custom Voting System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load background image if available
        backgroundImage = new ImageIcon("C:\\Program Files\\java project\\WhatsApp Image 2025-01-31 at 14.25.24_0612e2d3.jpg").getImage(); // Replace with actual path

        // Set up the custom panel with background and custom drawing
        JPanel customPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                drawBackground(g);
            }

        };
        // Voting System label
        JLabel votingSystemLabel = new JLabel("VOTING SYSTEM");
        votingSystemLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font style and size
        votingSystemLabel.setBounds(20, 20, 200, 30); // Positioning for the label
        customPanel.add(votingSystemLabel);

        // About Us button
        aboutUsButton = new RoundedButton("About Us");
        aboutUsButton.setBounds(270, 30, 100, 35);
        aboutUsButton.setFont(new Font("arial", Font.PLAIN, 12));
        aboutUsButton.setBackground(new Color(0xa4dfd8));
        aboutUsButton.setForeground(new Color(0x3f585c));
        aboutUsButton.setBorder(BorderFactory.createEmptyBorder());
        aboutUsButton.setContentAreaFilled(false);

        aboutUsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "About Us\n" + //
                        "\n" + //
                        "Welcome to our Voting Sytem\n,A secure, transparent, and user-friendly online voting platform \n designed to make elections seamless and trustworthy. \nOur mission is to ensure fairness, accuracy, and accessibility through advanced \n security measures, real-time results, and a simple voting process. With robust encryption,\n strict authentication, and a commitment to transparency, we empower democracy by making \n every vote count. Whether for student elections, corporate decisions, or large-scale \npolls, our system guarantees reliability, efficiency, and integrity in every election. Vote with \n confidence—because your voice matters!"));
        customPanel.add(aboutUsButton);

        // How It Works button
        howItWorksButton = new RoundedButton("How It Works");
        howItWorksButton.setBounds(370, 30, 100, 35);
        howItWorksButton.setFont(new Font("arial", Font.PLAIN, 12));
        howItWorksButton.setBackground(new Color(0xa4dfd8));
        howItWorksButton.setForeground(new Color(0x3f585c));
        howItWorksButton.setBorder(BorderFactory.createEmptyBorder());
        howItWorksButton.setContentAreaFilled(false);
        howItWorksButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "How the voting process works."));
        customPanel.add(howItWorksButton);

        // Sign Out button (replacing Sign In)
        signOutButton = new RoundedButton("Sign Out");
        signOutButton.setBounds(950, 20, 100, 30);
        signOutButton.setFont(new Font("Verdana", Font.PLAIN, 13));
        signOutButton.setBackground(new Color(0xffffff)); // White background
        signOutButton.setForeground(new Color(0x3f585c));
        
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    /*swingTrial2 votingPage = new swingTrial2(connection1);
                    votingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                       //@@@@@@@@@@@@@
                    votingPage.setVisible(true);*/
                });
            }
        }); // <-- Closing bracket was missing here
        
        customPanel.add(signOutButton);
        

        // Greeting Label ("Hey, Admin")
        greetingLabel = new JLabel("Hey,"+userName);
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 40)); // Adjusted font size
        greetingLabel.setBounds(100, 150, 400, 50); // Positioning
        customPanel.add(greetingLabel);

         // "election participation" button
         hostElectionButton = new RoundedButton("<html><div style='text-align: center;'>"
         + "Participate<br>"
         + "in election"
         + "</div></html>");
         hostElectionButton.setBounds(250, 250, 170, 120); // Position for the first button box
         hostElectionButton.setFont(new Font("Verdana", Font.PLAIN, 18));
         hostElectionButton.setBackground(new Color(0x3d7777)); //bright light green
         hostElectionButton.setForeground(new Color(0xffffff));
         hostElectionButton.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e){
                 //SwingUtilities.getWindowAncestor(adminPage2.this).dispose();
                 
                     SwingUtilities.invokeLater(() -> {
                         VotingPageNew votingPage = new VotingPageNew(connection1 ,voterid);
                         votingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                         votingPage.setVisible(true);
                     });  
                     
                 
             }
         });
         customPanel.add(hostElectionButton);
 
         // "Candidate info" button
         resultsButton = new RoundedButton("Candidate info");
         resultsButton.setBounds(450, 250, 170, 120); // Position for the second button box
         resultsButton.setFont(new Font("Verdana", Font.PLAIN, 18));
         resultsButton.setBackground(new Color(0x62bfbc)); //dark light green
         resultsButton.setForeground(new Color(0xffffff));
         resultsButton.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e){
                 //SwingUtilities.getWindowAncestor(adminPage2.this).dispose();
                 
                     SwingUtilities.invokeLater(() -> {
                         candinfo resPage = new candinfo(connection1,voterid);
                         resPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                         resPage.setVisible(true);
                     });  
                     
                 
             }
         });
         customPanel.add(resultsButton);


          // "tutorial" button
          tutoButton = new RoundedButton("Tutorial");
          tutoButton.setBounds(650, 250, 170, 120); // Position for the second button box
          tutoButton.setFont(new Font("Verdana", Font.PLAIN, 18));
          tutoButton.setBackground(new Color(0x2a5555)); //dark light green
          tutoButton.setForeground(new Color(0xffffff));
          tutoButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "TUTORIAL\n" + //
                        "\n" + //
                        "How to Vote\n" +
    "\n" +
    "Step 1: Cast Your Vote\n\n" +
    "On the Voter Home Page, you will see:\n" +
    "• A list of candidates\n" +
    "• A 'Vote' button next to each one\n\n" +
    "Click the 'Vote' button for your preferred candidate.\n\n" +
    "The system will:\n" +
    "✅ Record your vote in the database\n" +
    "❌ Prevent you from voting again (by checking if you’ve already voted)\n\n" +
    "Step 2: View Confirmation\n\n" +
    "After voting, a message like 'Vote successfully recorded!' appears.\n" +
    "Optionally, you may be shown:\n" +
    "Then you may be logged out automatically or returned to the home screen with voting disabled.\n\n" +
    "Step 3: Logout\n\n" +
    "After voting, click 'Logout' to securely exit your session."));
          customPanel.add(tutoButton);
        // Add custom panel to frame
        add(customPanel);
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            adminPage2 votingPage = new adminPage2();
            votingPage.setVisible(true);
        });
    }*/
}
