import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class LandingPageWithBackground extends JFrame implements ActionListener {

    // Components for the form
    JTextField eventNameField, eventOrganizerField;
    JFormattedTextField eventDateField;
    JComboBox<String> venueDropdown;
    JButton submitButton, registerButton, viewEventsButton;
    JLabel backgroundImage;
    JPanel formPanel;

    // Components for login
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    JComboBox<String> roleDropdown;

    String loggedInRole; // Holds the logged-in role (admin/user)

    private JButton backButton;

    private void displayBackButton(Runnable backAction) {
        // Create Back button and set its properties
        backButton = new JButton("Back");
        backButton.setBounds(10, 10, 100, 30);
        backButton.addActionListener(e -> backAction.run());
        backgroundImage.add(backButton);
        backgroundImage.repaint();
    }


    public LandingPageWithBackground() {
        // Set up the JFrame
        setTitle("Event Registration");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Set background image
        setLayout(null);
        backgroundImage = new JLabel(new ImageIcon("C:\\Users\\Siddhesh\\OneDrive\\Desktop\\Java DEV\\img.jpg")); // Replace with the path of your image
        backgroundImage.setBounds(0, 0, 800, 600);
        setContentPane(backgroundImage);

        // Call the login screen
        displayLoginScreen();
        setVisible(true);
    }

    // Function to display login screen
    private void displayLoginScreen() {
        backgroundImage.removeAll();

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(200, 150, 400, 300);
        loginPanel.setBackground(new Color(255, 255, 255, 180));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 30, 100, 30);
        loginPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 30, 200, 30);
        loginPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 80, 100, 30);
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 80, 200, 30);
        loginPanel.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 130, 100, 30);
        loginPanel.add(roleLabel);

        String[] roles = {"Admin", "User"};
        roleDropdown = new JComboBox<>(roles);
        roleDropdown.setBounds(150, 130, 200, 30);
        loginPanel.add(roleDropdown);

        loginButton = new JButton("Login");
        loginButton.setBounds(150, 180, 100, 30);
        loginButton.addActionListener(this);
        loginPanel.add(loginButton);

        backgroundImage.add(loginPanel);
        backgroundImage.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Handle login
            handleLogin();
        } else if (e.getSource() == registerButton) {
            // Show the event registration form
            displayRegistrationForm();
        } else if (e.getSource() == submitButton) {
            // Handle form submission
            submitEventDetails();
        } else if (e.getSource() == viewEventsButton) {
            // Handle event viewing
            viewRegisteredEvents();
        } else if (e.getActionCommand().equals("Cancel Event")) {
            // Handle event cancellation
            displayCancelEventScreen();
        }
    }

    // Function to handle login
    private void handleLogin() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        loggedInRole = (String) roleDropdown.getSelectedItem();

        // Simple validation for example purposes
        if (username.equals("admin") && password.equals("admin123") && loggedInRole.equals("Admin")) {
            JOptionPane.showMessageDialog(this, "Logged in as Admin");
            displayAdminLandingPage();
        } else if (username.equals("user") && password.equals("user123") && loggedInRole.equals("User")) {
            JOptionPane.showMessageDialog(this, "Logged in as User");
            displayUserLandingPage();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }

    // Function to display the admin landing page after login
    private void displayAdminLandingPage() {
        backgroundImage.removeAll();

//        registerButton = new JButton("Register Event");
//        registerButton.setBounds(250, 250, 150, 50);
//        registerButton.addActionListener(this);
//        backgroundImage.add(registerButton);

        // Add a button for admin to view registered events
        viewEventsButton = new JButton("View Registered Events");
        viewEventsButton.setBounds(450, 250, 200, 50);
        viewEventsButton.addActionListener(this);
        backgroundImage.add(viewEventsButton);

        // Add a button for admin to cancel events
        JButton cancelEventButton = new JButton("Cancel Event");
        cancelEventButton.setBounds(250, 250, 150, 50);
        cancelEventButton.addActionListener(this);
        cancelEventButton.setActionCommand("Cancel Event"); // Set action command
        backgroundImage.add(cancelEventButton);

        // Add Back button
        displayBackButton(() -> displayLoginScreen());

        backgroundImage.repaint();
    }

    // Function to display the user landing page after login
    private void displayUserLandingPage() {
        backgroundImage.removeAll();

        registerButton = new JButton("Register");
        registerButton.setBounds(350, 250, 100, 50);
        registerButton.addActionListener(this);
        backgroundImage.add(registerButton);

        // Add Back button
        displayBackButton(() -> displayLoginScreen());

        backgroundImage.repaint();
    }

    // Function to display the event registration form
    private void displayRegistrationForm() {
        backgroundImage.removeAll();

        // Create a panel to hold the form components
        formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(150, 100, 500, 400);
        formPanel.setBackground(new Color(255, 255, 255, 180));

        // Event Name field
        JLabel eventNameLabel = new JLabel("Event Name:");
        eventNameLabel.setBounds(50, 30, 100, 30);
        eventNameField = new JTextField();
        eventNameField.setBounds(200, 30, 200, 30);
        formPanel.add(eventNameLabel);
        formPanel.add(eventNameField);

        // Event Date field
        JLabel eventDateLabel = new JLabel("Event Date (YYYY-MM-DD):");
        eventDateLabel.setBounds(50, 80, 150, 30);
        eventDateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        eventDateField.setBounds(200, 80, 200, 30);
        formPanel.add(eventDateLabel);
        formPanel.add(eventDateField);

        // Event Organizer field
        JLabel eventOrganizerLabel = new JLabel("Organizer Name:");
        eventOrganizerLabel.setBounds(50, 130, 150, 30);
        eventOrganizerField = new JTextField();
        eventOrganizerField.setBounds(200, 130, 200, 30);
        formPanel.add(eventOrganizerLabel);
        formPanel.add(eventOrganizerField);

        // Venue Dropdown
        JLabel venueLabel = new JLabel("Select Venue:");
        venueLabel.setBounds(50, 180, 150, 30);
        String[] venues = {"GK ground", "Venus hotel", "Anand Hall", "Queen's Lawn"};
        venueDropdown = new JComboBox<>(venues);
        venueDropdown.setBounds(200, 180, 200, 30);
        formPanel.add(venueLabel);
        formPanel.add(venueDropdown);

        // Submit button
        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 250, 100, 30);
        submitButton.addActionListener(this);
        formPanel.add(submitButton);

        // Add form panel to background
        backgroundImage.add(formPanel);

        // Add Back button
        displayBackButton(() -> displayLoginScreen());

        backgroundImage.repaint();
    }

    private void submitEventDetails() {
        String eventName = eventNameField.getText();
        String eventVenue = (String) venueDropdown.getSelectedItem();  // Dropdown for venue
        String eventDate = eventDateField.getText();
        String eventOrganizer = eventOrganizerField.getText();

        // Ensure fields are not empty
        if (eventName.isEmpty() || eventVenue.isEmpty() || eventDate.isEmpty() || eventOrganizer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            // Establish connection to MySQL database
            String url = "jdbc:mysql://localhost:3306/event_registrationnn";
            String username = "root";  // Replace with your MySQL username
            String password = "Siddhesh@2922";  // Replace with your MySQL password

            Connection conn = DriverManager.getConnection(url, username, password);

            // First, check if the event date already exists in the database
            String checkDateQuery = "SELECT * FROM events WHERE event_date = ?";
            PreparedStatement checkDateStmt = conn.prepareStatement(checkDateQuery);
            checkDateStmt.setDate(1, java.sql.Date.valueOf(eventDate)); // Use java.sql.Date for SQL compatibility
            ResultSet rs = checkDateStmt.executeQuery();

            if (rs.next()) {
                // If the result set is not empty, it means an event on this date already exists
                JOptionPane.showMessageDialog(this, "No slots are remaining for this date!");
                return; // Stop further execution
            }

            // If no event exists on the same date, proceed to insert the new event
            String query = "INSERT INTO events (event_name, event_date, event_organizer_name, event_venue) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, eventName);
            pstmt.setDate(2, java.sql.Date.valueOf(eventDate)); // Use java.sql.Date for SQL compatibility
            pstmt.setString(3, eventOrganizer);
            pstmt.setString(4, eventVenue);
            pstmt.executeUpdate(); // Execute the SQL statement

            // Close the connection
            conn.close();
            JOptionPane.showMessageDialog(this, "Event registered successfully!");
            formPanel.setVisible(false); // Hide registration form after submission

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred!");
        }
    }

    // Function to view registered events
    private void viewRegisteredEvents() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            // Establish connection to MySQL database
            String url = "jdbc:mysql://localhost:3306/event_registrationnn";
            String username = "root";  // Replace with your MySQL username
            String password = "Siddhesh@2922";  // Replace with your MySQL password

            Connection conn = DriverManager.getConnection(url, username, password);

            // SQL query to fetch all registered events
            String query = "SELECT * FROM events";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Create a JFrame to display events
            JFrame eventFrame = new JFrame("Registered Events");
            eventFrame.setSize(600, 400);
            eventFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Create a JTable to display events
            String[] columns = {"Event ID", "Event Name", "Event Date", "Organizer", "Venue"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
            JTable eventsTable = new JTable(tableModel);

            // Add event data to table
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getDate("event_date"),
                        rs.getString("event_organizer_name"),
                        rs.getString("event_venue")
                };
                tableModel.addRow(row);
            }

            // Add JTable to JScrollPane
            JScrollPane scrollPane = new JScrollPane(eventsTable);
            eventFrame.add(scrollPane, BorderLayout.CENTER);

            eventFrame.setVisible(true);

            // Close the connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred while retrieving events!");
        }
    }

    private void displayCancelEventScreen() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            // Establish connection to MySQL database
            String url = "jdbc:mysql://localhost:3306/event_registrationnn";
            String username = "root";  // Replace with your MySQL username
            String password = "Siddhesh@2922";  // Replace with your MySQL password

            Connection conn = DriverManager.getConnection(url, username, password);

            // SQL query to fetch all registered events
            String query = "SELECT * FROM events";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Create a JFrame to display events
            JFrame cancelEventFrame = new JFrame("Cancel Event");
            cancelEventFrame.setSize(600, 400);
            cancelEventFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Create a JTable to display events
            String[] columns = {"Event ID", "Event Name", "Event Date", "Organizer", "Venue"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
            JTable eventsTable = new JTable(tableModel);

            // Add event data to table
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getDate("event_date"),
                        rs.getString("event_organizer_name"),
                        rs.getString("event_venue")
                };
                tableModel.addRow(row);
            }

            // Add JTable to JScrollPane
            JScrollPane scrollPane = new JScrollPane(eventsTable);
            cancelEventFrame.add(scrollPane, BorderLayout.CENTER);

            // Add a button to cancel the selected event
            JButton cancelSelectedEventButton = new JButton("Cancel Selected Event");
            cancelSelectedEventButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = eventsTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int eventId = (int) eventsTable.getValueAt(selectedRow, 0); // Get the event_id of the selected row
                        cancelEvent(eventId); // Call the function to cancel the event
                        ((DefaultTableModel) eventsTable.getModel()).removeRow(selectedRow); // Remove row from table
                        JOptionPane.showMessageDialog(cancelEventFrame, "Event cancelled successfully!");
                    } else {
                        JOptionPane.showMessageDialog(cancelEventFrame, "Please select an event to cancel.");
                    }
                }
            });

            cancelEventFrame.add(cancelSelectedEventButton, BorderLayout.SOUTH);
            cancelEventFrame.setVisible(true);

            // Close the connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred!");
        }
    }

    // Function to cancel an event by its event ID
    private void cancelEvent(int eventId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            // Establish connection to MySQL database
            String url = "jdbc:mysql://localhost:3306/event_registrationnn";
            String username = "root";  // Replace with your MySQL username
            String password = "Siddhesh@2922";  // Replace with your MySQL password

            Connection conn = DriverManager.getConnection(url, username, password);

            // SQL query to delete the selected event
            String query = "DELETE FROM events WHERE event_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, eventId);
            pstmt.executeUpdate(); // Execute the SQL statement

            // Close the connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred while cancelling the event!");
        }
    }

    public static void main(String[] args) {
        new LandingPageWithBackground();
    }
}
