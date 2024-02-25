import javax.swing.*;
import javax.xml.crypto.Data;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/* Load product, save product
 */ 

public class App {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Connection connection;
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/db_connectivity_assignment";
        String user = "root";
        String password = "0322";
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, password);
        // System.out.println("hello");
        new App().createAndShowGUI(connection);
        DataAccess da = new DataAccess(connection);
        // System.out.println(da.readProjects());
        
    }
    public void createAndShowGUI(Connection connection) throws SQLException {
        this.connection = connection;
        frame = new JFrame("CardLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Main View
        JPanel mainView = createMainView();
        cardPanel.add(mainView, "main");

        // First Secondary View
        JobView jv = new JobView(this, connection);
        cardPanel.add(jv.getPanel(), "job-view");

        // Second Secondary View
        EmployeeView ev = new EmployeeView(this, connection);
        cardPanel.add(ev.getPanel(), "employee-view");

        ProjectView pv = new ProjectView(this, connection);
        cardPanel.add(pv.getPanel(), "project-view");


        frame.add(cardPanel);

        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createMainView() {
        JPanel mainPanel = new JPanel();
        JButton jobViewButton = new JButton("View Jobs");
        JButton employeeViewButton = new JButton("View Employees");
        JButton projectViewButton = new JButton("View Projects");

        jobViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "job-view");
            }
        });

        employeeViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "employee-view");
            }
        });

        projectViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "project-view");
            }
        });

        mainPanel.add(jobViewButton);
        mainPanel.add(employeeViewButton);
        mainPanel.add(projectViewButton);

        return mainPanel;
    }

    public void showMainView() {
        cardLayout.show(cardPanel, "main");
    }
}
