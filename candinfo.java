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

public class candinfo extends JFrame implements ActionListener {
    private JButton addCandidateButton, aboutUsButton, howItWorksButton, signOutButton, doneButton;
    private JPanel candidateListPanel; // Panel to hold candidate labels
    private ArrayList<String> candidates; // List to store candidate names
    private Connection connection;
    
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
       /*g2.setColor(new Color(0xa4dfd8));
        g2.fillRoundRect(480, 150, 400, 400, 30, 30);
        g2.setColor(new Color(0x3f585c));
        g2.fillRoundRect(200, 150, 300, 400, 30, 30);*/
    }
    //-----------------------------------------------------------
    public candinfo(Connection _connection ,String id) {
        // Set up the frame
        String vid=id;
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
        JPanel customPanel = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g) {
                drawBackground(g);
            }
        };
       

        // Panel to hold candidate labels
        candidateListPanel = new JPanel();
        candidateListPanel.setLayout(null);
        candidateListPanel.setBounds(20, 100, 900, 600); // Position and size
        candidateListPanel.setOpaque(false); // Make it transparent to show background
        customPanel.add(candidateListPanel);

        // Add initial candidate labels to the panel
        refreshCandidateList();


        JLabel adcandN =new JLabel("Candidate Information");
        adcandN.setBounds(400, 100, 400, 50);
        adcandN.setFont(new Font("Arial", Font.BOLD, 24));
        adcandN.setForeground(new Color(0x3f585c));
        customPanel.add(adcandN);

        /*JLabel adcandNdes = new JLabel("<html><div style='text-align: center;'>"
        + "This page allows the admin<br>"
        + "to enter a candidate’s name and<br>"
        + "party and save them to the database.<br>"
        + "The candidate list updates automatically<br>"
        + "after adding a new entry."
        + "</div></html>");

        adcandNdes.setBounds(490, 300, 400, 120); // Centered width & correct height
        adcandNdes.setFont(new Font("Arial", Font.BOLD, 18));
        adcandNdes.setForeground(new Color(0x3f585c));
        adcandNdes.setHorizontalAlignment(SwingConstants.CENTER); // Center horizontally
        adcandNdes.setVerticalAlignment(SwingConstants.CENTER); // Center vertically
        customPanel.add(adcandNdes);*/


    

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
        ((candinfo.RoundedButton) aboutUsButton).setshowBorder(false);
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
        ((candinfo.RoundedButton) howItWorksButton).setshowBorder(false);

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

       // Done button setup
       doneButton = new RoundedButton("Done");
       doneButton.setBounds(950, 600, 100, 30); // Positioned at the bottom right
       doneButton.setFont(new Font("Verdana", Font.PLAIN, 13));
       doneButton.setBackground(new Color(0xffffff)); // White background
       doneButton.setForeground(new Color(0x3f585c));

       ((candinfo.RoundedButton) doneButton).setshowBorder(false);

       doneButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               SwingUtilities.invokeLater(() -> {
                       
                   votepage votingPage = new votepage(_connection ,vid);
                   votingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                   votingPage.setVisible(true);
               });
           }
       });
       customPanel.add(doneButton);

        // Add custom panel to frame
        add(customPanel);
    }
    //--------------------------------------------------------
    public class RoundedButton extends JButton {
        private boolean showBorder = true; // Default: border is disabled
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setOpaque(false);
            setBorderPainted(false);
        }
        public void setshowBorder(boolean showBorder) {
            this.showBorder = showBorder;
            repaint(); // Redraw the button when the border visibility changes
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
            if(showBorder){
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.DARK_GRAY);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            g2.dispose();
        }
        }
    }
    //--------------------------------------------------------------------------------
    
   


    // Method to refresh the candidate list display
    private void refreshCandidateList() {
        candidateListPanel.removeAll(); 
        fetchCandidates(); // Fetch latest candidates
        
        candidateListPanel.setLayout(null); // Ensure manual positioning works
        candidateListPanel.setOpaque(false); // Make transparent if necessary
    
        int Ypos = 25;
        int gap = 100;
        
        for (String candidate : candidates) {
            JLabel candidateLabel = new JLabel(candidate);
            candidateLabel.setBounds(10, Ypos, 400, 150);
            candidateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            candidateLabel.setForeground(Color.black);
            candidateListPanel.add(candidateLabel);
    
            Ypos += gap; // Increase position for next label
        }
    
        candidateListPanel.revalidate();
        candidateListPanel.repaint();
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        // Action listener not needed anymore as we have replaced the submit button
    }
//__________________________________________

 public void fetchCandidates() {
    candidates.clear(); // Clear the list before fetching new data
        String query = "SELECT * FROM candidates";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
             ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String candidateName = resultSet.getString("cand_name");
                String candidateDesc = resultSet.getString("cand_desc");

                String candidateInfo = "<html><b>" + candidateName + "</b><br>" + candidateDesc + "</html>";
                candidates.add(candidateInfo);

                
            
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
