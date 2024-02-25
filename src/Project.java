import java.util.ArrayList;
import java.util.HashMap;

public class Project {
    private int number;
    private String name;
    private int leader;
    private Double totalCharge;
    private ArrayList<Assignment> assignments;

    // Default Constructor
    public Project() {
        // Initialize fields with default values if needed
        this.number = 0;
        this.name = "";
        this.leader = 0;
        this.totalCharge = 0.0;
        this.assignments = new ArrayList<>();
    }

    // Parameterized Constructor
    public Project(int number, String name, int leader, Double totalCharge, ArrayList<Assignment> assignments) {
        this.number = number;
        this.name = name;
        this.leader = leader;
        this.totalCharge = totalCharge;
        this.assignments = assignments;
    }

    // Getters
    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getLeader() {
        return leader;
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    // Setters
    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeader(int leader) {
        this.leader = leader;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

}
