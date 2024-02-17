import java.sql.Statement;
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

}
