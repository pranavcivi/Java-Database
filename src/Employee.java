public class Employee {
    private int employeeNumber;
    private String name;
    private int jobClassId;

    public Employee(){}
    
    public Employee(int employeeNumber, String name, int jobClassId) {
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.jobClassId = jobClassId;
    }

    // Getter for employeeNumber
    public int getEmployeeNumber() {
        return employeeNumber;
    }

    // Setter for employeeNumber
    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for jobClassId
    public int getJobClassId() {
        return jobClassId;
    }

    // Setter for jobClassId
    public void setJobClassId(int jobClassId) {
        this.jobClassId = jobClassId;
    }
}
