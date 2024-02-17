import javax.swing.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeView {

    private JPanel panel;
    private App mainFrame;
    private DataAccess dataAccess;
    private Connection connection;
    private String getEmployees;

    public EmployeeView(App mainFrame, Connection connection) throws SQLException {
        this.mainFrame = mainFrame;
        this.connection = connection;
        this.dataAccess = new DataAccess(connection);
        initializePanel();
    }

    private void initializePanel() throws SQLException {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel buttPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back to Main View");
        buttPanel.add(backButton);

        dataAccess = new DataAccess(connection);
        getEmployees = dataAccess.readEmployees();
        JTextArea textArea = new JTextArea(getEmployees);
        textArea.setFont( new Font("monospaced", Font.BOLD, 12) );
        JPanel jobsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jobsPanel.add(textArea);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showMainView();
            }
        });

        JPanel idInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel idLabel = new JLabel("Enter Employee Number to load Job");
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner numericSpinner = new JSpinner(spinnerModel);
        JButton idSubmit = new JButton("Submit");
        idInputPanel.add(idLabel);
        idInputPanel.add(numericSpinner);
        idInputPanel.add(idSubmit);

        JPanel addEmpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        SpinnerNumberModel spinnerModel2 = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner numericSpinner2 = new JSpinner(spinnerModel2);
        numericSpinner2.setPreferredSize(new Dimension(100, 20));
        JTextField inputName = new JTextField("");
        inputName.setPreferredSize(new Dimension(100, 20));
        SpinnerNumberModel spinnerModel3 = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner numericSpinner3 = new JSpinner(spinnerModel3);
        numericSpinner3.setPreferredSize(new Dimension(100, 20));
        JButton submitAddEmp = new JButton("Add new Employee");
        addEmpPanel.add(numericSpinner2);
        addEmpPanel.add(inputName);
        addEmpPanel.add(numericSpinner3);
        addEmpPanel.add(submitAddEmp);

        submitAddEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inputName.getText().isEmpty()){
                    Employee newEmp = new Employee((int)numericSpinner2.getValue(), (String)inputName.getText(), (int)spinnerModel3.getValue());
                    try {dataAccess.createEmployee(newEmp);} catch (SQLException e1) {e1.printStackTrace();}
                    // try {refreshPanel();} catch (SQLException e1) {e1.printStackTrace();}
                    try {getEmployees = dataAccess.readEmployees();} catch (SQLException e1) {e1.printStackTrace();}
                    textArea.setText(getEmployees);
                    numericSpinner2.setValue(0);
                    inputName.setText("");
                    numericSpinner3.setValue(0);
                }
            }
        });

        idSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int loadId = (Integer)numericSpinner.getValue();
                Employee emp = new Employee();
                // System.out.println("loadId: " + loadId);
                try {emp = dataAccess.readEmployees(loadId);} catch (SQLException e1) {e1.printStackTrace();}
                JPanel loadedJobPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

                JLabel loadLabel = new JLabel("Loaded Employee:");

                SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
                JSpinner numericSpinner = new JSpinner(spinnerModel);
                numericSpinner.setPreferredSize(new Dimension(100, 20));
                numericSpinner.setValue(loadId);

                JTextField loadEmpName = new JTextField("");
                loadEmpName.setPreferredSize(new Dimension(150, 20));
                loadEmpName.setText(emp.getName());

                SpinnerNumberModel spinnerModel3 = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
                JSpinner numericSpinner3 = new JSpinner(spinnerModel3);
                numericSpinner3.setPreferredSize(new Dimension(100, 20));
                numericSpinner3.setValue(emp.getJobClassId());

                JButton loadSubmit = new JButton("Update Job");

                loadedJobPanel.add(loadLabel);
                loadedJobPanel.add(numericSpinner);
                loadedJobPanel.add(loadEmpName);
                loadedJobPanel.add(numericSpinner3);
                loadedJobPanel.add(loadSubmit);

                panel.add(loadedJobPanel);
                panel.revalidate();

                loadSubmit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        Employee updateEmp = new Employee((Integer)numericSpinner.getValue(), loadEmpName.getText(), (Integer)numericSpinner3.getValue());
                        try {
                            dataAccess.updateEmployee(updateEmp, loadId);
                            try {
                                getEmployees = dataAccess.readEmployees();
                                textArea.setText(getEmployees);
                                loadedJobPanel.remove(loadLabel);
                                loadedJobPanel.remove(numericSpinner);
                                loadedJobPanel.remove(loadEmpName);
                                loadedJobPanel.remove(numericSpinner3);
                                loadedJobPanel.remove(loadSubmit);
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }


                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        });

        panel.add(buttPanel);
        panel.add(jobsPanel);
        panel.add(addEmpPanel);
        panel.add(idInputPanel);
        
    }

    public JPanel getPanel() {
        return panel;
    }
}
