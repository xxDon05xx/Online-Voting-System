import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

public class adminPageshow extends JFrame implements ActionListener {
    private JButton addCandidateButton, aboutUsButton, howItWorksButton, signOutButton, manageVoterButton, doneButton;
    private JTextField endDateField;
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
        g2.setColor(new Color(0xa4dfd8));
        g2.fillRoundRect(480, 150, 400, 400, 30, 30);
        g2.setColor(new Color(0x3f585c));
        g2.fillRoundRect(200, 150, 300, 400, 30, 30);
    }

    // -----------------------------------------------------------
    public adminPageshow(Connection _connection) {
        // Set up the frame
        connection = _connection;
        setTitle("Custom Voting System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize candidate list
        candidates = new ArrayList<>();

        // Load background image if available
        backgroundImage = new ImageIcon("WhatsApp Image 2025-01-31 at 14.25.24_0612e2d3.jpg").getImage(); // Background
                                                                                                          // image

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
        candidateListPanel.setBounds(200, 150, 300, 400); // Position and size
        candidateListPanel.setOpaque(false); // Make it transparent to show background

        // Add initial candidate labels to the panel
        refreshCandidateList();

        customPanel.add(candidateListPanel);
        JLabel candN = new JLabel("Cand_Name |Cand_ID");
        candN.setBounds(20, 0, 200, 50);
        candN.setFont(new Font("Arial", Font.BOLD, 18));
        candN.setForeground(Color.white);
        candidateListPanel.add(candN);

        JLabel line = new JLabel("____________________");
        line.setBounds(20, 9, 200, 50);
        line.setFont(new Font("Arial", Font.BOLD, 18));
        line.setForeground(Color.white);
        candidateListPanel.add(line);

        JLabel adcandN = new JLabel("Adding Candidates");
        adcandN.setBounds(590, 200, 200, 50);
        adcandN.setFont(new Font("Arial", Font.BOLD, 21));
        adcandN.setForeground(new Color(0x3f585c));
        customPanel.add(adcandN);

        JLabel adcandNdes = new JLabel("<html><div style='text-align: center;'>"
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
        customPanel.add(adcandNdes);

        // Add Candidate button setup
        addCandidateButton = new RoundedButton("Add Candidate");
        addCandidateButton.setBounds(235, 460, 200, 40);
        addCandidateButton.setBackground(new Color(0xa4dfd8));
        addCandidateButton.setForeground(new Color(0x3f585c));
        ((adminPageshow.RoundedButton) addCandidateButton).setshowBorder(false);
        addCandidateButton.addActionListener(e -> showAddCandidateDialog());
        customPanel.add(addCandidateButton);

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
        ((adminPageshow.RoundedButton) aboutUsButton).setshowBorder(false);
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
        ((adminPageshow.RoundedButton) howItWorksButton).setshowBorder(false);

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
                    /*
                     * swingTrial2 votingPage = new swingTrial2(connection1);
                     * votingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //@@@@@@@@@@@@@
                     * votingPage.setVisible(true);
                     */
                });
            }
        }); // <-- Closing bracket was missing here

        customPanel.add(signOutButton);

        // Manage Voter button, positioned next to the candidate buttons
        /*
         * manageVoterButton = new JButton("Manage Voter");
         * manageVoterButton.setBounds(600, 250, 150, 30); // Adjusted position
         * manageVoterButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
         * "Manage Voter functionality here."));
         * customPanel.add(manageVoterButton);
         */

        // Done button setup
        doneButton = new RoundedButton("Done");
        doneButton.setBounds(950, 600, 100, 30); // Positioned at the bottom right
        doneButton.setFont(new Font("Verdana", Font.PLAIN, 13));
        doneButton.setBackground(new Color(0xffffff)); // White background
        doneButton.setForeground(new Color(0x3f585c));

        ((adminPageshow.RoundedButton) doneButton).setshowBorder(false);

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

    // --------------------------------------------------------
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
            if (showBorder) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.DARK_GRAY);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        }
    }
    // --------------------------------------------------------------------------------

    // --------------------------------------------------------------------------
    // Method to show a dialog for adding a new candidate
    private void showAddCandidateDialog() {
        JTextField candidateNameField = new JTextField(15);
        JTextField candidatePartyField = new JTextField(15);

        // JTextArea for candidate motives & agenda
        JTextArea candidateAgendaArea = new JTextArea(5, 30); // 5 rows, 30 columns
        candidateAgendaArea.setLineWrap(true);
        candidateAgendaArea.setWrapStyleWord(true);

        // JScrollPane for scrolling if needed
        JScrollPane scrollPane = new JScrollPane(candidateAgendaArea);
        scrollPane.setPreferredSize(new Dimension(300, 100));

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Candidate Name:"));
        panel.add(candidateNameField);
        panel.add(new JLabel("Candidate Party:"));
        panel.add(candidatePartyField);
        panel.add(new JLabel("Motives & Agenda:"));
        panel.add(scrollPane); // Add scrollPane instead of JTextArea directly

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Candidate", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String candidateName = candidateNameField.getText();
            String candidateParty = candidatePartyField.getText();
            String candidateDesc = candidateAgendaArea.getText();

            // Add candidate to the database
            ADDcandidate(candidateName, candidateParty, candidateDesc);
            refreshCandidateList();
        }
    }

    // Method to refresh the candidate list display
    private void refreshCandidateList() {
        candidateListPanel.removeAll();
        fetchCandidates(); // Fetch latest candidates

        candidateListPanel.setLayout(null); // Ensure manual positioning works
        candidateListPanel.setOpaque(false); // Make transparent if necessary

        int Ypos = 50;
        int gap = 30;

        for (String candidate : candidates) {
            JLabel candidateLabel = new JLabel(candidate);
            candidateLabel.setBounds(10, Ypos, 200, 50);
            candidateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            candidateLabel.setForeground(Color.white);
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

    // __________________________________________
    public void ADDcandidate(String candidateName, String candidateParty, String candidateDesc) {

        try {
            String query = "insert into candidates (cand_ID,cand_name,cand_desc) values(?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, candidateParty);
            stmt.setString(2, candidateName);
            stmt.setString(3, candidateDesc);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchCandidates() {
        candidates.clear(); // Clear the list before fetching new data
        String query = "SELECT * FROM candidates";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String candidateInfo = "   " + resultSet.getString("cand_name") + "      "
                        + resultSet.getInt("cand_ID");
                candidates.add(candidateInfo);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
     * public static void main(String[] args) {
     * SwingUtilities.invokeLater(() -> {
     * adminPageshow votingPage = new adminPageshow(new Connection());
     * votingPage.setVisible(true);
     * });
     * }
     */
}
