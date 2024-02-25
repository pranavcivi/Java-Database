import javax.swing.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectView {

    private JPanel panel;
    private App mainFrame;
    private DataAccess dataAccess;
    private Connection connection;

    private String getProjects;
    private JTextArea textArea;
    private JTextArea assignmentTextArea;
    private JTextArea employeeTextArea;
    private JComboBox<Integer> cb;
    private JComboBox<Integer> assignmentCB;

    public ProjectView(App mainFrame, Connection connection) throws SQLException {
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

        panel.add(buttPanel);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showMainView();
            }
        });

        JPanel showProjectsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textArea = new JTextArea(dataAccess.readProjects());
        showProjectsPanel.add(textArea);

        JPanel selectProjectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl = new JLabel("Select Job ID");
        Integer[] currentProjectIDs = dataAccess.readProjectIds().toArray(new Integer[0]);
        cb = new JComboBox<>(currentProjectIDs);
        JButton selectIdSubmit = new JButton("Submit");
        textArea.setFont( new Font("monospaced", Font.BOLD, 12) );
        selectProjectPanel.add(lbl);
        selectProjectPanel.add(cb);
        selectProjectPanel.add(selectIdSubmit);

        JPanel addProjectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel addProjectInstructions = new JLabel("Input new Number, Name, Leader, Total Charge:");
        JSpinner idSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        idSpinner.setPreferredSize(new Dimension(50, 20));
        JTextField nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(75, 20));
        JSpinner leaderIdSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        leaderIdSpinner.setPreferredSize(new Dimension(50, 20));
        JSpinner totalChargeSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        totalChargeSpinner.setPreferredSize(new Dimension(50, 20));
        JButton addProjectSubmit = new JButton("Add new Project");
        addProjectPanel.add(addProjectInstructions);
        addProjectPanel.add(idSpinner);
        addProjectPanel.add(nameTextField);
        addProjectPanel.add(leaderIdSpinner);
        addProjectPanel.add(totalChargeSpinner);
        addProjectPanel.add(addProjectSubmit);

        // section to view employee table

        addProjectSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Project newProject = new Project((int)idSpinner.getValue(), nameTextField.getText(), (int)leaderIdSpinner.getValue(), (double)totalChargeSpinner.getValue(), null);
                try {dataAccess.createProject(newProject);} catch (SQLException e1) {e1.printStackTrace();}
                try {getProjects = dataAccess.readProjects();} catch (SQLException e1) {e1.printStackTrace();}
                textArea.setText(getProjects);
                idSpinner.setValue(0);
                nameTextField.setText("");
                leaderIdSpinner.setValue(0);
                totalChargeSpinner.setValue(0.0);
            }
        });

        selectIdSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedID = (int)cb.getSelectedItem();
                Project loadedProject = new Project();
                try {loadedProject = dataAccess.readProject(selectedID);} catch (SQLException e1) {e1.printStackTrace();}
                
                JPanel loadedProjectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JLabel loadLabel = new JLabel("Update Selected Project:");
                JSpinner loadIdSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
                loadIdSpinner.setPreferredSize(new Dimension(50, 20));
                loadIdSpinner.setValue(loadedProject.getNumber());
                JTextField loadName = new JTextField();
                loadName.setText(loadedProject.getName());
                loadName.setPreferredSize(new Dimension(125, 20));
                JSpinner loadLeaderSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
                loadLeaderSpinner.setPreferredSize(new Dimension(50, 20));
                loadLeaderSpinner.setValue(loadedProject.getLeader());
                JSpinner loadChargeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.01));
                loadChargeSpinner.setPreferredSize(new Dimension(100, 20));
                loadChargeSpinner.setValue(loadedProject.getTotalCharge());
                JButton updateProjectButton = new JButton("Update");

                loadedProjectPanel.add(loadLabel);
                loadedProjectPanel.add(loadIdSpinner);
                loadedProjectPanel.add(loadName);
                loadedProjectPanel.add(loadLeaderSpinner);
                loadedProjectPanel.add(loadChargeSpinner);
                loadedProjectPanel.add(updateProjectButton);

                panel.add(loadedProjectPanel);
                panel.revalidate();

                // add text area for assignments based on selected project
                JPanel showAssignmentsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                try {assignmentTextArea = new JTextArea(dataAccess.readAssignments(selectedID));} catch (SQLException e1) {e1.printStackTrace();}
                showAssignmentsPanel.add(assignmentTextArea);
                // Integer[] currentAssignmentIDs = null;
                // try {currentAssignmentIDs = dataAccess.readAssignmentIds(selectedID).toArray(new Integer[0]);} catch (SQLException e1) {e1.printStackTrace();}
                // assignmentCB = new JComboBox<>(currentAssignmentIDs);
                // showAssignmentsPanel.add(assignmentCB);
                assignmentTextArea.setFont( new Font("monospaced", Font.BOLD, 12) );
                panel.add(showAssignmentsPanel);

                JLabel jl = new JLabel("Add new Assignment");
                JPanel showAssignmentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JSpinner empSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
                empSpinner.setPreferredSize(new Dimension(50, 20));
                JLabel projLabel = new JLabel(String.valueOf(selectedID));
                // JSpinner projSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
                // projSpinner.setPreferredSize(new Dimension(50, 20));
                JSpinner hoursBilledSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
                hoursBilledSpinner.setPreferredSize(new Dimension(100, 20));
                JSpinner totalChargeSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
                totalChargeSpinner.setPreferredSize(new Dimension(100, 20));
                JButton assignmentSubmit = new JButton("Add Assignment");
                showAssignmentPanel.add(jl);
                showAssignmentPanel.add(empSpinner);
                showAssignmentPanel.add(projLabel);
                showAssignmentPanel.add(hoursBilledSpinner);
                showAssignmentPanel.add(totalChargeSpinner);
                showAssignmentPanel.add(assignmentSubmit);

                panel.add(showAssignmentPanel);
                panel.revalidate();

                assignmentSubmit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        Assignment newAss = new Assignment((int)empSpinner.getValue(), selectedID, (double)hoursBilledSpinner.getValue(), (double)totalChargeSpinner.getValue());
                        try {dataAccess.createAssignment(newAss);} catch (SQLException e1) {e1.printStackTrace();}
                        try {assignmentTextArea.setText(dataAccess.readAssignments(selectedID));} catch (SQLException e1) {e1.printStackTrace();}
                        panel.revalidate();

                    }
                });


                updateProjectButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        Project updateProject = new Project((int)loadIdSpinner.getValue(), loadName.getText(), (int)loadLeaderSpinner.getValue(), (Double)loadChargeSpinner.getValue(), null);
                        try {
                            dataAccess.updateProject(updateProject, selectedID);
                            try {
                                getProjects = dataAccess.readProjects();
                                textArea.setText(getProjects);
                                Integer[] currentProjectIDs = dataAccess.readProjectIds().toArray(new Integer[0]);
                                cb.setModel(new DefaultComboBoxModel<>(currentProjectIDs));
                                loadedProjectPanel.remove(loadLabel);
                                loadedProjectPanel.remove(loadIdSpinner);
                                loadedProjectPanel.remove(loadName);
                                loadedProjectPanel.remove(loadLeaderSpinner);
                                loadedProjectPanel.remove(loadChargeSpinner);
                                loadedProjectPanel.remove(updateProjectButton);
                                showAssignmentsPanel.remove(assignmentTextArea);

                                panel.revalidate();
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



        panel.add(showProjectsPanel);
        panel.add(addProjectPanel);
        panel.add(selectProjectPanel);
    }

    public JPanel getPanel() {
        return panel;
    }
}
