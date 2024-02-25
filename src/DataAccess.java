import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataAccess {
    private Connection conn;
    public DataAccess(Connection connection){
        this.conn = connection;
    }

    public String createJob(Job job) throws SQLException{
        Statement smt = conn.createStatement();
        String query = String.format("INSERT INTO jobclass (id, title, wage) VALUES (%d, '%s', %f)", job.getId(), job.getTitle(), job.getWage());
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }

    public String createEmployee(Employee employee) throws SQLException{
        Statement smt = conn.createStatement();
        String query = String.format("INSERT INTO employee (employee_number, name, JobClassId) VALUES (%d, '%s', %d)", employee.getEmployeeNumber(), employee.getName(), employee.getJobClassId());
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }

    public String readJobs() throws SQLException{
        Statement smt = conn.createStatement();
        String query = "select * from jobclass;";
        ResultSet rs = smt.executeQuery(query);
        StringBuilder sb = new StringBuilder(String.format("%-10s | %-30s | %-20s\n", "id", "Title", "Wage"));
        sb.append("-".repeat(11) + "|" + "-".repeat(32) + "|" + "-".repeat(17) + "\n");
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("Title");
            double wage = rs.getDouble("Wage");

            // Use String.format to create a formatted string
            String formattedLine = String.format("%-10s | %-30s | %-20.2f", id, title, wage);
            sb.append(formattedLine);
            sb.append("\n");
        }
        // System.out.println(sb.toString());
        return sb.toString();
    }
    public Job readJobs(int id) throws SQLException{
        Statement smt = conn.createStatement();
        String query = "select * from jobclass where id=" + id + ";";
        ResultSet rs = smt.executeQuery(query);
        Job job;
        if(rs.next()){
            job = new Job(id, rs.getString("title"), rs.getDouble("wage"));
            return job;
        }
        return null;
        
    }

    public String readEmployees() throws SQLException{
        Statement smt = conn.createStatement();
        String query = "select * from employee;";
        ResultSet rs = smt.executeQuery(query);
        StringBuilder sb = new StringBuilder(String.format("%-15s | %-30s | %-20s\n", "Employee Num", "Name", "Job Class Id"));
        sb.append("-".repeat(16) + "|" + "-".repeat(32) + "|" + "-".repeat(17) + "\n");
        while(rs.next()){
            int empId = rs.getInt("employee_number");
            String name = rs.getString("name");
            int jobClassId = rs.getInt("JobClassId");
            String formattedLine = String.format("%-15d | %-30s | %-20d", empId, name, jobClassId);
            sb.append(formattedLine);
            sb.append("\n");
        }
        return sb.toString();
    }
    public Employee readEmployees(int id) throws SQLException{
        Statement smt = conn.createStatement();
        String query = "select * from employee where employee_number=" + id + ";";
        ResultSet rs = smt.executeQuery(query);
        Employee emp;
        if(rs.next()){
            emp = new Employee(id, rs.getString("name"), rs.getInt("JobClassId"));
            return emp;
        }
        return null;
    }

    // update jobclass set id=98, title='change', wage='10.01' where id=99;
    public String updateJob(Job newJob, int oldId) throws SQLException{
        Statement smt = conn.createStatement();
        String query = String.format("update jobclass set id=%d, title='%s', wage=%f where id=%d", newJob.getId(), newJob.getTitle(), newJob.getWage(), oldId);
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }

    public String updateEmployee(Employee newEmployee, int oldId) throws SQLException{
        Statement smt = conn.createStatement();
        String query = String.format("update employee set employee_number=%d, name='%s', JobClassId=%d where employee_number=%d;", newEmployee.getEmployeeNumber(), newEmployee.getName(), newEmployee.getJobClassId(), oldId);
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }

    public String deleteJob(int id) throws SQLException{
        Statement smt = conn.createStatement();
        String query = String.format("delete from jobclass where id=%d", id);
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }

    public String deleteEmployee(int id) throws SQLException{
        Statement smt = conn.createStatement();
        String query = String.format("delete from employee where id=%d", id);
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }

    public String readProjects() throws SQLException{
        Statement smt = conn.createStatement();
        String query = "select * from project;";
        ResultSet rs = smt.executeQuery(query);
        StringBuilder sb = new StringBuilder(String.format("%-6s | %-15s | %-10s | %-10s\n", "number", "Name", "Leader", "Total Charge"));
        while(rs.next()){
            int number = rs.getInt("number");
            String name = rs.getString("name");
            int leader = rs.getInt("leader");
            double totalCharge = rs.getDouble("TotalCharge");
            String formattedLine = String.format("%-6d | %-15s | %-10d | %-10.2f", number, name, leader, totalCharge);
            sb.append(formattedLine + "\n");
        }
        return sb.toString();
    }
    public Project readProject(int id) throws SQLException{
        Statement smt = conn.createStatement();
        String query = "select * from project where number=" + id + ";";
        ResultSet rs = smt.executeQuery(query);
        Project proj;
        if(rs.next()){
            proj = new Project(id, rs.getString("name"), rs.getInt("leader"), rs.getDouble("TotalCharge"), null);
            return proj;
        }
        return null;
    }

    public ArrayList<Integer> readProjectIds() throws SQLException{
        ArrayList<Integer> out = new ArrayList<>();
        Statement smt = conn.createStatement();
        String query = "select number from project;";
        ResultSet rs = smt.executeQuery(query);
        while(rs.next()){
            out.add(rs.getInt("number"));
        }
        return out;
    }
    public String createProject(Project newProj) throws SQLException{
        int number = newProj.getNumber();
        String name = newProj.getName();
        int leader = newProj.getLeader();
        double totalCharge = newProj.getTotalCharge();
        
        Statement smt = conn.createStatement();
        String query = String.format("insert into project (number, name, leader, TotalCharge) values (%d, '%s', %d, %f)", number, name, leader, totalCharge);
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }

    public String updateProject(Project updatedProj, int oldID) throws SQLException {
        int number = updatedProj.getNumber();
        String name = updatedProj.getName();
        int leader = updatedProj.getLeader();
        double totalCharge = updatedProj.getTotalCharge();
    
        Statement smt = conn.createStatement();
        String query = String.format("update project set number = %d, name = '%s', leader = %d, TotalCharge = %f where number = %d", number, name, leader, totalCharge, oldID);
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }
    
    public String deleteProject(int projectNumber) throws SQLException {
        Statement smt = conn.createStatement();
        String query = String.format("delete from project where number = %d", projectNumber);
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }

    public String createAssignment(Assignment newAssignment) throws SQLException {
        int employeeID = newAssignment.getEmployeeID();
        int projectID = newAssignment.getProjectID();
        double hoursBilled = newAssignment.getHoursBilled();
        double totalCharge = newAssignment.getTotalCharge();

        Statement smt = conn.createStatement();
        String query = String.format("insert into assignment (employeeID, projectID, hoursBilled, totalCharge) values (%d, %d, %f, %f)", employeeID, projectID, hoursBilled, totalCharge);
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }

    public String readAssignment() throws SQLException {
        Statement smt = conn.createStatement();
        String query = String.format("select * from assignment");
        ResultSet resultSet = smt.executeQuery(query);
        StringBuilder sb = new StringBuilder();

        while(resultSet.next()) {
            int empid = resultSet.getInt("EmployeeID");
            int projid = resultSet.getInt("ProjectID");
            double hoursBilled = resultSet.getDouble("hoursBilled");
            double totalCharge = resultSet.getDouble("totalCharge");
            String formattedLine = String.format("%d | %d | %.1f| %.2f\n", empid, projid, hoursBilled, totalCharge);
            sb.append(formattedLine);
        }
        return sb.toString();
    }
    public ArrayList<Integer> readAssignmentIds(int projectID) throws SQLException{
        ArrayList<Integer> out = new ArrayList<>();
        Statement smt = conn.createStatement();
        String query = String.format("select EmployeeID from assignment where projectid=%d;", projectID);
        ResultSet rs = smt.executeQuery(query);
        while(rs.next()){
            out.add(rs.getInt("EmployeeID"));
        }
        return out;
    }

    public String readAssignments(int projectID) throws SQLException {
        Statement smt = conn.createStatement();
        String query = String.format("select * from assignment where projectid=%d;", projectID);
        ResultSet resultSet = smt.executeQuery(query);
        StringBuilder sb = new StringBuilder(String.format("%-15s | %-15s | %-15s | %-10s\n", "EmployeeID", "ProjectID", "HoursBilled", "Total Charge"));

        while(resultSet.next()) {
            int empid = resultSet.getInt("EmployeeID");
            int projid = resultSet.getInt("ProjectID");
            double hoursBilled = resultSet.getDouble("hoursBilled");
            double totalCharge = resultSet.getDouble("totalCharge");
            String formattedLine = String.format("%-15d | %-15d | %-15.1f| %-10.2f\n", empid, projid, hoursBilled, totalCharge);
            sb.append(formattedLine);
        }
        return sb.toString();
    }
    public Assignment getAssignment(int employeeID, int projectID) throws SQLException {
        Statement smt = conn.createStatement();
        String query = String.format("select * from assignment where employeeid=%d and projectid=%d;", employeeID, projectID);
        ResultSet resultSet = smt.executeQuery(query);

        if(resultSet.next()) {
            int empid = resultSet.getInt("EmployeeID");
            int projid = resultSet.getInt("ProjectID");
            double hoursBilled = resultSet.getDouble("hoursBilled");
            double totalCharge = resultSet.getDouble("totalCharge");
            return new Assignment(empid, projid, hoursBilled, totalCharge);
        }
        return null;
        
    }

    // update and delete functions for assignment
    public String updateAssignment(Assignment ass, int oldEmployeeID, int oldProjectID) throws SQLException{
        int employeeId = ass.getEmployeeID();
        int projectId = ass.getProjectID();
        double hoursBilled = ass.getHoursBilled();
        double totalCharge = ass.getTotalCharge();

        Statement smt = conn.createStatement();
        String query = String.format("update assignment set EmployeeID = %d, ProjectID = %d, HoursBilled = %f, TotalCharge = %f where employeeId = %d and projectId = %d", employeeId, projectId, hoursBilled, totalCharge, oldEmployeeID, oldProjectID);
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }

    public String deleteAssignment(int empid, int projid) throws SQLException{
        Statement smt = conn.createStatement();
        String query = String.format("delete from assignment where EmployeeID = %d and ProjectID = %d", empid, projid);
        int rowsAffected = smt.executeUpdate(query);
        return "Rows Affected: " + rowsAffected;
    }
    

}
