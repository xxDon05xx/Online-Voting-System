
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

class Voter {
    private String rollNo;
    private String name;
    private String password;
    private String voterId;
    private String is_admin;

    public Voter(String rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public void setis_admin(String is_admin) {
        this.is_admin = is_admin;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getVoterId() {
        return voterId;
    }

    public String getis_admin() {
        return is_admin;
    }
}

class VotingSystem {
    private List<Voter> predefinedVoters = new ArrayList<>();
    public Connection connection;

    public VotingSystem() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/votingsys", "root", "mysql"); // Adjust
                                                                                                                // credentials
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }

    public Voter authenticateRollNoAndName(String rollNo, String name) {
        String query = "SELECT * FROM voters WHERE roll_no = ? AND name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, rollNo);
            statement.setString(2, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Voter voter = new Voter(rollNo, name);
                voter.setVoterId(resultSet.getString("voter_id"));
                voter.setis_admin(resultSet.getString("admin"));
                return voter;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Voter authenticateVoterIdAndPassword(String voterId, String password) {
        String query = "SELECT * FROM voters WHERE voter_id = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, voterId);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Voter vot_var = new Voter(resultSet.getString("roll_no"), resultSet.getString("name"));
                vot_var.setis_admin(resultSet.getString("admin"));
                return vot_var;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateVoterId() {
        Random random = new Random();
        return "VOTER" + (10000 + random.nextInt(90000));
    }

    public void storePasswordAndVoterId(Voter voter, String password) {
        String voterId = generateVoterId();
        voter.setPassword(password);
        voter.setVoterId(voterId);

        try {
            String query = "UPDATE voters SET password = ?, voter_id = ? WHERE roll_no = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, password);
            stmt.setString(2, voterId);
            stmt.setString(3, voter.getRollNo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class swingTrial2 extends JPanel {
    private BufferedImage backgroundImage;
    private VotingSystem authSystem = new VotingSystem();
    private JPanel signupPanel, loginPanel;
    private CardLayout cardLayout = new CardLayout();

    public swingTrial2() {
        setLayout(cardLayout);

        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("WhatsApp Image 2025-01-31 at 14.25.24_0612e2d3.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }

        signupPanel = createSignupPanel();
        loginPanel = createLoginPanel();

        add(signupPanel, "signup");
        add(loginPanel, "login");

        cardLayout.show(this, "signup");
    }

    private JPanel createSignupPanel() {
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                drawBackground(g);
            }
        };
        JLabel sign = new JLabel("Signin");
        sign.setFont(new Font("Dialog", Font.BOLD, 18));
        sign.setBounds(660, 180, 150, 25);
        panel.add(sign);

        JLabel rollNoLabel = new JLabel("Roll Number:");
        rollNoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        rollNoLabel.setBounds(510, 240, 150, 25);
        panel.add(rollNoLabel);

        RoundedTextField rollNoField = new RoundedTextField();
        rollNoField.setBounds(650, 240, 200, 25);
        panel.add(rollNoField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setBounds(510, 290, 150, 25);
        panel.add(nameLabel);

        RoundedTextField nameField = new RoundedTextField(); // *************** */
        nameField.setBounds(650, 290, 200, 25);
        panel.add(nameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(510, 340, 150, 25);
        panel.add(passwordLabel);

        RoundedPASSField passwordField = new RoundedPASSField();
        passwordField.setBounds(650, 340, 200, 25);
        panel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordLabel.setBounds(510, 390, 150, 25);
        panel.add(confirmPasswordLabel);

        RoundedPASSField confirmPasswordField = new RoundedPASSField();
        confirmPasswordField.setBounds(650, 390, 200, 25);
        panel.add(confirmPasswordField);

        RoundedButton submitButton = new RoundedButton("Submit");
        submitButton.setBounds(650, 440, 100, 30);
        submitButton.setBackground(new Color(0x3f585c));
        submitButton.setForeground(new Color(0xffffff));
        panel.add(submitButton);

        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(510, 470, 400, 25);
        panel.add(resultLabel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rollNo = rollNoField.getText();
                String name = nameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!password.equals(confirmPassword)) {
                    resultLabel.setText("Error: Passwords do not match.");
                    return;
                }

                Voter voter = authSystem.authenticateRollNoAndName(rollNo, name);
                if (voter != null) {
                    authSystem.storePasswordAndVoterId(voter, password);
                    resultLabel.setText("Registration successfull! Your Voter ID: " + voter.getVoterId());
                } else {
                    resultLabel.setText("Invalid roll number or name. Registration failed.");
                }
            }
        });

        addHeaderComponents(panel);
        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                drawBackground(g);
            }
        };
        JLabel log = new JLabel("Login");
        log.setFont(new Font("Dialog", Font.BOLD, 18));
        log.setBounds(660, 180, 150, 25);
        panel.add(log);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setBounds(510, 240, 150, 25);
        panel.add(nameLabel);

        RoundedTextField nameField = new RoundedTextField();
        nameField.setBounds(650, 240, 200, 25);
        panel.add(nameField);

        JLabel voterIdLabel = new JLabel("Voter ID:");
        voterIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        voterIdLabel.setBounds(510, 290, 150, 25);
        panel.add(voterIdLabel);

        RoundedTextField voterIdField = new RoundedTextField();
        voterIdField.setBounds(650, 290, 200, 25);
        panel.add(voterIdField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(510, 340, 150, 25);
        panel.add(passwordLabel);

        RoundedPASSField passwordField = new RoundedPASSField();
        passwordField.setBounds(650, 340, 200, 25);
        panel.add(passwordField);

        RoundedButton loginButton = new RoundedButton("Log In");
        loginButton.setBounds(650, 390, 100, 30);
        loginButton.setBackground(new Color(0x3f585c));
        loginButton.setForeground(new Color(0xffffff));
        panel.add(loginButton);

        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(500, 400, 400, 25);
        panel.add(resultLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String voterId = voterIdField.getText();
                String password = new String(passwordField.getPassword());

                Voter voter = authSystem.authenticateVoterIdAndPassword(voterId, password);
                if (voter != null) {
                    resultLabel.setText("Login successful! Redirecting..." + voter.getis_admin() + "cvbj");
                    SwingUtilities.getWindowAncestor(swingTrial2.this).dispose();
                    if ("True".equals(voter.getis_admin())) {
                        // ___________________________________

                        SwingUtilities.invokeLater(() -> {

                            adminPage2 votingPage = new adminPage2(authSystem.connection);
                            votingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            votingPage.setVisible(true);
                        });

                    } else {
                        SwingUtilities.invokeLater(() -> {
                            votepage VotingPage = new votepage(authSystem.connection, voterId);
                            VotingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            VotingPage.setVisible(true);
                        });
                    }
                    // ___________________________________
                }
                // Redirect to home page
                else {
                    resultLabel.setText("Invalid Voter ID or password.");
                }
            }
        });

        addHeaderComponents(panel);
        return panel;
    }
    // _____________________________________________________________________

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

    // ______________________________________________________________________________
    public class RoundedPASSField extends JPasswordField {
        private int arcSize = 20; // Adjust the roundness of corners

        public RoundedPASSField() {
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

    // ______________________________________________________________________________
    // ___________________________________________________________________

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

    // _______________________________________________________________________________________________

    private void addHeaderComponents(JPanel panel) {
        JLabel header = new JLabel("VOTING SYSTEM", SwingConstants.LEFT);
        header.setFont(new Font("Verdana", Font.BOLD, 24));
        header.setForeground(new Color(0x3f585c));
        header.setBounds(20, 10, 250, 50);
        panel.add(header);

        RoundedButton aboutUsLabel = new RoundedButton("About Us");
        aboutUsLabel.setBounds(270, 30, 100, 35);
        aboutUsLabel.setFont(new Font("arial", Font.PLAIN, 12));
        aboutUsLabel.setBackground(new Color(0xa4dfd8));
        aboutUsLabel.setForeground(new Color(0x3f585c));
        aboutUsLabel.setshowBorder(false);
        aboutUsLabel.setContentAreaFilled(false);

        aboutUsLabel.addActionListener(e -> JOptionPane.showMessageDialog(this, "About Us\n" + //
                "\n" + //
                "Welcome to our Voting Sytem\n,A secure, transparent, and user-friendly online voting platform \n designed to make elections seamless and trustworthy. \nOur mission is to ensure fairness, accuracy, and accessibility through advanced \n security measures, real-time results, and a simple voting process. With robust encryption,\n strict authentication, and a commitment to transparency, we empower democracy by making \n every vote count. Whether for student elections, corporate decisions, or large-scale \npolls, our system guarantees reliability, efficiency, and integrity in every election. Vote with \n confidence—because your voice matters!"));
        panel.add(aboutUsLabel);

        RoundedButton howItWorksLabel = new RoundedButton("How It Works");
        howItWorksLabel.setBounds(370, 30, 150, 35);
        howItWorksLabel.setFont(new Font("arial", Font.PLAIN, 12));
        howItWorksLabel.setBackground(new Color(0xa4dfd8));
        howItWorksLabel.setForeground(new Color(0x3f585c));
        howItWorksLabel.setshowBorder(false);
        howItWorksLabel.addActionListener(e -> JOptionPane.showMessageDialog(this, "How the voting process works."));
        panel.add(howItWorksLabel);

        RoundedButton signUpButton = new RoundedButton("Sign Up");
        signUpButton.setBounds(750, 20, 120, 35);
        signUpButton.setFont(new Font("Verdana", Font.PLAIN, 13));
        signUpButton.setBackground(new Color(0xa4dfd8));
        signUpButton.setForeground(new Color(0x3f585c));
        signUpButton.setBorder(BorderFactory.createLineBorder(new Color(0x3f585c), 2));
        panel.add(signUpButton);

        signUpButton.addActionListener(e -> cardLayout.show(swingTrial2.this, "signup"));

        RoundedButton logInButton = new RoundedButton("Log In");
        logInButton.setBounds(900, 20, 120, 35);
        logInButton.setFont(new Font("Verdana", Font.PLAIN, 13));
        logInButton.setBackground(new Color(0xffffff));
        logInButton.setForeground(new Color(0x3f585c));
        panel.add(logInButton);

        logInButton.addActionListener(e -> cardLayout.show(swingTrial2.this, "login"));
    }

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
        g2.setColor(new Color(0xffffff));
        g2.fillRoundRect(480, 150, 400, 400, 30, 30);
        g2.setColor(new Color(0x3f585c));
        g2.fillRoundRect(200, 150, 300, 400, 30, 30);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Voting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setContentPane(new swingTrial2());
        frame.setVisible(true);
    }
    // ______________________________________________

}
