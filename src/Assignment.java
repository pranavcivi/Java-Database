public class Assignment {
    private int employeeID;
    private int projectID;
    private double hoursBilled;
    private double totalCharge;

    public Assignment(){}

    public Assignment(int employeeID, int projectID, double hoursBilled, double totalCharge){
        this.employeeID = employeeID;
        this.projectID = projectID;
        this.hoursBilled = hoursBilled;
        this.totalCharge = totalCharge;
    }

    // Getters
    public int getEmployeeID() {
        return employeeID;
    }

    public int getProjectID() {
        return projectID;
    }

    public double getHoursBilled() {
        return hoursBilled;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    // Setters
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public void setHoursBilled(double hoursBilled) {
        this.hoursBilled = hoursBilled;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }
}
