import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

class CustomPanel extends JPanel {
    private Image backgroundImage;

    public CustomPanel(Image backgroundImage) {
       
    }
}

public class VotingPageNew extends JFrame implements ActionListener {
    private JRadioButton candidate1;
    private JRadioButton[] buttons;
    private JButton aboutUsButton, howItWorksButton, signOutButton, hostElectionButton, resultsButton;

    private JButton submitButton;
    private ButtonGroup candidateGroup;
    private Connection connection;
    private String voterid; 

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
    g2.fillRoundRect(480, 150, 400, 400, 30, 30);
    g2.setColor(new Color(0x3f585c));
    g2.fillRoundRect(200, 150, 300, 400, 30, 30);
    
}
//______________________________________________________________________________________________________
  

//______________________________________________________________________________________________________


public class RoundedRadioButton extends JRadioButton {

    public RoundedRadioButton(String text) {
        super(text);
        setOpaque(false); // Make background transparent
        setFocusPainted(false);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Custom background
        if (isSelected()) {
            g2.setColor(new Color(0x3f585c)); // Selected color
        } else {
            g2.setColor(new Color(0x62bfbc)); // Default color
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // Draw text
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int textX = 10;
        int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Optional: Add border styling here if needed
    }
}

//___________________________________________________________________________________________
 public VotingPageNew(Connection _connection, String ID) {
        // Set up the frame
        connection=_connection;
        voterid=ID;
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

        // Create the top label
        JLabel titleLabel = new JLabel("CAST YOUR VOTE!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(0x3f585c));
        titleLabel.setBounds(530, 170, 300, 50);
        customPanel.add(titleLabel);

        JLabel adcandNdes = new JLabel("<html><div style='text-align: center;'>"
        + "This page allows the admin<br>"
        + "to enter a candidate’s party and<br>"
        + "save them to the database.<br>"
        + "name and The candidate list<br>"
        + "updates automatically after<br>"
        + "adding a new entry."
        + "</div></html>");

        adcandNdes.setBounds(150, 275, 400, 150); // Centered width & correct height
        adcandNdes.setFont(new Font("Arial", Font.BOLD, 18));
        adcandNdes.setForeground(new Color(0xffffff));
        adcandNdes.setHorizontalAlignment(SwingConstants.CENTER); // Center horizontally
        adcandNdes.setVerticalAlignment(SwingConstants.CENTER); // Center vertically
        customPanel.add(adcandNdes);

        candidateGroup = new ButtonGroup();
        Font candidateFont = new Font("Arial", Font.PLAIN, 20);
        Dimension buttonSize = new Dimension(300, 60);
        ArrayList<String> candidates=fetchCandidates();
        buttons = new JRadioButton[candidates.size()];
        int yPosition = 230; // Starting y-coordinate
        for (int i = 0; i < buttons.length; i++) {
            System.out.println(candidates.get(i));
            buttons[i] = new RoundedRadioButton(candidates.get(i));
            buttons[i].setBounds(530, yPosition, buttonSize.width, buttonSize.height);
            buttons[i].setFont(candidateFont);
            buttons[i].setForeground(new Color(0xffffff));
            buttons[i].setBackground(new Color(0x3f585c));

            candidateGroup.add(buttons[i]);
            customPanel.add(buttons[i]);
            yPosition += buttonSize.height + 10; // Increment y-position for the next button
        }
       
        
        // Submit button setup
        submitButton = new RoundedButton("Submit");
        submitButton.setBounds(590, 490, 200, 40);
        submitButton.addActionListener(this);
        submitButton.setFont(new Font("Verdana", Font.PLAIN, 13));
        submitButton.setBackground(new Color(0x3f585c)); // White background
        submitButton.setForeground(new Color(0xffffff));
        
        customPanel.add(submitButton);

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
        

        
        // Add custom panel to frame
        add(customPanel);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) { 
            String selectedCandidate = "";
            for(int i=0;i<buttons.length;i++){
                if (buttons[i].isSelected()) {
                    System.out.println(buttons[i].getName()+"/"+buttons[i].getText());
                    selectedCandidate = buttons[i].getText();
                }  
            }
           
            /*if (candidate1.isSelected()) {
                selectedCandidate = "Candidate 1";
            } else if (candidate2.isSelected()) {
                selectedCandidate = "Candidate 2";
            } else if (candidate3.isSelected()) {
                selectedCandidate = "Candidate 3";
            } else if (candidate4.isSelected()) {
                selectedCandidate = "Candidate 4";
            } else if (candidate5.isSelected()) {
                selectedCandidate = "Candidate 5";
            }*/

            if (!selectedCandidate.isEmpty()) {
                
                try {
                    ADDvote(selectedCandidate, voterid);
                JOptionPane.showMessageDialog(this, "You voted for " + selectedCandidate);
                SwingUtilities.invokeLater(() -> {
                    ThankYouPage thankYouPage = new ThankYouPage();
                    thankYouPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    thankYouPage.setVisible(true);
                    this.dispose(); // Close current VotingPageNew frame
                });
                } catch (Exception f) {JOptionPane.showMessageDialog(this, "You have already cast your vote");
                SwingUtilities.invokeLater(() -> {
                    ThankYouPage thankYouPage = new ThankYouPage();
                    thankYouPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    thankYouPage.setVisible(true);
                    this.dispose(); // Close current VotingPageNew frame
                });
                }

            } else {
                JOptionPane.showMessageDialog(this, "Please select a candidate.");
            }
            
        }
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VotingPageNew VotingPage = new VotingPageNew();
            VotingPage.setVisible(true);
        });
    }*/
    public ArrayList<String> fetchCandidates() {
        String query = "SELECT * FROM candidates";
        ArrayList<String> candidates=new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
             ResultSet resultSet = statement.executeQuery();
            
             while (resultSet.next()) {
                
                String candidateInfo = "   " + resultSet.getString("cand_name") + " (" + resultSet.getInt("cand_ID") + ")";
                candidates.add(candidateInfo);
                
             }
            
            }
         catch (SQLException e) {
            e.printStackTrace();
            
        }
        return candidates;
        
    }
    public void ADDvote(String candidateName, String voterid) throws SQLException {
        PreparedStatement stmt = null;
        try {
            int startIndex = candidateName.indexOf("(");
            int endIndex = candidateName.indexOf(")");
    
            if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
                throw new IllegalArgumentException("Invalid candidate name format: " + candidateName);
            }
    
            String canid = candidateName.substring(startIndex + 1, endIndex);
    
            String query = "INSERT INTO votes (ID, voter_id, cand_ID) VALUES (?, ?, ?)";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, generateVoteId());
            stmt.setString(2, voterid);
            stmt.setString(3, canid);
    
            stmt.executeUpdate();
            System.out.println("Vote cast successfully!");
    
        } catch (SQLException e) {
            if (e.getSQLState().equals("45000")) { // Catch trigger error
                System.out.println("Error: Duplicate vote detected! This voter has already voted.");
            } else {
                e.printStackTrace();
            }
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    
    public Integer generateVoteId() {
        Random random = new Random();
        return (1000 + random.nextInt(9000));
    }
}
