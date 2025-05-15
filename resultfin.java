import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

class CustomPanel extends JPanel {
    private Image backgroundImage;

    public CustomPanel(Image backgroundImage) {
        
    }
}

public class resultfin extends JFrame implements ActionListener {
    private JButton  aboutUsButton, howItWorksButton, signInButton,doneButton;
    private JPanel candidateListPanel, allcandidateListPanel; // Panel to hold candidate labels
    private ArrayList<String> candidates; // List to store candidate names
    private Connection connection;
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
    g2.setColor(new Color(0xa4dfd8));
    g2.fillRoundRect(80, 300, 600, 300, 30, 30);
    g2.setColor(new Color(0x3f585c));
    g2.fillRoundRect(80, 150, 600, 100, 30, 30);
    
}
//______________________________________________________________________________________________________
    public resultfin(Connection _connection) {
        // Set up the frame
        connection=_connection;
        setTitle("Custom Voting System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize candidate list
        candidates = new ArrayList<>();

        // Load background image if available
        backgroundImage = new ImageIcon("C:\\Program Files\\java project\\WhatsApp Image 2025-01-31 at 14.25.24_0612e2d3.jpg").getImage(); // Replace with actual path

        // Set up the custom panel with background and custom drawing
        JPanel customPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                drawBackground(g);
            }

        };
         // Panel to hold candidate labels
        candidateListPanel = new JPanel();
        candidateListPanel.setLayout(null);
        candidateListPanel.setBounds(80, 150, 600, 100); // Position and size
        candidateListPanel.setOpaque(false); // Make it transparent to show background

        // Add initial all candidate labels to the panel
        refreshCandidateList();
        customPanel.add(candidateListPanel);

        allcandidateListPanel = new JPanel();
        allcandidateListPanel.setLayout(null);
        allcandidateListPanel.setBounds(80, 300, 600, 300); // Position and size
        allcandidateListPanel.setOpaque(false); // Make it transparent to show background

        // Add initial all candidate labels to the panel
        refreshallCandidateList();
        customPanel.add(allcandidateListPanel);
        
        JLabel candN =new JLabel("RESULT");
        candN.setBounds(10, 5, 200, 30);
        candN.setFont(new Font("Arial", Font.BOLD, 18));
        candN.setForeground(Color.white);
        candidateListPanel.add(candN);

        JLabel AcandN =new JLabel("Current Vote Count");
        AcandN.setBounds(20, 5, 200, 30);
        AcandN.setFont(new Font("Arial", Font.BOLD, 20));
        AcandN.setForeground(new Color(0x3f585c));
        allcandidateListPanel.add(AcandN);

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

        // Sign In button
        signInButton = new RoundedButton("Sign Out");
        signInButton.setBounds(950, 20, 100, 30);
        signInButton.setFont(new Font("Verdana", Font.PLAIN, 13));
        signInButton.setBackground(new Color(0xffffff)); // White background
        signInButton.setForeground(new Color(0x3f585c));
        
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    /*swingTrial2 votingPage = new swingTrial2(connection1);
                    votingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                       //@@@@@@@@@@@@@
                    votingPage.setVisible(true);*/
                });
            }
        }); // <-- Closing bracket was missing here
        
        customPanel.add(signInButton);

        doneButton = new RoundedButton("Done");
        doneButton.setBounds(950, 600, 100, 30); // Positioned at the bottom right
        doneButton.setFont(new Font("Verdana", Font.PLAIN, 13));
        doneButton.setBackground(new Color(0xffffff)); // White background
        doneButton.setForeground(new Color(0x3f585c));

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                        
                    adminPage2 votingPage = new adminPage2(connection);
                    votingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    votingPage.setVisible(true);
                });
            }
        });
        customPanel.add(doneButton);
        
       

        

        // Add custom panel to frame
        add(customPanel);
    }
 // Method to refresh the candidate list display
 private void refreshCandidateList() {
    candidateListPanel.removeAll();
    fetchCandidates();
    for (String candidate : candidates) {
        JLabel candidateLabel = new JLabel(candidate);
        candidateLabel.setBounds(10, 30, 600, 50);
        candidateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        candidateLabel.setForeground(new Color(0xa4dfd8));
        candidateListPanel.add(candidateLabel);
    }
    
    candidateListPanel.revalidate();
    candidateListPanel.repaint();
}
// Method to refresh the candidate list display
private void refreshallCandidateList() {
    allcandidateListPanel.removeAll(); 
    fetchallCandidates(); // Fetch latest candidates
    
    allcandidateListPanel.setLayout(null); // Ensure manual positioning works
    allcandidateListPanel.setOpaque(false); // Make transparent if necessary

    int Ypos = 50;
    int gap = 60;
    
    for (String candidate : candidates) {
        JLabel acandidateLabel = new JLabel(candidate);
        acandidateLabel.setBounds(20, Ypos, 600, 40);
        acandidateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        acandidateLabel.setForeground(new Color(0x3d7777));
        allcandidateListPanel.add(acandidateLabel);

        Ypos += gap; // Increase position for next label
    }

    allcandidateListPanel.revalidate();
    allcandidateListPanel.repaint();
}
@Override
public void actionPerformed(ActionEvent e) {
    // Action listener not needed anymore as we have replaced the submit button
}
 public void fetchCandidates() {
    String query = "SELECT c.cand_name, COUNT(DISTINCT v.voter_id) AS vote_count " +
    "FROM votes v " +
    "JOIN candidates c ON v.cand_ID = c.cand_ID " +
    "GROUP BY c.cand_ID, c.cand_name " +
    "ORDER BY vote_count DESC " +
    "LIMIT 1";
try (PreparedStatement statement = connection.prepareStatement(query)) {
             ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String candidateInfo ="<html>" +" The candidate '" + resultSet.getString("cand_name") + "' has won in the election <br> with total votes of " + resultSet.getInt("vote_count") + " ";
                candidates.add(candidateInfo);
                
            
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
    }

    public void fetchallCandidates() {
        candidates.clear(); // Clear the list before fetching new data
        String query = "SELECT c.cand_ID, c.cand_name, COUNT(DISTINCT v.voter_id) AS vote_count " +
               "FROM candidates c " +
               "LEFT JOIN votes v ON v.cand_ID = c.cand_ID " +
               "GROUP BY c.cand_ID, c.cand_name " +
               "ORDER BY vote_count DESC";
try (PreparedStatement statement = connection.prepareStatement(query)) {
                 ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) {
                    String candidateallInfo = String.format("   %-20s %-10d %-10d",
                    resultSet.getString("cand_name"),
                    resultSet.getInt("vote_count"),
                    resultSet.getInt("cand_ID")
                );
                       candidates.add(candidateallInfo);
                    
                
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            
        }
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            adminPageshow votingPage = new adminPageshow(new Connection());
            votingPage.setVisible(true);
        });
    }*/
}
