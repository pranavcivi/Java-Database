import javax.swing.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class JobView {

    private JPanel panel;
    private App mainFrame;
    private DataAccess dataAccess;
    private Connection connection;

    private String getJobs;
    private JTextArea textArea;

    public JobView(App mainFrame, Connection connection) throws SQLException {
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
        // layoutX="14.0" layoutY="19.0" mnemonicParsing="false" text="Button"
        // backButton.setLayout(null);
        
        dataAccess = new DataAccess(connection);
        getJobs = dataAccess.readJobs();
        textArea = new JTextArea(getJobs);
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
        JLabel idLabel = new JLabel("Enter id to load Job");
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner numericSpinner = new JSpinner(spinnerModel);
        JButton idSubmit = new JButton("Submit");
        idInputPanel.add(idLabel);
        idInputPanel.add(numericSpinner);
        idInputPanel.add(idSubmit);

        JPanel addJobPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        SpinnerNumberModel spinnerModel2 = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner numericSpinner2 = new JSpinner(spinnerModel2);
        numericSpinner2.setPreferredSize(new Dimension(100, 20));
        JTextField inputJobName = new JTextField("");
        inputJobName.setPreferredSize(new Dimension(100, 20));
        SpinnerNumberModel spinnerModel3 = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.01);
        JSpinner numericSpinner3 = new JSpinner(spinnerModel3);
        numericSpinner3.setPreferredSize(new Dimension(100, 20));
        JButton submitAddJob = new JButton("Add new Job");
        addJobPanel.add(numericSpinner2);
        addJobPanel.add(inputJobName);
        addJobPanel.add(numericSpinner3);
        addJobPanel.add(submitAddJob);

        submitAddJob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inputJobName.getText().isEmpty()){
                    Job newJob = new Job((int)numericSpinner2.getValue(), (String)inputJobName.getText(), (Double)spinnerModel3.getValue());
                    try {dataAccess.createJob(newJob);} catch (SQLException e1) {e1.printStackTrace();}
                    // try {refreshPanel();} catch (SQLException e1) {e1.printStackTrace();}
                    try {getJobs = dataAccess.readJobs();} catch (SQLException e1) {e1.printStackTrace();}
                    textArea.setText(getJobs);
                    numericSpinner2.setValue(0);
                    inputJobName.setText("");
                    numericSpinner3.setValue(0);
                }
            }
        });
        idSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int loadId = (Integer)numericSpinner.getValue();
                Job job = new Job();
                try {job = dataAccess.readJobs(loadId);} catch (SQLException e1) {e1.printStackTrace();}
                JPanel loadedJobPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

                JLabel loadLabel = new JLabel("Loaded Job:");

                SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
                JSpinner numericSpinner = new JSpinner(spinnerModel);
                numericSpinner.setPreferredSize(new Dimension(100, 20));
                numericSpinner.setValue(loadId);

                JTextField loadJobName = new JTextField("");
                loadJobName.setPreferredSize(new Dimension(150, 20));
                loadJobName.setText(job.getTitle());

                SpinnerNumberModel spinnerModel3 = new SpinnerNumberModel(0, 0, Double.MAX_VALUE, 0.01);
                JSpinner numericSpinner3 = new JSpinner(spinnerModel3);
                numericSpinner3.setPreferredSize(new Dimension(100, 20));
                numericSpinner3.setValue(job.getWage());

                JButton loadSubmit = new JButton("Update Job");

                loadedJobPanel.add(loadLabel);
                loadedJobPanel.add(numericSpinner);
                loadedJobPanel.add(loadJobName);
                loadedJobPanel.add(numericSpinner3);
                loadedJobPanel.add(loadSubmit);

                panel.add(loadedJobPanel);
                panel.revalidate();

                loadSubmit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        Job updateJob = new Job((Integer)numericSpinner.getValue(), loadJobName.getText(), (Double)numericSpinner3.getValue());
                        try {
                            dataAccess.updateJob(updateJob, loadId);
                            try {
                                getJobs = dataAccess.readJobs();
                                textArea.setText(getJobs);
                                loadedJobPanel.remove(loadLabel);
                                loadedJobPanel.remove(numericSpinner);
                                loadedJobPanel.remove(loadJobName);
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

        
        // panel.add(backButton);
        panel.add(buttPanel);
        panel.add(jobsPanel);
        panel.add(addJobPanel);
        panel.add(idInputPanel);
    }

    public JPanel getPanel() {
        return panel;
    }
}
